package com.ydh;

/*
 * TODO:
 *  SPLIT - ściąga dzieli na video
 *  RAW - tylko ściąga
 *  SPLITAUDIO - ściąga audio i dzieli na audio
 *  AUDIO - ściąga tylko audio
 *  CONVERT - ściąga i tylko konwertuje
 *  CONVERTAUDIO - ściąga i konwertuje audio (dla zbugowanych filmów)
 *
 * TODO:
 *  CONFIG VERBOSE OR NOT
 *  CONFIG PRINT STACKTRACE
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Config.loadConfig();
        FileParser.parseFile("input.txt");
        if(Config.VERBOSE_CONSOLE) System.out.println(MemoryImproved.getInstance().produceContentString());
        if(Config.VERBOSE_LOGS) FileManager.writeFile("content.log", MemoryImproved.getInstance().produceContentString());

        int i=0;
        for (MovieInfo movieInfo : MemoryImproved.getInstance().getMoviesToDownload()) {
            i++;
            String youtubeDownloadCommand = MemoryImproved.produceYoutubeDownloadCommand(movieInfo);
            if(Config.VERBOSE_CONSOLE) System.out.println("YT COMMAND: " + youtubeDownloadCommand + "\n\n");
            if(Config.VERBOSE_LOGS) FileManager.writeFile(i+"ytCommand.bat", youtubeDownloadCommand);
            List<String> filesBefore = getAllFiles();
            String outputYT = ProcessHelper.execute(youtubeDownloadCommand);
            List<String> filesAfter = getAllFiles();
            if(Config.VERBOSE_CONSOLE) System.out.println("YT OUTPUT: " + outputYT + "\n\n");
            if(Config.VERBOSE_LOGS) FileManager.writeFile(i+"ytCommand.log", outputYT);

            String fileNameToConvert = findNewFile(filesBefore, filesAfter);
            if(Config.VERBOSE_CONSOLE) System.out.println("FOUND NEW FILE: " + fileNameToConvert);
            if (fileNameToConvert == null) throw new Exception("404 - File not found");
            //CONVERT
            String ffmpegCommand = null;
            switch(movieInfo.getMethod()) {
                case "SPLIT":
                    ffmpegCommand = MemoryImproved.produceFFmpegCommand(movieInfo, fileNameToConvert);
                    break;
                case "RAW":
                    ffmpegCommand = MemoryImproved.produceFFmpegCommandConvertOnly(movieInfo, fileNameToConvert);
                    break;
                case "AUDIOONLY":
                    throw new Exception("NOT IMPLEMENTED YET");
                case "VIDEOONLY":
                    throw new Exception("NOT IMPLEMENTED YET");
                case "AUDIOFIX":
                    throw new Exception("NOT IMPLEMENTED YET");
                case "VIDEOFIX":
                    throw new Exception("NOT IMPLEMENTED YET");
                case "AVFIX:":
                    throw new Exception("NOT IMPLEMENTED YET");
                default:
                    throw new Exception("Invalid file content - allowed methods: SPLIT/RAW/AUDIOONLY/VIDEOONLY/AUDIOFIX/VIDEOFIX/AVFIX");
            }
            if(Config.VERBOSE_CONSOLE) System.out.println("FF COMMAND: " + ffmpegCommand + "\n\n");
            if (Config.HALF_MANUAL_FFMPEG) {
                FileManager.writeFile("ffCommand.bat", ffmpegCommand);
                System.out.println("Half manual mode is ON! Run ffCommand.bat, wait for finish and press Enter here to continue");
                System.in.read();
                removeFile("ffCommand.bat");
            }
            else {

                String outputFF = ProcessHelper.execute(ffmpegCommand);
                if(Config.VERBOSE_CONSOLE) System.out.println("FF OUTPUT: " + outputFF + "\n\n");
                if(Config.VERBOSE_LOGS) FileManager.writeFile(i+"ffCommand.log", outputFF);
            }
            if(Config.VERBOSE_LOGS) FileManager.writeFile(i+"ffCommand.bat", ffmpegCommand);
            if (Config.AUTO_CLEAN && !movieInfo.isDoSplit())
                if (!removeFile(fileNameToConvert))
                    throw new Exception("Cannot delete temp file");
        }
    }
    private static List<String> getAllFiles() {
        File curDir = new File(".");
        File[] filesList = curDir.listFiles();
        List<String> fileList = new ArrayList<>();
        for(File f : filesList){
            //if(f.isDirectory()) getAllFiles(f);
            if(f.isFile()){
                if(Config.VERBOSE_CONSOLE) System.out.println("FILE: " + f.getName());
                fileList.add(f.getName());
            }
        }
        return fileList;
    }
    private static boolean removeFile(String fileName) {
        File curDir = new File(".");
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            //if(f.isDirectory()) getAllFiles(f);
            if(f.isFile()){
                if(Config.VERBOSE_CONSOLE) System.out.println("FILE: " + f.getName());
                if(f.getName().equals(fileName)) {
                    return f.delete();
                }
            }
        }
        return false;
    }
    private static String findNewFile(List<String> filesBefore, List<String> filesAfter) {
        for (String fileNew : filesAfter) {
            boolean found = false;
            for (String fileOld : filesBefore) {
                if (fileNew.equals(fileOld)) found=true;
            }
            if (!found) return fileNew;
        }
        return null;
    }
}

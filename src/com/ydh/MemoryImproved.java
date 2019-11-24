package com.ydh;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MemoryImproved {
    private static MemoryImproved _instance;
    private final List<MovieInfo> moviesToDownload = new ArrayList<>();

    public static synchronized MemoryImproved getInstance() {
        if (_instance == null ) _instance = new MemoryImproved();
        return _instance;
    }

    private MemoryImproved() {}

    public List<MovieInfo> getMoviesToDownload() {
        return moviesToDownload;
    }

    public String produceContentString() {
        StringBuilder content = new StringBuilder();
        for (MovieInfo movieInfo : moviesToDownload) {
            content.append("Movie link: ");
            content.append(movieInfo.getMovieLink());
            content.append("\n");
            content.append("Do split?: ");
            content.append(String.valueOf(movieInfo.isDoSplit()));
            content.append("\n");
            if (movieInfo.isDoSplit()) {
                for (SplitInfo splitinfo : movieInfo.getSplitList()) {
                    content.append("Start time: ");
                    content.append(splitinfo.getStartTime());
                    content.append("\n");
                    content.append("End time: ");
                    content.append(splitinfo.getEndTime());
                    content.append("\n");
                    content.append("New name: ");
                    content.append(splitinfo.getNewName());
                    content.append("\n");
                }
            }
            content.append("\n");
        }
        return content.toString();
    }

    public static String produceYoutubeDownloadCommand(MovieInfo movieInfo) {
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("youtube-dl");
        commandBuilder.append(" -f bestvideo+bestaudio");
        //commandBuilder.append(" --format mkv");
        //commandBuilder.append(" --merge-output-format mkv");
        if (movieInfo.isDoSplit()) {
            commandBuilder.append(" -o dl");
        }
        commandBuilder.append(" ");
        commandBuilder.append(movieInfo.getMovieLink());
        return commandBuilder.toString();
    }

    public static String produceFFmpegCommand(MovieInfo movieInfo, String fileNameToConvert) {
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("ffmpeg -loglevel error -nostdin -i ");
        commandBuilder.append(fileNameToConvert);
        for (SplitInfo splitInfo : movieInfo.getSplitList()) {
            commandBuilder.append(" -ss ");
            commandBuilder.append(splitInfo.getStartTime());
            if (splitInfo.getEndTime() != null) {
                commandBuilder.append(" -to ");
                commandBuilder.append(splitInfo.getEndTime());
            }
            commandBuilder.append(" -acodec mp3 -vcodec copy ");
            commandBuilder.append("\"");
            commandBuilder.append(splitInfo.getNewName());
            commandBuilder.append(".mkv\"");
        }
        return commandBuilder.toString();
    }

    public static String produceFFmpegCommandNoVideo(MovieInfo movieInfo, String fileNameToConvert) {
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("ffmpeg -loglevel error -nostdin -i ");
        commandBuilder.append(fileNameToConvert);
        for (SplitInfo splitInfo : movieInfo.getSplitList()) {
            commandBuilder.append(" -ss ");
            commandBuilder.append(splitInfo.getStartTime());
            if (splitInfo.getEndTime() != null) {
                commandBuilder.append(" -to ");
                commandBuilder.append(splitInfo.getEndTime());
            }
            commandBuilder.append(" -acodec mp3 -vn ");
            commandBuilder.append("\"");
            commandBuilder.append(splitInfo.getNewName());
            commandBuilder.append(".mp3\"");
        }
        return commandBuilder.toString();
    }

    public static String produceFFmpegCommandConvertOnly(MovieInfo movieInfo, String fileNameToConvert) {
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("ffmpeg -loglevel error -nostdin -i ");
        commandBuilder.append(fileNameToConvert);
        commandBuilder.append(" -acodec mp3 -vcodec copy output.mkv");
        return commandBuilder.toString();
    }

    public static String produceFFmpegCommandConvertOnlyNoVideo(MovieInfo movieInfo, String fileNameToConvert) {
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("ffmpeg -loglevel error -nostdin -i ");
        commandBuilder.append(fileNameToConvert);
        commandBuilder.append(" -acodec mp3 -vn output.mp3");
        return commandBuilder.toString();
    }
}

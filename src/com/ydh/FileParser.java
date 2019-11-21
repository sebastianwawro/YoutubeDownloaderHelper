package com.ydh;

import java.io.IOException;
import java.util.StringTokenizer;

public class FileParser {

    public static void parseFile(String fileName) throws Exception {
        String fileContent = FileManager.readFile("input.txt");
        StringTokenizer fileTokenizer = new StringTokenizer(fileContent, "\r\n");
        if (!fileTokenizer.hasMoreTokens() || !fileTokenizer.nextToken().equals("START")) {
            throw new Exception("Invalid file content - it has to start with \"START\"");
        }
        while (fileTokenizer.hasMoreTokens()) {
            //LOAD MOVIE NAME
            String movieLink = fileTokenizer.nextToken();
            if (!movieLink.startsWith("https")) {
                throw new Exception("Invalid link format - it has to start with https");
            }

            //LOAD DO SPLIT
            if (!fileTokenizer.hasMoreTokens()) throw new Exception("Invalid file content - no more data");
            String method = fileTokenizer.nextToken();
            boolean doSplit;
            if (method.equals("SPLIT")) {
                doSplit = true;
            }
            else if (method.equals("RAW")) {
                doSplit = false;
            }
            else {
                throw new Exception("Invalid file content - specify \"SPLIT\" or \"RAW\" option");
            }

            if (doSplit && !fileTokenizer.hasMoreTokens()) throw new Exception("Invalid file content - no split data supplied");
            MovieInfo movieInfo = new MovieInfo(movieLink, doSplit);
            MemoryImproved.getInstance().getMoviesToDownload().add(movieInfo);

            //LOAD SPLIT DATA (IF ANY)
            boolean endNow = false;
            while (fileTokenizer.hasMoreTokens()) {
                String splitInfoLine = fileTokenizer.nextToken();
                if (splitInfoLine.equals("NEXT")) {
                    break;
                }
                else if (splitInfoLine.equals("END")) {
                    endNow = true;
                    break;
                }
                movieInfo.getSplitList().add(parseSplitInfoLine(splitInfoLine));
            }
            SplitInfo.updateEndTimes(movieInfo.getSplitList());
            if (endNow) {
                break;
            }
        }
    }

    private static SplitInfo parseSplitInfoLine(String line) {
        StringTokenizer lineTokenizer = new StringTokenizer(line, "\t");
        String startTime = lineTokenizer.nextToken();
        String newName = lineTokenizer.nextToken();
        return new SplitInfo(startTime, newName);
    }
}

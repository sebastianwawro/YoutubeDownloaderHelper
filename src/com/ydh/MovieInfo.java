package com.ydh;

import java.util.ArrayList;
import java.util.List;

public class MovieInfo {
    private String movieLink;
    private boolean doSplit;
    private String method;
    private final List<SplitInfo> splitList = new ArrayList<>();

    public MovieInfo(String movieLink, boolean doSplit) {
        this.movieLink = movieLink;
        this.doSplit = doSplit;
    }

    public MovieInfo(String movieLink, boolean doSplit, String method) {
        this.movieLink = movieLink;
        this.doSplit = doSplit;
        this.method = method;
    }

    public String getMovieLink() {
        return movieLink;
    }

    public void setMovieLink(String movieLink) {
        this.movieLink = movieLink;
    }

    public boolean isDoSplit() {
        return doSplit;
    }

    public void setDoSplit(boolean doSplit) {
        this.doSplit = doSplit;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<SplitInfo> getSplitList() {
        return splitList;
    }
}

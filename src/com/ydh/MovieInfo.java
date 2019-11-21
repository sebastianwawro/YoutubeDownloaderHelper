package com.ydh;

import java.util.ArrayList;
import java.util.List;

public class MovieInfo {
    private String movieLink;
    private boolean doSplit;
    private final List<SplitInfo> splitList = new ArrayList<>();

    public MovieInfo(String movieLink, boolean doSplit) {
        this.movieLink = movieLink;
        this.doSplit = doSplit;
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

    public List<SplitInfo> getSplitList() {
        return splitList;
    }
}

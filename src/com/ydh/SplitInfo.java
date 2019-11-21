package com.ydh;

import java.util.List;

public class SplitInfo {
    private String startTime;
    private String endTime;
    private String newName;

    public SplitInfo(String startTime, String newName) {
        this.startTime = startTime;
        this.newName = newName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public static void updateEndTimes(List<SplitInfo> splitInfoList) {
        int count = splitInfoList.size();
        for (int i=0; i<count-1; i++) {
            SplitInfo current = splitInfoList.get(i);
            SplitInfo next = splitInfoList.get(i+1);
            current.setEndTime(next.getStartTime());
        }
    }
}

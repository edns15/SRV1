package com.sr.taller1.model;

public class User {

    private String userId;
    private long trackId;
    private long name;

    public String getUserId() {
        return userId;
    }
    public long getTrackId() {
        return trackId;
    }
    public long getName() {
        return name;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setTrackId(long userId) {
        this.trackId = userId;
    }
    public void setName(long name) {
        this.name = name;
    }

}

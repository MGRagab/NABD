package com.appventure.nabd.beans;

/**
 * Created by MGRagab on 11/5/2015.
 */
public class FollowResponse {



    private int status;
    private int isFollowed;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setIsFollowed(int isFollowed) {
        this.isFollowed = isFollowed;
    }

    public int getStatus() {
        return status;
    }

    public int getIsFollowed() {
        return isFollowed;
    }
}

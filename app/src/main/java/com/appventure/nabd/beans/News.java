package com.appventure.nabd.beans;

import java.util.ArrayList;

/**
 * Created by MGRagab on 10/27/2015.
 */
public class News {
    private  Source source ;
    private String sinceWhen ,title , details , image , link ;
    private  int commentsNumber , savesNumber ;
    private ArrayList<Tag> tags ;
    boolean isFavorited ;
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getSinceWhen() {
        return sinceWhen;
    }

    public void setSinceWhen(String sinceWhen) {
        this.sinceWhen = sinceWhen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(int commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public int getSavesNumber() {
        return savesNumber;
    }

    public void setSavesNumber(int savesNumber) {
        this.savesNumber = savesNumber;
    }

    public boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

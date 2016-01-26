package com.appventure.nabd.beans;

/**
 * Created by MGRagab on 11/5/2015.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class newsSourcesTypes {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}

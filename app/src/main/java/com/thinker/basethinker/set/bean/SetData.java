package com.thinker.basethinker.set.bean;


import com.google.gson.annotations.SerializedName;

/**
 * Created by farley on 17/5/24.
 * description:
 */

public class SetData {
    @SerializedName("id")
    private Long id;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
   
    public SetData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


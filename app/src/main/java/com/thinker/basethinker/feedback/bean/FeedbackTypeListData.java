package com.thinker.basethinker.feedback.bean;


import com.google.gson.annotations.SerializedName;

/**
 * Created by farley on 17/5/30.
 * description:
 */

public class FeedbackTypeListData {
    @SerializedName("id")
    private Long id = null;
    @SerializedName("type")
    private String type = null;
    @SerializedName("typeDesc")
    private String typeDesc = null;
    @SerializedName("typeName")
    private String typeName = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

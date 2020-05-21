package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by farley on 17/7/10.
 * description:
 */

public class MessageBean extends BaseBean {
    @SerializedName("content")
    private List<MessageData> content = new ArrayList();
    @SerializedName("error")
    private String error = null;
    @SerializedName("error_description")
    private String errorDescription = null;
    @SerializedName("first")
    private Boolean first = null;
    @SerializedName("last")
    private Boolean last = null;
    @SerializedName("number")
    private Integer number = null;
    @SerializedName("searchDate")
    private Date searchDate = null;
    @SerializedName("size")
    private Integer size = null;
    @SerializedName("success")
    private Boolean success = null;
    @SerializedName("totalElements")
    private Long totalElements = null;
    @SerializedName("totalPages")
    private Integer totalPages = null;

    public List<MessageData> getContent() {
        return content;
    }

    public void setContent(List<MessageData> content) {
        this.content = content;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}

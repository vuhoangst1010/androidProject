package com.example.covid_19;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsClass {

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    @SerializedName("data")
    @Expose
    private List<ResponseData> data;

    public NewsClass() {
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<ResponseData> getData() {
        return data;
    }

    public void setData(List<ResponseData> data) {
        this.data = data;
    }
}

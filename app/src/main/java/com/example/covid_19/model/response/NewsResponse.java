package com.example.covid_19.model.response;

import com.example.covid_19.model.entity.Pagination;
import com.example.covid_19.model.entity.News;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author PhuocNDT
 */
public class NewsResponse {

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    @SerializedName("data")
    @Expose
    private List<News> data;

    public NewsResponse() {
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }
}

package com.example.covid_19.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("limit")
    @Expose
    private int limit;

    @SerializedName("offset")
    @Expose
    private int offset;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("total")
    @Expose
    private int total;

    public Pagination() {
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

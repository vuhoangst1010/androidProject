package com.example.covid_19;

public class RecycleItem {
    private int imageId;
    private String name;
    private String description;

    public RecycleItem(int imageId, String name, String description) {
        this.imageId = imageId;
        this.name = name;
        this.description = description;
    }

    public RecycleItem() {
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

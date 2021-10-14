package com.example.covid_19;

public class NewsClass {
    private String sources, categories, countries, languages , keywords , sort;
    private int offset , limit ;

    public NewsClass(String sources, String categories, String countries, String languages, String keywords, String sort, int offset, int limit) {
        this.sources = sources;
        this.categories = categories;
        this.countries = countries;
        this.languages = languages;
        this.keywords = keywords;
        this.sort = sort;
        this.offset = offset;
        this.limit = limit;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

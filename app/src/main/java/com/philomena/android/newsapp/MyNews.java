package com.philomena.android.newsapp;

class MyNews {

    private String NewsName;
    private String NewsTitle;
    private String NewsDate;
    private String NewsUrl;

    public MyNews(String newsName, String newsTitle, String newsDate, String newsUrl) {
        NewsName = newsName;
        NewsTitle = newsTitle;
        NewsDate = newsDate;
        NewsUrl = newsUrl;
    }

    public String getNewsName() {
        return NewsName;
    }

    public void setNewsName(String newsName) {
        NewsName = newsName;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsDate() {
        return NewsDate;
    }

    public void setNewsDate(String newsDate) {
        NewsDate = newsDate;
    }

    public String getNewsUrl() {
        return NewsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        NewsUrl = newsUrl;
    }
}

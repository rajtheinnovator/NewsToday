package com.example.android.newstoday;

/**
 * Created by ABHISHEK RAJ on 10/1/2016.
 */

public class News {
    private String mTitle;
    private String mNewsAuthor;
    private String mWebUrl;
    public News(String title, String newsAuthor, String webUrl){
        mTitle = title;
        mNewsAuthor = newsAuthor;
        mWebUrl =webUrl;
    }
    public String getTitle(){return mTitle;}
    public String getNewsAuthor(){return mNewsAuthor;}
    public String getWebUrl(){return mWebUrl;}
}

package com.example.android.newstoday;

import static android.R.attr.description;

/**
 * Created by ABHISHEK RAJ on 10/1/2016.
 */

public class News {
    private String mTitle;
    private String mDescription;
    private String mWebUrl;
    public News(String title, String description, String webUrl){
        mTitle = title;
        mDescription =description;
        mWebUrl =webUrl;
    }
    public String getTitle(){return mTitle;}
    public String getDescription(){return mDescription;}
    public String getWebUrl(){return mWebUrl;}
}

package com.example.android.lately.Reddit.RedditArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wasabi on 3/9/2016.
 */
public class Data {
    private List<RedditArticle> children = new ArrayList<RedditArticle>();

    public List<RedditArticle> getChildren() {
        return children;
    }

    public void setChildren(List<RedditArticle> children) {
        this.children = children;
    }

}

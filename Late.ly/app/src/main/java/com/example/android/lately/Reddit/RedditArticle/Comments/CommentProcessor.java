package com.example.android.lately.Reddit.RedditArticle.Comments;

import android.util.Log;

import com.example.android.lately.Cards.RedditComment;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created by Wasabi on 3/10/2016.
 */
public class CommentProcessor {
    private String jsonData;

    public CommentProcessor(String url) {
        this.jsonData = url;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String url) {
        this.jsonData = url;
    }

    // Load various details about the comment
    private RedditComment loadComment(JSONObject data, int level){
        RedditComment comment=new RedditComment(null,null,-1,null);
        try{
            comment.setmContent(data.getString("body"));
            comment.setmAuthor(data.getString("author"));
            comment.setmScore(data.getInt("score"));
            comment.setmLevel(level);
        }catch(Exception e){
            Log.d("ERROR","Unable to parse comment : "+e);
        }
        return comment;
    }

    // This is where the comment is actually loaded
    // For each comment, its replies are recursively loaded
    private void process(ArrayList<RedditComment> comments
            , JSONArray c, int level)
            throws Exception {
        for(int i=0;i<c.length();i++){
            if(c.getJSONObject(i).optString("kind")==null)
                continue;
            if(c.getJSONObject(i).optString("kind").equals("t1")==false)
                continue;
            JSONObject data=c.getJSONObject(i).getJSONObject("data");
            RedditComment comment=loadComment(data,level);
            if(comment.getmAuthor()!=null) {
                comments.add(comment);
                addReplies(comments,data,level+1);
            }
        }
    }

    // Add replies to the comments
    private void addReplies(ArrayList<RedditComment> comments,
                            JSONObject parent, int level){
        try{
            if(parent.get("replies").equals("")){
                // This means the comment has no replies
                return;
            }
            JSONArray r=parent.getJSONObject("replies")
                    .getJSONObject("data")
                    .getJSONArray("children");
            process(comments, r, level);
        }catch(Exception e){
            Log.d("ERROR", "addReplies : " + e);
        }
    }

    // Load the comments as an ArrayList, so that it can be
    // easily passed to the ArrayAdapter
    public ArrayList<RedditComment> fetchComments(){
        ArrayList<RedditComment> comments=new ArrayList<RedditComment>();
        try{
            // Fetch the contents of the comments page
            String raw=jsonData;

            JSONArray r=new JSONArray(raw)
                    .getJSONObject(1)
                    .getJSONObject("data")
                    .getJSONArray("children");

            // All comments at this point are at level 0
            // (i.e., they are not replies)
            process(comments, r, 0);

        }catch(Exception e){
            Log.d("ERROR","Could not connect: "+e);
        }
        return comments;
    }
}

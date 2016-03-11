package com.example.project3vice.vice_classes;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * AsyncTask to retrieve Vice Article data and place within viceArticle class object
 */
public class ArticleSearchAsyncTask extends AsyncTask <Long,Void,ViceArticle> {

    private ViceArticle mViceArticle;

    public ArticleSearchAsyncTask(){}

    @Override
    protected ViceArticle doInBackground(Long... id) {
        String data = "";
        mViceArticle = new ViceArticle();

        try {
            URL url = new URL("http://vice.com/api/article/" + id[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream instream = connection.getInputStream();
            data = getInputData(instream);
            connection.disconnect();
        } catch (Throwable e) {e.printStackTrace();}

        try {
            //gets root JSON Object containing all JSON data
            JSONObject dataObject = new JSONObject(data);
            JSONObject root = dataObject.optJSONObject("data");
            JSONObject article = root.optJSONObject("article");

            //code to populate necessary ViceArticle fields (can be tailored depending on
            // what we want to display on screen
            mViceArticle.setTitle(article.optString("title", ""));
            mViceArticle.setId(article.optLong("id", 0));
            mViceArticle.setUrl(article.optString("url", ""));
            mViceArticle.setAuthor(article.optString("author", ""));
            mViceArticle.setPubDate(article.optString("pubDate", ""));
            mViceArticle.setPubTimestamp(article.optLong("pubTimestamp", 0));
            mViceArticle.setCategory(article.optString("category", ""));
            mViceArticle.setNsfw(article.optBoolean("nsfw", false));
            mViceArticle.setNsfb(article.optBoolean("nsfb", false));
            mViceArticle.setThumb(article.optString("thumb", ""));
            mViceArticle.setImage(article.optString("image", ""));

            //code to pull Video media data if available
            if(article.optJSONObject("media")!=null){
                Media media = new Media();
                Video video = new Video();
                String oId = article.optJSONObject("media").optJSONObject("video").optString("ooyalaId","");
                video.setOoyalaId(oId);
                media.setVideo(video);
                mViceArticle.setMedia(media);
            }

            String[] tags = new String[article.optJSONArray("tags").length()];
            for (int i = 0; i <article.optJSONArray("tags").length();i++){
                tags[i] = article.optJSONArray("tags").optString(i);
            }
            mViceArticle.setTags(tags);

            //code to retrieve body content currently pulling html text...
            mViceArticle.setBody(article.optString("body", ""));

        } catch (JSONException e) {e.printStackTrace();}

        return mViceArticle;
    }

    @Override
    protected void onPostExecute(ViceArticle viceArticle) {
        super.onPostExecute(viceArticle);
    }

    public String getInputData(InputStream inStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        String data;
        while((data = reader.readLine()) != null){builder.append(data);}
        reader.close();
        return builder.toString();
    }
}

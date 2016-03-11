package com.example.project3vice.vice_classes;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * AsyncTask to retrieve list of latest Vice Articles by category
 */
public class GetLatestByCategoryAsyncTask extends AsyncTask <String,Void,ArrayList<ViceArticle>> {

    private ArrayList<ViceArticle> mLatestViceArticles = new ArrayList<>();

    public GetLatestByCategoryAsyncTask() {
    }

    @Override
    protected ArrayList<ViceArticle> doInBackground(String... category) {
        String data = "";

        try {
            URL url = new URL("http://www.vice.com/api/getlatest/category/"+ category[0] +"/0");
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
            JSONArray listOfArticles = root.getJSONArray("items");

            //populates list of ViceArticles with their titles, image, date, tags, preview, and
            // ID's for deeper individual article searches
            String[] emptyTagArray = {};
            for (int i = 0; i <listOfArticles.length();i++){
                JSONObject newJSONArticle = listOfArticles.optJSONObject(i);
                ViceArticle newArticle = new ViceArticle();
                newArticle.setTitle(newJSONArticle.optString("title", ""));
                newArticle.setImage(newJSONArticle.optString("image", ""));
                newArticle.setPubDate(newJSONArticle.optString("pubDate", ""));
                newArticle.setId(newJSONArticle.optLong("id", 0));
                newArticle.setCategory(newJSONArticle.optString("category", ""));
                newArticle.setPreview(newJSONArticle.optString("preview",""));

                if (newJSONArticle.optJSONArray("tags") != null) {
                    String[] tags = new String[newJSONArticle.optJSONArray("tags").length()];
                    for (int x = 0; x < newJSONArticle.optJSONArray("tags").length(); x++) {
                        tags[x] = newJSONArticle.optJSONArray("tags").optString(x);
                    }
                    newArticle.setTags(tags);
                }

                mLatestViceArticles.add(newArticle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mLatestViceArticles;
    }

    @Override
    protected void onPostExecute(ArrayList<ViceArticle> list) {
        super.onPostExecute(list);
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

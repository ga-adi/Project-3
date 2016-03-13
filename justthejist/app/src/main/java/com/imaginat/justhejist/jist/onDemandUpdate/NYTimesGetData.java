package com.imaginat.justhejist.jist.onDemandUpdate;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.imaginat.justhejist.jist.DBHelper.TopStoryDBHelper;
import com.imaginat.justhejist.jist.api.nyt.NYTApi;
import com.imaginat.justhejist.jist.models.Medium;
import com.imaginat.justhejist.jist.models.NewsStory;
import com.imaginat.justhejist.jist.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nat on 3/9/16.
 */
public class NYTimesGetData extends AsyncTask<String, Void, List<NewsStory>> {

  public interface NYTimesDataReceivedInterface{
    void onCompleted(List<NewsStory>newStories);
    // `getContext' is a pretty common method
    Context resolveContext();
  }

  NYTimesDataReceivedInterface mNYTimesDataReceivedInterface=null;
  public NYTimesGetData(NYTimesDataReceivedInterface a) { mNYTimesDataReceivedInterface = a; }


  @Override
  protected List<NewsStory> doInBackground(String... params) {
    String section = params[0];


    try {
      List<NewsStory> stories =  NYTApi.getStories(section);
      // TODO(boloutaredoubeni): DB operation
      for (NewsStory story : stories) {
        ContentValues values = new ContentValues();
        List<String> keywords = new ArrayList<>();
        if(story.getKeywords()!=null){
          keywords.addAll(story.getKeywords());
        }

        List<String> multimediaJSON = new ArrayList<>();
        for (final Medium medium : story.getMedia()) {
          multimediaJSON.add(medium.toJSON());
        }
        String mediaJSONArray = '[' + Utils.joinString(multimediaJSON) + ']';
        String condensedKeywords = Utils.joinString(keywords);
        values.put(TopStoryDBHelper.COL_SECTION, story.getSection());
        values.put(TopStoryDBHelper.COL_SUBSECTION, "subsection");
        values.put(TopStoryDBHelper.COL_ABSTRACT, story.getSummary());
        values.put(TopStoryDBHelper.COL_BYLINE, story.getAuthor());
        values.put(TopStoryDBHelper.COL_UPDATE, "update");
        values.put(TopStoryDBHelper.COL_CREATED, "created");
        values.put(TopStoryDBHelper.COL_TITLE, story.getTitle());
        values.put(TopStoryDBHelper.COL_PUBLISHED, "published");
        values.put(TopStoryDBHelper.COL_KEYWORDS, condensedKeywords);
        values.put(TopStoryDBHelper.COL_MULTIMEDIA, multimediaJSON.size() > 0 ? mediaJSONArray : "\"\"");
        values.put(TopStoryDBHelper.COL_URL, story.getUrl());
        TopStoryDBHelper
            .getInstance(mNYTimesDataReceivedInterface.resolveContext())
            .addArticle(values);
      }
      return stories;
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }

  @Override
  protected void onPostExecute(List<NewsStory> stories) {
    super.onPostExecute(stories);
    if (stories == null) {
      Toast.makeText(mNYTimesDataReceivedInterface.resolveContext(), "ERROR: no connection found ",
          Toast.LENGTH_SHORT)
          .show();
    }
    mNYTimesDataReceivedInterface.onCompleted(stories);
    // mArrayAdapter.notifyDataSetChanged();
  }

}

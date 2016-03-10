package com.imaginat.justhejist.jist.onDemandUpdate;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.imaginat.justhejist.jist.api.nyt.JSONParser;
import com.imaginat.justhejist.jist.api.nyt.NYTApi;
import com.imaginat.justhejist.jist.api.nyt.NYTService;
import com.imaginat.justhejist.jist.api.nyt.Section;
import com.imaginat.justhejist.jist.models.NewsStory;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by nat on 3/9/16.
 */
public class NYTimesGetData extends AsyncTask<String, Void, List<NewsStory>> {
  String data = null;
  Context mContext;

  public NYTimesGetData(Activity a) { mContext = a; }

  @Override
  protected List<NewsStory> doInBackground(String... params) {
    String section = params[0];
    Retrofit retrofit = new Retrofit.Builder().baseUrl(NYTApi.BASE_URL).build();
    NYTService service = retrofit.create(NYTService.class);
    try {
      Call<ResponseBody> call = service.getTopStories(Section.TECHNOLOGY);
      ResponseBody body = call.execute().body();
      String json = body.string();
      return JSONParser.getStoriesFrom(json);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }

  @Override
  protected void onPostExecute(List<NewsStory> stories) {
    super.onPostExecute(stories);
    if (stories == null) {
      Toast.makeText(mContext, "ERROR: no connection found ",
          Toast.LENGTH_SHORT)
          .show();
    }
    // TODO(programmingnat): pass into adapter
  }

}

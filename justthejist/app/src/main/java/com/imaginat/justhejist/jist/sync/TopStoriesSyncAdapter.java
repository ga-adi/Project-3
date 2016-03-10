package com.imaginat.justhejist.jist.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class TopStoriesSyncAdapter extends AbstractThreadedSyncAdapter {

  private static final String AUTHORITY =
      "com.imaginat.justthejist.jist"; // TODO(boloutaredoubeni): this may be
                                       // wrong
  private static final String PREFIX = "content://" + AUTHORITY + '/';

  public TopStoriesSyncAdapter(Context context, boolean autoInitialize) {
    super(context, autoInitialize);
  }

  @Override
  public void onPerformSync(Account account, Bundle extras, String authority,
                            ContentProviderClient provider,
                            SyncResult syncResult) {
    SyncResult result = new SyncResult();
    try {
      // TODO(boloutaredoubeni): do the sync
      // pass the bundle data and get it
      // delete the old data
      // insert the new stuff
      //      insertTopStories(provider);
    } catch (Throwable e) {
      e.printStackTrace();
      result.hasHardError();
    }
  }

  /**
   * Get the data from the NYT API and put it in the database
   * @param client
   */
  //  private void insertTopStories(ContentProviderClient client) throws
  //  IOException, Exception{
  //    Retrofit retrofit = new Retrofit.Builder()
  //        .baseUrl(NYTApi.BASE_URL)
  //        .addConverterFactory(GsonConverterFactory.create())
  //        .build();
  //    NYTService service = retrofit.create(NYTService.class);
  //    // NOTE(boloutaredoubeni): trying out one call now ...
  //    Call<TopStoriesResponseEntity> call =
  //    service.listResults(Section.TECHNOLOGY);
  //    TopStoriesResponseEntity topStoriesResponseEntity =
  //    call.execute().body();
  //    List<Result> results = topStoriesResponseEntity.getResults();
  //    for (Result result : results) {
  //      if (result.getSection().equals( Section.TECHNOLOGY)) {
  //        throw new Exception("Wrong section");
  //      }
  //      Log.v("Title", result.getTitle());
  //      Log.v("URL", result.getUrl());
  ////      List<Multimedia> media = Arrays.asList(result.getMultimedia());
  ////      Log.v("Media", "It is " + media.size());
  //    }
  //  }

  private void deleteOldStories(ContentProviderClient client) {}
}

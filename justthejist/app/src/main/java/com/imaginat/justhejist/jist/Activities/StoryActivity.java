package com.imaginat.justhejist.jist.Activities;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.imaginat.justhejist.jist.R;
import com.imaginat.justhejist.jist.api.nyt.WebScraper;
import com.imaginat.justhejist.jist.models.NewsStory;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class StoryActivity extends AppCompatActivity {

  private TextView mTextView;
  private ImageView mImageView;
  private NewsStory mStory;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_story);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);


//    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//    fab.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show();
//      }
//    });
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    NewsStory.Builder builder = new NewsStory.Builder();
    Bundle bundle = getIntent().getExtras();
    mStory = builder
        .url(bundle.getString("URL"))
        .author(bundle.getString("AUTHOR"))
        .title(bundle.getString("TITLE"))
        .build();

    String picUrl = bundle.getString("PIC_URL");
    mTextView = (TextView)findViewById(R.id.story_txt);
    mImageView = (ImageView) findViewById(R.id.header_img);
    Picasso.with(this)
        .load(picUrl)
        .error(R.drawable.ted_cruz2_nyt)
        .fit()
        .centerCrop()
        .into(mImageView);

    ((CollapsingToolbarLayout)findViewById(R.id.toolbar_layout)).setTitle(mStory.getTitle());

    String shareURL = bundle.getString("URL");
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(shareURL))
                    .build();

            final ShareButton postLinkButton =
            (ShareButton)findViewById(R.id.fb_share_button);
            postLinkButton.setShareContent(content);

  }

  @Override
  protected void onResume() {
    super.onResume();
    new HTMLParserTask().execute(mStory.getUrl());
  }

  private class HTMLParserTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
      String url = params[0];
      try {
        return WebScraper.parseHTML(url);
      } catch (IOException e) {
        return e.getMessage();
      }
    }

    @Override
    protected void onPostExecute(String s) {
      super.onPostExecute(s);
      mTextView.setText(s);
    }
  }
}

package com.java.rongyilang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.java.rongyilang.database.DataBase;
import com.java.rongyilang.database.MyData;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

//    SimpleExoPlayer absPlayerInternal;
//    PlayerView pvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_detail);

        MaterialToolbar toolbar = findViewById(R.id.detail_top_bar);
        Menu menu = toolbar.getMenu();

        MenuItem menuItemFav = menu.getItem(0);
        MenuItem menuItemClose = menu.getItem(1);

        String mTitle = getIntent().getStringExtra("Title");
        String mTime = getIntent().getStringExtra("Time");
        String mAuthor = getIntent().getStringExtra("Author");
        String mText = getIntent().getStringExtra("Text");
        List<String> mImage = getIntent().getStringArrayListExtra("Image");
        String mID = getIntent().getStringExtra("ID");
        String mVideo = getIntent().getStringExtra("Video");
        String mCategory = getIntent().getStringExtra("Category");

        new Thread(()->{
            List<MyData> list = DataBase.getInstance(this).getDataUao().findDataByID(mID);
            MyData myData;
            if (list.size() == 0) {
                myData = new MyData(mID, mImage, mTime, mVideo, mTitle, mText,
                        mAuthor, mCategory, true, false);
            } else {
                myData = list.get(0);
                myData.isHistory = true;
                runOnUiThread(()->{
                    if (myData.isFavourite) menuItemFav.setIcon(R.drawable.ic_baseline_star_24);
                    else menuItemFav.setIcon(R.drawable.ic_baseline_star_outline_24);
                });
            }

            DataBase.getInstance(this).getDataUao().insertData(myData);
        }).start();

        TextView mTitleView = findViewById(R.id.detail_title);
        TextView mTimeView = findViewById(R.id.detail_time);
        TextView mTextView = findViewById(R.id.detail_text);
        TextView mAuthorView = findViewById(R.id.detail_author);
        LinearLayout mImageView = findViewById(R.id.detail_image_layout);

        mTitleView.setText(mTitle);
        mTimeView.setText(mTime);
        mAuthorView.setText(mAuthor);
        mTextView.setText(mText);

        if (mVideo.contains("http")) {
            Log.d("", "video url=[" + mVideo + "]");
            SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
            PlayerView playerView = findViewById(R.id.detail_video);
            playerView.setVisibility(View.VISIBLE);
            playerView.setPlayer(player);

            MediaItem mediaItem = MediaItem.fromUri(mVideo);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
        }

        for (int i = 0; i < mImage.size(); i++) {
            ImageView detailImage = (ImageView) getLayoutInflater()
                    .inflate(R.layout.layout_detail_image, mImageView, false);

            Log.d("", "URL=" + mImage.get(i));
            mImageView.addView(detailImage);
            Glide.with(this)
                    .load(mImage.get(i))
                    .into(detailImage);
        }

        menuItemFav.setOnMenuItemClickListener(menuItem -> {
            new Thread(()->{
                MyData nowData = DataBase.getInstance(this).getDataUao().findDataByID(mID).get(0);
                nowData.isFavourite = !nowData.isFavourite;
                DataBase.getInstance(this).getDataUao().updateData(nowData);
                runOnUiThread(()->{
                    if (nowData.isFavourite) menuItemFav.setIcon(R.drawable.ic_baseline_star_24);
                    else menuItemFav.setIcon(R.drawable.ic_baseline_star_outline_24);
                });

            }).start();
            return true;
        });

        menuItemClose.setOnMenuItemClickListener(menuItem -> {
            finish();
            return true;
        });

    }

//    protected void onCreate(Bundle savedInstanceState, String url) {
//
//        int appNameStringRes = R.string.app_name;
//
//        pvMain = findViewById(R.id.detail_video); // creating player view
//
//        TrackSelector trackSelectorDef = new DefaultTrackSelector();
//        absPlayerInternal = ExoPlayerFactory.newSimpleInstance(this, trackSelectorDef); //creating a player instance
//
//        String userAgent = Util.getUserAgent(this, this.getString(appNameStringRes));
//        DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(this,userAgent);
//        Uri uriOfContentUrl = Uri.parse(url);
//        MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);  // creating a media source
//
//        absPlayerInternal.prepare(mediaSource);
//        absPlayerInternal.setPlayWhenReady(true); // start loading video and play it at the moment a chunk of it is available offline
//
//        pvMain.setPlayer(absPlayerInternal); // attach surface to the view
//    }
//
//    private void pausePlayer(SimpleExoPlayer player) {
//        if (player != null) {
//            player.setPlayWhenReady(false);
//        }
//    }
//
//    private void playPlayer(SimpleExoPlayer player) {
//        if (player != null) {
//            player.setPlayWhenReady(true);
//        }
//    }
//
//    private void stopPlayer(){
//        pvMain.setPlayer(null);
//        absPlayerInternal.release();
//        absPlayerInternal = null;
//    }
//
//    private void seekTo(SimpleExoPlayer player, long positionInMS) {
//        if (player != null) {
//            player.seekTo(positionInMS);
//        }
//    }
}
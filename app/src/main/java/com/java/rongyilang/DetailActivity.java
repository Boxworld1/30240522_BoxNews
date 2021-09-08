package com.java.rongyilang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.util.Log;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.java.rongyilang.database.DataBase;
import com.java.rongyilang.database.MyData;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

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
}
package com.java.rongyilang.home.newslist;

import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.java.rongyilang.DetailActivity;
import com.java.rongyilang.home.newslist.placeholder.PlaceholderContent.PlaceholderItem;
import com.java.rongyilang.databinding.FragmentItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {

    private List<PlaceholderItem> mValues;
    public HomeItemAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    public void setItemAdapter(List<PlaceholderItem> items) {
        int preSize = this.mValues.size();
//        if (preSize > 0) {
//            mValues.clear();
//            notifyItemRangeChanged(0, preSize);
//        }
        mValues = items;
        notifyItemRangeChanged(0, items.size());
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String mTitle = holder.mItem.title;
        String mTime = holder.mItem.time;
        String mText = holder.mItem.text;
        String mAuthor = holder.mItem.author;
        List<String> mImage = holder.mItem.image;
        String mID = holder.mItem.id;
        String mVideo = holder.mItem.video;
        String mCategory = holder.mItem.category;
        boolean mIsHistory = holder.mItem.isHistory;


        holder.mTitleView.setText(mTitle);
        holder.mTimeView.setText(mTime);
        holder.mTextView.setText(mText);
        holder.mAuthorView.setText(mAuthor);

        if (mIsHistory) {
            holder.mTitleView.setTextColor(Color.parseColor("#828282"));
        } else {
            holder.mTitleView.setTextColor(Color.parseColor("#000000"));
        }

        if (mImage.size() == 0) {

            holder.mImageView.setVisibility(View.GONE);
            holder.mTextLayout.setVisibility(View.VISIBLE);
            holder.mImageLayout.setVisibility(View.GONE);

        }  else if (mImage.size() >= 4){

            holder.mTextLayout.setVisibility(View.GONE);
            holder.mImageLayout.setVisibility(View.VISIBLE);

            Glide.with(holder.itemView.getContext())
                    .load(mImage.get(0))
                    .centerCrop()
                    .into(holder.mImageView1);

            Glide.with(holder.itemView.getContext())
                    .load(mImage.get(1))
                    .centerCrop()
                    .into(holder.mImageView2);

        } else {

            holder.mImageView.setVisibility(View.VISIBLE);
            holder.mTextLayout.setVisibility(View.VISIBLE);
            holder.mImageLayout.setVisibility(View.GONE);

            Glide.with(holder.itemView.getContext())
                    .load(mImage.get(0))
                    .centerCrop()
                    .into(holder.mImageView);

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("Title", mTitle);
                intent.putExtra("Time", mTime);
                intent.putExtra("Text", mText);
                intent.putExtra("Author", mAuthor);
                intent.putStringArrayListExtra("Image", (ArrayList<String>) mImage);
                intent.putExtra("ID", mID);
                intent.putExtra("Video", mVideo);
                intent.putExtra("Category", mCategory);

                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitleView;
        public final TextView mTimeView;
        public final TextView mTextView;
        public final TextView mAuthorView;
        public final ImageView mImageView;
        public final ImageView mImageView1;
        public final ImageView mImageView2;
        public final LinearLayout mTextLayout;
        public final LinearLayout mImageLayout;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mTitleView = binding.itemTitle;
            mTimeView = binding.itemTime;
            mTextView = binding.itemText;
            mAuthorView = binding.itemAuthor;
            mImageView = binding.itemImage;
            mTextLayout = binding.itemTextLayout;
            mImageLayout = binding.itemImageLayout;
            mImageView1 = binding.itemImage1;
            mImageView2 = binding.itemImage2;
        }

    }

}


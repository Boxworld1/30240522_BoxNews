package com.java.rongyilang.search;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.java.rongyilang.DetailActivity;
import com.java.rongyilang.databinding.FragmentSearchItemBinding;
import com.java.rongyilang.search.placeholder.SearchPlaceholderContent;

import java.util.ArrayList;
import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> {

    private final List<SearchPlaceholderContent.SearchPlaceholderItem> mValues;

    public SearchItemAdapter(List<SearchPlaceholderContent.SearchPlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentSearchItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false));

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

        holder.mTitleView.setText(mTitle);
        holder.mTimeView.setText(mTime);
        holder.mTextView.setText(mText);
        holder.mAuthorView.setText(mAuthor);

        if (mImage.size() == 0) {
            holder.mImageView.setVisibility(View.GONE);
        } else {
            holder.mImageView.setVisibility(View.VISIBLE);
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
        public SearchPlaceholderContent.SearchPlaceholderItem mItem;


        public ViewHolder(FragmentSearchItemBinding binding) {
            super(binding.getRoot());
            mTitleView = binding.searchItemTitle;
            mTimeView = binding.searchItemTime;
            mTextView = binding.searchItemText;
            mAuthorView = binding.searchItemAuthor;
            mImageView = binding.searchItemImage;
        }

    }
}
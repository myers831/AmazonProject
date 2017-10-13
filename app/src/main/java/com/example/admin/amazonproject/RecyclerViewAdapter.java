package com.example.admin.amazonproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Admin on 10/13/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public static final String TAG = "recyclerViewAdapter";

    List<BookComplete> bookCompleteList = new ArrayList<>();
    Context context;

    public RecyclerViewAdapter(List<BookComplete> bookCompleteList) {
        this.bookCompleteList = bookCompleteList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.books_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookComplete bookComplete = bookCompleteList.get(position);
        holder.tvBookName.setText("Title: " + bookComplete.getTitle());
        holder.tvBookAuthor.setText("Author: " + bookComplete.getAuthor());
        Glide.with(context).load(bookComplete.getImageURL()).into(holder.ivBookImage);
    }

    @Override
    public int getItemCount() {
        return bookCompleteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBookImage;
        TextView tvBookName, tvBookAuthor;

        public ViewHolder(View itemView) {
            super(itemView);

            ivBookImage = itemView.findViewById(R.id.ivBookImage);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
        }
    }
}

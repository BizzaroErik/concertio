package com.concertioApp.com.concertioApp.viewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.concertioApp.concertio.R;

import java.util.LinkedList;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.WordViewHolder> {
    private final LinkedList<String> wordList;
    private LayoutInflater mInflater;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView wordItemView;
        final NewsListAdapter mAdapter;

        public WordViewHolder(View iView, NewsListAdapter adapter){
            super(iView);
            wordItemView = itemView.findViewById(R.id.article_body);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            String element = wordList.get(mPosition);
            wordList.set(mPosition, "Clicked! " + element);
            mAdapter.notifyDataSetChanged();
        }
    }

    public NewsListAdapter(Context context, LinkedList<String> wl){
        mInflater = LayoutInflater.from(context);
        this.wordList = wl;
    }
    @NonNull
    @Override
    public NewsListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.newslist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.WordViewHolder holder, int position) {
        String mCurrent = wordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }
}

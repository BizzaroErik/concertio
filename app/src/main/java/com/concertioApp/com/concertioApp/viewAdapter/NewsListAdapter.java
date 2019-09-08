package com.concertioApp.com.concertioApp.viewAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.concertioApp.com.concertioApp.objectAdapter.NewsItem;
import com.concertioApp.concertio.R;

import java.util.LinkedList;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.WordViewHolder> {
    private final LinkedList<NewsItem> newsList;
    private LayoutInflater mInflater;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //public final TextView wordItemView;
        public final TextView articleTitle;
        public final TextView articleBody;
        public final TextView articleDate;
        public final ImageView articleImage;
        public String articleURL;

        final NewsListAdapter mAdapter;

        public WordViewHolder(View iView, NewsListAdapter adapter){
            super(iView);
            //Todo: add article id's
            //wordItemView = itemView.findViewById(R.id.article_body);
            articleTitle= itemView.findViewById(R.id.article_title);
            articleBody = itemView.findViewById(R.id.article_body);
            articleDate = itemView.findViewById(R.id.article_age);
            articleImage = itemView.findViewById(R.id.article_image);
            articleURL = "";
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);

            //keep, may change to just adding linking functionality to title textviews
            /*articleTitle.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Uri webPage = Uri.parse(articleURL);
                    Context c = articleImage.getContext();
                    Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                    if(intent.resolveActivity(c.getPackageManager()) != null){
                        c.startActivity(intent);
                    }
                }
            });*/
        }


        //Done: add functionality to go to that url, like start implicit browser intent
        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            //NewsItem element = newsList.get(mPosition);
            //newsList.set(mPosition, "Clicked! " + element.toString());
            mAdapter.notifyDataSetChanged();
            Uri webPage = Uri.parse(articleURL);
            Context c = articleImage.getContext();
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            if(intent.resolveActivity(c.getPackageManager()) != null){
                c.startActivity(intent);
            }

        }

    }

    public NewsListAdapter(Context context, LinkedList<NewsItem> nl){
        mInflater = LayoutInflater.from(context);
        this.newsList = nl;
    }
    @NonNull
    @Override
    public NewsListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.newslist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListAdapter.WordViewHolder holder, int position) {
        NewsItem mCurrent = newsList.get(position);
        holder.articleTitle.setText(mCurrent.title);
        holder.articleBody.setText(mCurrent.desc);
        holder.articleDate.setText(mCurrent.age);
        holder.articleURL = mCurrent.url;
        //holder.wordItemView.setText("Text set");
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


}

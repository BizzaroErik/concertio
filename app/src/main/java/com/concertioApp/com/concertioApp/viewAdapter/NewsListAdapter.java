package com.concertioApp.com.concertioApp.viewAdapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.concertioApp.com.concertioApp.objectAdapter.NewsItem;
import com.concertioApp.concertio.R;
import java.util.ArrayList;
import java.util.Date;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.WordViewHolder> {
    private final ArrayList<NewsItem> newsList;
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
        }

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

    public NewsListAdapter(Context context, ArrayList<NewsItem> nl){
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
        holder.articleDate.setText(setDate(mCurrent.age));
        holder.articleURL = mCurrent.url;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public String setDate(String date){
        int dateLength = 10;
        DateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();

        String todaysFullDate = simpleDate.format(today);
        String todaysDate = todaysFullDate.substring(0, dateLength);
        String articleDate = date.substring(0, dateLength);

        if(todaysDate.equals(articleDate)){
            Log.d("AGE", "DATE CHECK: "+ todaysDate);
            String todayHours = todaysFullDate.substring(dateLength+1, dateLength+3);
            String articleHours = date.substring(dateLength+1, dateLength+3);
            int hours = Integer.parseInt(todayHours) - Integer.parseInt(articleHours);
            return hours + " hours ago";
        }
        else{
            String dayArticle = articleDate.substring(dateLength-2, dateLength);
            String dayToday = todaysDate.substring(dateLength-2, dateLength);
            int age = Integer.parseInt(dayArticle) - Integer.parseInt(dayToday);
            Log.d("AGE", "Days Difference: "+ age);
            return age +" days ago";
        }

    }
}

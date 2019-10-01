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

import com.concertioApp.com.concertioApp.objectAdapter.ConcertItem;
import com.concertioApp.concertio.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

//Todo:Add api functionality
//Todo:Add onTouchListener to ticket image
public class ConcertListAdapter extends RecyclerView.Adapter<ConcertListAdapter.ConcertViewHolder> {
    private final ArrayList<ConcertItem> concertList;
    private LayoutInflater mInflater;

    class ConcertViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //public final TextView concertItemView;
        final ConcertListAdapter mAdapter;
        public final TextView concertTitle;
        public final TextView concertLocation;
        public final TextView concertDate;
        public final ImageView concertTicket;
        public String ticketURL;

        public ConcertViewHolder(View iView, ConcertListAdapter adapter){
            super(iView);
            //concertItemView = itemView.findViewById(R.id.article_body);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);

            concertTitle = iView.findViewById(R.id.concert_title);
            //concertSubTitle = iView.findViewById(R.id.concert_subtitle);
            concertLocation = iView.findViewById(R.id.concert_location);
            concertDate = iView.findViewById(R.id.concert_date);
            concertTicket = iView.findViewById(R.id.ticket_link);
            concertTicket.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Uri webPage = Uri.parse(ticketURL);
                    Context c = v.getContext();
                    Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                    if(intent.resolveActivity(c.getPackageManager()) != null){
                        c.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            //String element = concertList.get(mPosition);
            //concertList.set(mPosition, "Clicked! " + element);
            mAdapter.notifyDataSetChanged();
        }
    }

    public ConcertListAdapter(Context context, ArrayList<ConcertItem> concertList){
        mInflater = LayoutInflater.from(context);
        this.concertList = concertList;
    }
    @NonNull
    @Override
    public ConcertListAdapter.ConcertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.concertlist_item, parent, false);
        return new ConcertViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcertListAdapter.ConcertViewHolder holder, int position) {
        ConcertItem ci = concertList.get(position);
        holder.concertTitle.setText(ci.title);
        //holder.concertSubTitle.setText(ci.desc);
        holder.concertLocation.setText(ci.location);
        holder.concertDate.setText(ci.date);
        holder.ticketURL = ci.urlToTickets;
        //String mCurrent = wordList.get(position);
        //holder.concertItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return concertList.size();
    }
}


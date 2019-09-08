package com.concertioApp.com.concertioApp.viewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.concertioApp.concertio.R;
import java.util.LinkedList;

//Todo:Add api functionality
//Todo:Add onTouchListener to ticket image
public class ConcertListAdapter extends RecyclerView.Adapter<ConcertListAdapter.ConcertViewHolder> {
    private final LinkedList<String> wordList;
    private LayoutInflater mInflater;

    class ConcertViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //public final TextView concertItemView;
        final ConcertListAdapter mAdapter;

        public ConcertViewHolder(View iView, ConcertListAdapter adapter){
            super(iView);
            //concertItemView = itemView.findViewById(R.id.article_body);
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

    public ConcertListAdapter(Context context, LinkedList<String> wl){
        mInflater = LayoutInflater.from(context);
        this.wordList = wl;
    }
    @NonNull
    @Override
    public ConcertListAdapter.ConcertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.concertlist_item, parent, false);
        return new ConcertViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcertListAdapter.ConcertViewHolder holder, int position) {
        String mCurrent = wordList.get(position);
        //holder.concertItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }
}


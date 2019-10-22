package com.android.onehuman.cleanlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.onehuman.cleanlauncher.R;
import com.android.onehuman.cleanlauncher.events.AlphabeticalMenu_Header_OnItemClickListener;
import com.android.onehuman.cleanlauncher.model.Header;
import com.android.onehuman.cleanlauncher.viewHolders.HeaderViewHolder;

import java.util.List;

public class AlphabeticalAdapter extends RecyclerView.Adapter<HeaderViewHolder> {

    private Context context;
    private List<Header> alphabetList;

    public AlphabeticalAdapter(Context c, List<Header> al) {
        this.context=c;
        this.alphabetList = al;
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alphabetical_header_item, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewHolder holder, int position) {
        holder.label.setText((alphabetList.get(position)).getLabel());
        holder.label.setOnClickListener(new AlphabeticalMenu_Header_OnItemClickListener(context, alphabetList.get(position)));


    }

    @Override
    public int getItemCount() {
        return alphabetList.size();
    }

}
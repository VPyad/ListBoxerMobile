package com.example.vpyad.myapplication3.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.vpyad.myapplication3.R;
import com.example.vpyad.myapplication3.models.ListItem;

import java.util.List;

/**
 * Created by vpyad on 08-Jan-18.
 */

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.MyViewHolder> {

    private AdapterView.OnItemClickListener onItemClickListener;

    private List<ListItem> itemsList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView itemTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemTextView = itemView.findViewById(R.id.text_of_item_in_list);
            itemTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }

    public ItemsListAdapter(AdapterView.OnItemClickListener onItemClickListener, List<ListItem> itemsList) {
        this.onItemClickListener = onItemClickListener;
        this.itemsList = itemsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ListItem listItem = itemsList.get(position);
        holder.itemTextView.setText(listItem.getItem());

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public ListItem getItem(int position) {
        return itemsList.get(position);
    }
}

package com.example.treasurehunt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class scoreListAdapter extends RecyclerView.Adapter<scoreListAdapter.FirstViewHolder> {

    private ArrayList<String> content;
    private int position;
    int r = 0;
    private int counter;

    public scoreListAdapter(ArrayList<String> content) {
        this.content = content;
    }

    @NonNull
    @Override
    public FirstViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Parameters correct? No!
        View v = inflater.inflate(R.layout.score_item_layout, parent, false);
        FirstViewHolder viewHolder = new FirstViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FirstViewHolder holder, int position) {
        holder.bind(content.get(position));
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public void removeAll() {
        content.clear();
        notifyDataSetChanged();
    }


    public class FirstViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private View itemView;

        public FirstViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = itemView.findViewById(R.id.score_list_item);
        }

        public void bind(final String dataString) {
            textView.setText(dataString);
        }
    }
}

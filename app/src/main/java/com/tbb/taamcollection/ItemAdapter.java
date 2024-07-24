package com.tbb.taamcollection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapater, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        if (holder.textViewLotNumber != null) {
            holder.textViewLotNumber.setText(String.valueOf(item.getLotNumber()));
        }
        if (holder.textViewName != null) {
            holder.textViewName.setText(item.getName());
        }
        if (holder.textViewDescription != null) {
            holder.textViewDescription.setText(item.getDescription());
        }
        if (holder.textViewPeriod != null) {
            holder.textViewPeriod.setText(item.getPeriod().getValue());
        }
        if (holder.textViewCategory != null) {
            holder.textViewCategory.setText(item.getCategory().getValue());
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLotNumber, textViewName, textViewDescription, textViewPeriod, textViewCategory;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLotNumber = itemView.findViewById(R.id.textViewLotNumber);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPeriod = itemView.findViewById(R.id.textViewPeriod);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
        }
    }
}

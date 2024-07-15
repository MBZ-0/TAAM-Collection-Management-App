package com.tbb.taamcollection;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    CheckBox itemCheckbox;
    TextView textViewLotNumber, textViewName, textViewDescription, textViewPeriod, textViewCategory;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        itemCheckbox = itemView.findViewById(R.id.item_checkbox);
        textViewLotNumber = itemView.findViewById(R.id.textViewLotNumber);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewDescription = itemView.findViewById(R.id.textViewDescription);
        textViewPeriod = itemView.findViewById(R.id.textViewPeriod);
        textViewCategory = itemView.findViewById(R.id.textViewCategory);
    }
}

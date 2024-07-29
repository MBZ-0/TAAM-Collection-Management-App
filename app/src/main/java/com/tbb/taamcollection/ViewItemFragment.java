package com.tbb.taamcollection;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class ViewItemFragment extends Fragment {

    public ViewItemFragment() {
        // Required empty public constructor
    }

    private Context context;
    LinkedList<Item> list = new LinkedList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_item, container, false);
        LinearLayout l = view.findViewById(R.id.displays);
        list.add(new Item());
        list.add(new Item());
        for(Item v:list) {
            View card = inflater.inflate(R.layout.view_item_child, l, false);
            TextView name = card.findViewById(R.id.item_name);
            name.setText(v.getName());
            TextView description = card.findViewById(R.id.item_name);
            description.setText(v.getDescription());
            ImageView image = card.findViewById(R.id.group_image);
//            image.setImage(v.getImg());
            //Uncomment later and deal with it

            TextView category = card.findViewById(R.id.item_category);
            category.setText(v.getCategory().getValue());
            TextView period = card.findViewById(R.id.item_period);
            period.setText(v.getPeriod().getValue());
        }

        return view;
    }

    public void addItem(){

    }

}
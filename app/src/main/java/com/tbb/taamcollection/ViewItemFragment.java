package com.tbb.taamcollection;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ViewItemFragment extends Fragment {

    public ViewItemFragment() {
        // Required empty public constructor
    }

    private Context context;
    SharedViewModel sharedViewModel;
    LinkedList<Item> list = new LinkedList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_item, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        LinearLayout l = view.findViewById(R.id.displays);
//        list.add(new Item());
//        list.add(new Item());
        //Get items
        HashMap<Integer, List<Boolean>> checkBoxState = sharedViewModel.getCheckBoxState().getValue();
        if (checkBoxState != null) {
            for (int id : checkBoxState.keySet()) {
                List<Boolean> state = checkBoxState.get(id);
                if (state != null && state.contains(true)) {
                    ItemDatabase db = (ItemDatabase)ItemDatabase.db;
                    Item itm = db.allItems.get(id);
                    list.add(itm);
                    //System.out.println(itm.getName());
                }
            }
        }


        for(Item v:list) {
            View card = inflater.inflate(R.layout.view_item_child, l, false);
            TextView name = card.findViewById(R.id.item_name);
            name.setText(v.getName());
            TextView description = card.findViewById(R.id.item_description);
            description.setText(v.getDescription());
            ImageView image = card.findViewById(R.id.group_image);
//            image.setImage(v.getImg());
            //Uncomment later and deal with it

            TextView category = card.findViewById(R.id.item_category);
            category.setText(v.getCategory().getValue());
            TextView period = card.findViewById(R.id.item_period);
            period.setText(v.getPeriod().getValue());
            l.addView(card);
        }

        return view;
    }

    public void addItem(){

    }

}
package com.tbb.taamcollection;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SearchItemFragment extends Fragment {
    ItemDatabase db;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_item_fragment, container, false);
        Button searchButton = view.findViewById(R.id.searchButton);
        TextInputEditText lotNum = view.findViewById(R.id.lotNum);
        TextInputEditText nameItem = view.findViewById(R.id.nameItem);
        TextInputEditText categoryItem = view.findViewById(R.id.categoryItem);
        TextInputEditText periodItem = view.findViewById(R.id.periodItem);
        db = new ItemDatabase("items");
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String lot = lotNum.getText().toString();
                String name = nameItem.getText().toString();
                String category = categoryItem.getText().toString();
                String period = periodItem.getText().toString();
                if(!lot.isEmpty() || !name.isEmpty() || !category.isEmpty() || !period.isEmpty()){
                    LinkedList<Item> itemsList = db.search(name,lot,category,period);
                }



            }
        });




        return view;
    }






}

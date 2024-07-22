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
import java.util.List;


public class SearchItemFragment extends Fragment {
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_item_fragment, container, false);
        Button searchButton = view.findViewById(R.id.searchButton);
        TextInputEditText lotNum = view.findViewById(R.id.lotNum);
        TextInputEditText nameItem = view.findViewById(R.id.nameItem);
        TextInputEditText categoryItem = view.findViewById(R.id.categoryItem);
        TextInputEditText periodItem = view.findViewById(R.id.periodItem);

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String lot = lotNum.getText().toString();
                String name = nameItem.getText().toString();
                String category = categoryItem.getText().toString();
                String period = periodItem.getText().toString();
                FirebaseDatabase db = FirebaseDatabase.getInstance("https://taam-collection-management-app-default-rtdb.firebaseio.com");
                DatabaseReference items = db.getReference("items");

                // This code collects all relevant items and stores it in a list
                items.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Item> filteredItems = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Item item = snapshot.getValue(Item.class);
                            boolean match = true;
                            if (Integer.parseInt(lot) != item.getLotNumber()){
                                match = false;
                            }
                            if (!name.isEmpty() && !(name.equals(item.getName()))){
                                match = false;

                            }
                            if (!category.isEmpty() && !(category.equals(item.getCategory().getValue()))){
                                match = false;
                            }
                            if (!period.isEmpty() && !(period.equals(item.getPeriod().getValue()))){
                                match = false;
                            }
                            if(match){
                                filteredItems.add(item);
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });





            }
        });




        return view;
    }






}

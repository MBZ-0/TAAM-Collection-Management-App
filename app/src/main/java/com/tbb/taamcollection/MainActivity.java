package com.tbb.taamcollection;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private DatabaseReference itemsRef;
    private boolean isDataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getRecord();
    }

    private void getRecord() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);

        itemsRef = FirebaseDatabase.getInstance("https://taam-collection-management-app-default-rtdb.firebaseio.com/").getReference("db").child("items");
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!isDataLoaded) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                            if (Objects.equals(ds.getKey(), "id")) {
                                Item item = ds.getValue(Item.class);
                                itemList.add(item);
                            }
                    }
                    itemAdapter.notifyDataSetChanged();
                    isDataLoaded = true;
                }
                // progressBar.dismiss(); // Uncomment if progressBar is defined
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

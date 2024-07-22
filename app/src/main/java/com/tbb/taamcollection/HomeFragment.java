package com.tbb.taamcollection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    ExpandableListView expandableListView;
    CustomExpandableListAdapter expandableListAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<Integer> listDataLotNumbers;
    List<Integer> listDataImages;
    private DatabaseReference itemsRef;
    private ItemDatabase itemDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        expandableListView = view.findViewById(R.id.expandableListView);

        // Initialize Firebase reference
        itemDatabase = new ItemDatabase("items");

        // Initialize lists
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataLotNumbers = new ArrayList<>();
        listDataImages = new ArrayList<>();

        // Initialize adapter and set it to the ExpandableListView
        expandableListAdapter = new CustomExpandableListAdapter(getContext(), listDataHeader, listDataChild, listDataLotNumbers, listDataImages);
        expandableListView.setAdapter(expandableListAdapter);

        // Fetch data from Firebase
        prepareListDataFromDatabase();

        Button buttonAdmin = view.findViewById(R.id.admin);
        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new LoginFragment());
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void prepareListDataFromDatabase() {
        itemsRef = itemDatabase.database;
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listDataHeader.clear();
                listDataChild.clear();
                listDataLotNumbers.clear();
                listDataImages.clear();
                int i=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    if (item != null) {
                        listDataHeader.add(item.getName() != null ? item.getName() : "Unknown Name");
                        listDataLotNumbers.add(item.getLotNumber() != 0 ? item.getLotNumber() : 0);
                        List<String> childList = new ArrayList<>();
                        childList.add(item.getCategory() != null ? item.getCategory().getValue() : "Unknown Category");
                        childList.add(item.getPeriod() != null ? item.getPeriod().getValue() : "Unknown Period");
                        childList.add(item.getDescription() != null ? item.getDescription() : "Unknown Description");
                        listDataChild.put(listDataHeader.get(i), childList);
                        listDataImages.add(R.drawable.default_image); // Replace with actual image handling logic
                        i++;
                    } else {
                        Log.d(TAG, "Fetched item is null");
                    }
                }

                expandableListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }
}

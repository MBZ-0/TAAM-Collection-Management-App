package com.tbb.taamcollection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

public class CustomExpandableListFragment extends Fragment {

    private static final String TAG = "CustomExpandableListFragment";

    ExpandableListView expandableListView;
    CustomExpandableListAdapter expandableListAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<Integer> listDataLotNumbers;
    List<Integer> listDataImages;
    List<Integer> listIds;
    private DatabaseReference itemsRef;
    private ItemDatabase itemDatabase;
    SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_expandable_list_fragment, container, false);

        expandableListView = view.findViewById(R.id.expandableListView);

        // Initialize Firebase reference
        itemDatabase = new ItemDatabase("items");

        // Initialize lists
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataLotNumbers = new ArrayList<>();
        listDataImages = new ArrayList<>();
        listIds = new ArrayList<>();

        // Initialize ViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Check if there are arguments passed to the fragment
        Bundle args = getArguments();
        if (args != null) {
            LinkedList<Item> itemsList = (LinkedList<Item>) args.getSerializable("itemsList");
            if (itemsList != null) {
                prepareListDataFromItemsList(itemsList);
            }
        } else {
            // Fetch data from Firebase if no arguments are passed
            prepareListDataFromDatabase();
        }

        sharedViewModel.getCheckBoxState().observe(getViewLifecycleOwner(), new Observer<HashMap<Integer, List<Boolean>>>() {
            @Override
            public void onChanged(HashMap<Integer, List<Boolean>> state) {
                expandableListAdapter = new CustomExpandableListAdapter(getActivity(), listDataHeader, listDataChild, listDataLotNumbers, listDataImages, listIds, state);
                expandableListView.setAdapter(expandableListAdapter);
            }
        });

        sharedViewModel.getCheckBoxState().observe(getViewLifecycleOwner(), new Observer<HashMap<Integer, List<Boolean>>>() {
            @Override
            public void onChanged(HashMap<Integer, List<Boolean>> state) {
                expandableListAdapter = new CustomExpandableListAdapter(getActivity(), listDataHeader, listDataChild, listDataLotNumbers, listDataImages, listIds, state);
                expandableListView.setAdapter(expandableListAdapter);
            }
        });

        return view;
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
                listIds.clear();
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item item = snapshot.getValue(Item.class);
                    if (item != null) {
                        listDataHeader.add(item.getName() != null ? item.getName() : "Unknown Name");
                        listDataLotNumbers.add(item.getLotNumber() != 0 ? item.getLotNumber() : 0);
                        listIds.add(item.getId());
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

                if (sharedViewModel.getCheckBoxState().getValue() == null) {
                    HashMap<Integer, List<Boolean>> initialCheckBoxState = new HashMap<>();
                    for (int id : listIds) {
                        initialCheckBoxState.put(id, new ArrayList<>(Collections.nCopies(1, false)));
                    }
                    sharedViewModel.setCheckBoxState(initialCheckBoxState);
                }

                expandableListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }
  
    public CustomExpandableListAdapter getAdapter() {
        return expandableListAdapter;
    }

    public DatabaseReference getDatabaseReference() {
        return itemsRef;
    }

    public void removeItems() {
        HashMap<Integer, List<Boolean>> state = sharedViewModel.getCheckBoxState().getValue();
        if (state != null) {
            List<Integer> idsToRemove = new ArrayList<>();
            for (int id : listIds) {
                List<Boolean> groupCheckBoxStates = state.get(id);
                if (groupCheckBoxStates != null && groupCheckBoxStates.get(0)) {
                    idsToRemove.add(id);
                } else {
                    Log.d(TAG, "Item with ID " + id + " is not checked or state is null");
                }
            }

            for (int id : idsToRemove) {
                Log.d(TAG, "Attempting to remove item with ID: " + id);
                itemDatabase.remove(id);
                Log.d(TAG, "Removed item with ID: " + id);
            }

            // Fetch data again after deletion to update the list
            prepareListDataFromDatabase();
        }
    }
}

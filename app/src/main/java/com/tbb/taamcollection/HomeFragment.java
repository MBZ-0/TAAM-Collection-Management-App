package com.tbb.taamcollection;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    ExpandableListView expandableListView;
    CustomExpandableListAdapter expandableListAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        expandableListView = view.findViewById(R.id.expandableListView);
        prepareListData();
        expandableListAdapter = new CustomExpandableListAdapter(getContext(), listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);

        Button buttonAdmin = view.findViewById(R.id.admin); // Add this line
        buttonAdmin.setOnClickListener(new View.OnClickListener() { // Add this block
            @Override
            public void onClick(View v) {
                loadFragment(new LoginFragment()); // Replace with your AdminFragment
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

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding group data
        listDataHeader.add("Group 1");
        listDataHeader.add("Group 2");

        // Adding child data
        List<String> group1 = new ArrayList<>();
        group1.add("Category");
        group1.add("Period");
        group1.add("Description");
        group1.add("Image/Video");

        List<String> group2 = new ArrayList<>();
        group2.add("Category");
        group2.add("Period");
        group2.add("Description");
        group2.add("Image/Video");

        listDataChild.put(listDataHeader.get(0), group1);
        listDataChild.put(listDataHeader.get(1), group2);
    }
}
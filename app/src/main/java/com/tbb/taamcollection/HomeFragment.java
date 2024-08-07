package com.tbb.taamcollection;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView searchBasedView;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void onLogin(Button back, Button add, Button remove, Button report, Button admin){
        back.setVisibility(View.VISIBLE);
        add.setVisibility(View.VISIBLE);
        remove.setVisibility(View.VISIBLE);
        report.setVisibility(View.VISIBLE);
        admin.setVisibility(View.INVISIBLE);
    }

    public void onLogout(Button back, Button add, Button remove, Button report, Button admin){
        System.out.println("LOGOUT");
        AdminDatabase.loggedIn = false;
        back.setVisibility(View.INVISIBLE);
        add.setVisibility(View.INVISIBLE);
        remove.setVisibility(View.INVISIBLE);
        report.setVisibility(View.INVISIBLE);
        admin.setVisibility(View.VISIBLE);
    }

    SharedViewModel sharedViewModel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Button buttonAdmin = view.findViewById(R.id.admin);
        Button buttonBack = view.findViewById(R.id.adminBack);
        Button buttonAdd = view.findViewById(R.id.add);
        Button buttonRemove = view.findViewById(R.id.remove);
        Button buttonReport = view.findViewById(R.id.report);
        Button buttonView = view.findViewById(R.id.view);
        Button buttonSearch = view.findViewById(R.id.search);
        Button buttonBackFromSearch = view.findViewById(R.id.searchHomeScreenBack);
        searchBasedView = view.findViewById(R.id.searchBasedText);

        if (AdminDatabase.loggedIn) {
            onLogin(buttonBack, buttonAdd, buttonRemove, buttonReport, buttonAdmin);
        } else {
            onLogout(buttonBack, buttonAdd, buttonRemove, buttonReport, buttonAdmin);
        }

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AdminLoginView());
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AdminDatabase.loggedIn) {
                    loadFragment(new AddItemFragment());
                }
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AdminDatabase.loggedIn) {
                    loadFragment(new RemoveItemFragment());
                }
            }
        });

        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AdminDatabase.loggedIn) {
                    loadFragment(new ReportItemFragment());
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AdminDatabase.loggedIn) {
                    onLogout(buttonBack, buttonAdd, buttonRemove, buttonReport, buttonAdmin);
                }
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SearchItemFragment());
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<Integer, List<Boolean>> checkBoxState = sharedViewModel.getCheckBoxState().getValue();
                boolean valid = true;
                for (int id : checkBoxState.keySet()) {
                    List<Boolean> state = checkBoxState.get(id);
                    if (state != null && state.contains(true)) {
                        loadFragment(new ViewItemFragment());
                        valid = false;
                    }
                }
                if(valid){
                        TextView err = view.findViewById(R.id.noSelect);
                        err.setVisibility(View.VISIBLE);
                }


            }
        });

        // Handle back button to go to the previous screen
        buttonBackFromSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        // Check if there are search results passed to this fragment
        Bundle args = getArguments();
        if (args != null) {
            LinkedList<Item> searchResults = (LinkedList<Item>) args.getSerializable("searchResults");
            String searchBasedText = args.getString("searchBasedText");
            if (searchResults != null) {
                // Replace the expandable list with search results
                CustomExpandableListFragment searchResultsFragment = new CustomExpandableListFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemsList", searchResults);
                searchResultsFragment.setArguments(bundle);

                searchBasedView.setText(searchBasedText);

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.expandableListView, searchResultsFragment,
                        "CustomExpandableListFragment");
                transaction.commit();

                // Make the back button visible
                buttonBackFromSearch.setVisibility(View.VISIBLE);
                searchBasedView.setVisibility(View.VISIBLE);
            } else {
                // Make the back button invisible
                buttonBackFromSearch.setVisibility(View.GONE);
                searchBasedView.setVisibility(View.GONE);

                // Add the CustomExpandableListFragment to the HomeFragment layout
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.expandableListView, new CustomExpandableListFragment(),
                        "CustomExpandableListFragment");
                transaction.commit();
            }
        } else {
            // Make the back button invisible
            buttonBackFromSearch.setVisibility(View.GONE);
            searchBasedView.setVisibility(View.GONE);

            // Add the CustomExpandableListFragment to the HomeFragment layout
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.expandableListView, new CustomExpandableListFragment(),
                    "CustomExpandableListFragment");
            transaction.commit();
        }

        return view;
    }

    public void setSearchBasedTextView(String searchBasedText) {
        if (searchBasedView != null) {
            searchBasedView.setText(searchBasedText);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

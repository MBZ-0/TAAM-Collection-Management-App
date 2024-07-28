package com.tbb.taamcollection;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Button buttonAdmin = view.findViewById(R.id.admin);
        Button buttonBack = view.findViewById(R.id.adminBack);
        Button buttonAdd = view.findViewById(R.id.add);
        Button buttonRemove = view.findViewById(R.id.remove);
        Button buttonReport = view.findViewById(R.id.report);
        Button buttonView = view.findViewById(R.id.view);
        Button buttonSearch = view.findViewById(R.id.search);

        if(AdminDatabase.loggedIn){
            onLogin(buttonBack, buttonAdd, buttonRemove, buttonReport, buttonAdmin);
        } else {
            onLogout(buttonBack, buttonAdd, buttonRemove, buttonReport, buttonAdmin);
        }

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AdminLoginFragment());
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdminDatabase.loggedIn) {
                    loadFragment(new AddItemFragment());
                }
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdminDatabase.loggedIn) {
                    loadFragment(new RemoveItemFragment());
                }
            }
        });

        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdminDatabase.loggedIn) {
                    loadFragment(new ReportItemFragment());
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdminDatabase.loggedIn) {
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
                loadFragment(new ViewItemFragment());
            }
        });

        // Add the CustomExpandableListFragment to the HomeFragment layout
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.expandableListView, new CustomExpandableListFragment(), "CustomExpandableListFragment");
        transaction.commit();

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

package com.tbb.taamcollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    //REPLACE WITH SOMETHING ELSE
    boolean loggedIn = false;
    public void onLogin(Button back, Button add, Button remove, Button report, Button admin){
        System.out.println("LOGIN");
        loggedIn = true;
        back.setVisibility(View.VISIBLE);
        add.setVisibility(View.VISIBLE);
        remove.setVisibility(View.VISIBLE);
        report.setVisibility(View.VISIBLE);
        admin.setVisibility(View.INVISIBLE);
    }
    public void onLogout(Button back, Button add, Button remove, Button report, Button admin){
        System.out.println("LOGOUT");
        loggedIn = false;
        back.setVisibility(View.INVISIBLE);
        add.setVisibility(View.INVISIBLE);
        remove.setVisibility(View.INVISIBLE);
        report.setVisibility(View.INVISIBLE);
        admin.setVisibility(View.VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        Button buttonRecyclerView = view.findViewById(R.id.buttonRecyclerView);
        Button buttonScroller = view.findViewById(R.id.buttonScroller);
        Button buttonSpinner = view.findViewById(R.id.buttonSpinner);
        Button buttonManageItems = view.findViewById(R.id.buttonManageItems);
        //CREATE NEW BUTTONS
        Button buttonAdmin = view.findViewById(R.id.admin);
        Button buttonBack = view.findViewById(R.id.adminBack);
        Button buttonAdd = view.findViewById(R.id.add);
        Button buttonRemove = view.findViewById(R.id.remove);
        Button buttonReport = view.findViewById(R.id.report);
        Button buttonView = view.findViewById(R.id.view);
        Button buttonSearch = view.findViewById(R.id.search);

        if(loggedIn){
            onLogout(buttonBack, buttonAdd, buttonRemove, buttonReport, buttonAdmin);
        }else {
            onLogout(buttonBack, buttonAdd, buttonRemove, buttonReport, buttonAdmin);
        }

        buttonRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new RecyclerViewFragment());
            }
        });

        buttonScroller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ScrollerFragment());
            }
        });

        buttonSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SpinnerFragment());
            }
        });

        buttonManageItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ManageItemsFragment());
            }
        });

        buttonAdmin.setOnClickListener(new View.OnClickListener() { // Add this block
            @Override
            public void onClick(View v) {
                //TODO: COMMENT OUT following line
                onLogin(buttonBack, buttonAdd, buttonRemove, buttonReport, buttonAdmin);
                loadFragment(new LoginFragment()); // Replace with your AdminFragment
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() { // Add this block
            @Override
            public void onClick(View v) {
                if(loggedIn) {
                    //TODO: Replace with ADD fragment
                    //loadFragment(new LoginFragment());
                }
            }
        });
        buttonRemove.setOnClickListener(new View.OnClickListener() { // Add this block
            @Override
            public void onClick(View v) {
                if(loggedIn) {
                    //TODO: Replace with REMOVE fragment
                    //loadFragment(new LoginFragment());
                }
            }
        });
        buttonReport.setOnClickListener(new View.OnClickListener() { // Add this block
            @Override
            public void onClick(View v) {
                if(loggedIn) {
                    //TODO: Replace with REPORT fragment
                    //loadFragment(new LoginFragment());
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() { // Add this block
            @Override
            public void onClick(View v) {
                if(loggedIn) {
                    onLogout(buttonBack, buttonAdd, buttonRemove, buttonReport, buttonAdmin);
                }
            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener() { // Add this block
            @Override
            public void onClick(View v) {
                //TODO: Replace with SEARCH fragment
                //loadFragment(new LoginFragment());
            }
        });
        buttonView.setOnClickListener(new View.OnClickListener() { // Add this block
            @Override
            public void onClick(View v) {
                //TODO: Replace with VIEW fragment
                //loadFragment(new LoginFragment());
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
}

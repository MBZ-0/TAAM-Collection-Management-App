package com.tbb.taamcollection;

import android.os.Bundle;
import androidx.annotation.NonNull;
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
        AdminBase.loggedIn = false;
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
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        Button buttonRecyclerView = view.findViewById(R.id.buttonRecyclerView);
        Button buttonScroller = view.findViewById(R.id.buttonScroller);
        Button buttonSpinner = view.findViewById(R.id.buttonSpinner);
        Button buttonManageItems = view.findViewById(R.id.buttonManageItems);
        Button buttonAdmin = view.findViewById(R.id.admin);
        Button buttonBack = view.findViewById(R.id.adminBack);
        Button buttonAdd = view.findViewById(R.id.add);
        Button buttonRemove = view.findViewById(R.id.remove);
        Button buttonReport = view.findViewById(R.id.report);
        Button buttonView = view.findViewById(R.id.view);
        Button buttonSearch = view.findViewById(R.id.search);

        if(AdminBase.loggedIn){
            onLogin(buttonBack, buttonAdd, buttonRemove, buttonReport, buttonAdmin);
        } else {
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

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new LoginFragment()); // Replace with your AdminFragment
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdminBase.loggedIn) {
                    // Replace with ADD fragment
                    // loadFragment(new AddFragment());
                }
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdminBase.loggedIn) {
                    // Replace with REMOVE fragment
                    // loadFragment(new RemoveFragment());
                }
            }
        });

        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdminBase.loggedIn) {
                    // Replace with REPORT fragment
                    // loadFragment(new ReportFragment());
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdminBase.loggedIn) {
                    onLogout(buttonBack, buttonAdd, buttonRemove, buttonReport, buttonAdmin);
                }
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with SEARCH fragment
                // loadFragment(new SearchFragment());
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with VIEW fragment
                // loadFragment(new ViewFragment());
            }
        });

        // Add the CustomExpandableListFragment to the HomeFragment layout
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.expandableListView, new CustomExpandableListFragment());
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

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
import android.widget.TextView;

import java.util.List;

public class RemoveItemFragment extends Fragment {
    private TextView textViewDeleteText;
    private Button buttonConfirm, buttonCancel;

    List<Boolean> checkboxStates;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_item_fragment, container, false);

        textViewDeleteText = view.findViewById(R.id.textViewConfirmation);
        buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        textViewDeleteText.setText("Are you sure you want to delete " + "" + "? This action cannot be undone.");

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
            }
        });

        return view;
    }

    private void deleteItem() {
        Fragment homeFragment = getParentFragmentManager().findFragmentByTag("HomeFragment");
        if (homeFragment != null) {
            CustomExpandableListFragment fragment =
                    (CustomExpandableListFragment) homeFragment.getChildFragmentManager().findFragmentByTag("CustomExpandableListFragment");

            if (fragment != null) {
                fragment.removeItems();
            } else {
                System.out.println("CustomExpandableListFragment NOT FOUND!!!");
            }
        } else {
            System.out.println("HOME FRAGMENT NOT FOUND!!!");
        }

        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

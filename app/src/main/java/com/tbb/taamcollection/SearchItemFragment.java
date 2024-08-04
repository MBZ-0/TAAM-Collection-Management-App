package com.tbb.taamcollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.textfield.TextInputEditText;
import java.util.LinkedList;



public class SearchItemFragment extends Fragment {
    ItemDatabase db;
    private Spinner spinnerCategory, spinnerPeriod;
    private TextView errorField;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_item_fragment, container, false);
        Button searchButton = view.findViewById(R.id.searchButton);
        TextInputEditText lotNum = view.findViewById(R.id.lotNum);
        TextInputEditText nameItem = view.findViewById(R.id.nameItem);
        spinnerCategory = view.findViewById(R.id.spinnerSearchCategory);
        spinnerPeriod = view.findViewById(R.id.spinnerSeachPeriod);
        Button buttonBack = view.findViewById(R.id.buttonBack);
        errorField = view.findViewById(R.id.textViewError);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_array, R.layout.spinner);
        categoryAdapter.setDropDownViewResource(R.layout.spinner);
        spinnerCategory.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> periodAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.periods_array, R.layout.spinner);
        periodAdapter.setDropDownViewResource(R.layout.spinner);
        spinnerPeriod.setAdapter(periodAdapter);

        db = new ItemDatabase("items");

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lot = lotNum.getText().toString();
                String name = nameItem.getText().toString();

                ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.categories_array, R.layout.spinner);
                categoryAdapter.setDropDownViewResource(R.layout.spinner);
                spinnerCategory.setAdapter(categoryAdapter);

                String category = spinnerCategory.getSelectedItem().toString();
                String period = spinnerPeriod.getSelectedItem().toString();

                errorField.setText("");
                errorField.setTextColor(0xFFE20303);

                if (!lot.isEmpty() || !name.isEmpty() || !category.isEmpty() || !period.isEmpty()) {
                    LinkedList<Item> itemsList = db.search(name, lot, category, period);
                    passSearchResultsToHomeFragment(itemsList);
                }
                //
                else {
                    errorField.setText("Please fill in at least one field");
                }
            }
        });

        return view;
    }

    private void passSearchResultsToHomeFragment(LinkedList<Item> itemsList) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("searchResults", itemsList);
        homeFragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
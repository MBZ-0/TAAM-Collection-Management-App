package com.tbb.taamcollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.textfield.TextInputEditText;
import java.util.LinkedList;



public class SearchItemFragment extends Fragment {
    ItemDatabase db;
    private Spinner spinnerCategory, spinnerPeriod;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_item_fragment, container, false);
        Button searchButton = view.findViewById(R.id.searchButton);
        TextInputEditText lotNum = view.findViewById(R.id.lotNum);
        TextInputEditText nameItem = view.findViewById(R.id.nameItem);
        spinnerCategory = view.findViewById(R.id.spinnerSearchCategory);
        spinnerPeriod = view.findViewById(R.id.spinnerSeachPeriod);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_array, R.layout.spinner);
        categoryAdapter.setDropDownViewResource(R.layout.spinner);
        spinnerCategory.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> periodAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.periods_array, R.layout.spinner);
        periodAdapter.setDropDownViewResource(R.layout.spinner);
        spinnerPeriod.setAdapter(periodAdapter);

        db = new ItemDatabase("items");

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

                if (!lot.isEmpty() || !name.isEmpty() || !category.isEmpty() || !period.isEmpty()) {
                    LinkedList<Item> itemsList = db.search(name, lot, category, period);

                    CustomExpandableListFragment fragment = new CustomExpandableListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("itemsList", itemsList);
                    fragment.setArguments(bundle);

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }
}
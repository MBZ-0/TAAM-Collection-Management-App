package com.tbb.taamcollection;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemFragment extends Fragment {
    private static final String DEFAULT_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg";
    private EditText editTextLotNum, editTextName, editTextDescription, editTextImageUrl, editTextVideoUrl;
    private Spinner spinnerCategory, spinnerPeriod;
    private TextView errorField;
    private Button buttonAdd, buttonBack;

    private FirebaseDatabase db;
    private DatabaseReference itemsRef;
    private ItemDatabase itemdb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item_fragment, container, false);

        editTextLotNum = view.findViewById(R.id.editTextLotNum);
        editTextName = view.findViewById(R.id.editTextName);
        spinnerCategory = view.findViewById(R.id.spinnerAddCategory);
        spinnerPeriod = view.findViewById(R.id.spinnerAddPeriod);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextImageUrl = view.findViewById(R.id.editTextImageUrl);
        editTextVideoUrl = view.findViewById(R.id.editTextVideoUrl);
        buttonAdd = view.findViewById(R.id.buttonViewBack);
        buttonBack = view.findViewById(R.id.buttonAddReturn);
        errorField = view.findViewById(R.id.textViewError);

        ArrayAdapter<CharSequence> categoryadapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_array, R.layout.spinner);
        categoryadapter.setDropDownViewResource(R.layout.spinner);
        spinnerCategory.setAdapter(categoryadapter);

        ArrayAdapter<CharSequence> periodadapter = ArrayAdapter.createFromResource(getContext(),
                R.array.periods_array, R.layout.spinner);
        periodadapter.setDropDownViewResource(R.layout.spinner);
        spinnerPeriod.setAdapter(periodadapter);

        db = FirebaseDatabase.getInstance("https://b07-demo-summer-2024-default-rtdb.firebaseio.com/");
        itemdb = new ItemDatabase("items");

        errorField.setText("");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
            }
        });

        return view;
    }

    public static boolean isNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private void addItem() {
        String lotNumStr = editTextLotNum.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString().trim();
        String period = spinnerPeriod.getSelectedItem().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String imageUrl = editTextImageUrl.getText().toString().trim();
        String videoUrl = editTextVideoUrl.getText().toString().trim();

        errorField.setText("");
        errorField.setTextColor(0xFFE20303);

        if (lotNumStr.isEmpty() || name.isEmpty() || description.isEmpty()) {
            errorField.setText("Please fill in all Fields");
            return;
        }

        if (!isNumber(lotNumStr)) {
            errorField.setText("Lot Number should be a Number");
            return;
        }

        if (imageUrl.isEmpty()) {
            imageUrl = DEFAULT_IMAGE_URL;
        }

        errorField.setText("Item Added Successfully!");
        errorField.setTextColor(0xFF8BC34A);

        int lotNum = Integer.parseInt(lotNumStr);

        Category propercategory = Category.fromLabel(category);
        Period properperiod = Period.fromLabel(period);

        Item item = new Item(itemdb);
        item.setLotNumber(lotNum);
        item.setName(name);
        item.setDescription(description);
        item.setCategory(propercategory);
        item.setPeriod(properperiod);
        item.setImageUrl(imageUrl);
        item.setVideoUrl(videoUrl);

        itemdb.add(item);
        itemdb.updateDatabase();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
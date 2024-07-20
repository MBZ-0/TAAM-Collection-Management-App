package com.tbb.taamcollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemFragment extends Fragment {
    private EditText editTextLotNum, editTextName, editTextDescription;
    private Spinner spinnerCategory, spinnerPeriod;
    private TextView errorField;
    private Button buttonAdd;

    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        editTextLotNum = view.findViewById(R.id.editTextLotNum);
        editTextName = view.findViewById(R.id.editTextName);
        spinnerCategory = view.findViewById(R.id.spinnerAddCategory);
        spinnerPeriod = view.findViewById(R.id.spinnerAddPeriod);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        errorField = view.findViewById(R.id.textViewError);

        db = FirebaseDatabase.getInstance("https://b07-demo-summer-2024-default-rtdb.firebaseio.com/");

        errorField.setText("");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        return view;
    }

    private static boolean isNumber(String str) {
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

        errorField.setText("");

        if (lotNumStr.isEmpty() || name.isEmpty() || description.isEmpty()) {
            errorField.setText("Please fill in all Fields");
            return;
        }

        if (!isNumber(lotNumStr)) {
            errorField.setText("Lot Number should be a Number");
            return;
        }

        int lotNum = Integer.parseInt(lotNumStr);

        ItemDatabase itemdb = new ItemDatabase("items");
        Category propercategory = Category.fromLabel("");
        Period properperiod = Period.fromLabel("");

        Item item = new Item(itemdb);
        item.setLotNumber(lotNum);
        item.setName(name);
        item.setCategory(propercategory);
        item.setPeriod(properperiod);

        //itemsRef = db.getReference("categories/" + category);
        //String id = itemsRef.push().getKey();
        //Item item = new Item(id, title, author, genre, description);

//        itemsRef.child(id).setValue(item).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getContext(), "Failed to add item", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
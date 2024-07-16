package com.tbb.taamcollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemFragment extends Fragment {
    private EditText editTextLotNum, editTextName, editTextCatogory, editTextPeriod, editTextDescription;
    private Button buttonAdd;

    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        editTextLotNum = view.findViewById(R.id.editTextLotNum);
        editTextName = view.findViewById(R.id.editTextName);
        editTextCatogory = view.findViewById(R.id.editTextCategory);
        editTextPeriod = view.findViewById(R.id.editTextPeriod);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        buttonAdd = view.findViewById(R.id.buttonAdd);

        db = FirebaseDatabase.getInstance("https://b07-demo-summer-2024-default-rtdb.firebaseio.com/");

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
        String category = editTextCatogory.getText().toString().trim();
        String period = editTextPeriod.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (lotNumStr.isEmpty() || name.isEmpty() || category.isEmpty() || period.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isNumber(lotNumStr)) {
            Toast.makeText(getContext(), "Lot Number should be an Integer", Toast.LENGTH_SHORT).show();
            return;
        }

        int lotNum = Integer.parseInt(lotNumStr);

        itemsRef = db.getReference("categories/" + category);
        String id = itemsRef.push().getKey();
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
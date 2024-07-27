package com.tbb.taamcollection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RemoveItemFragment extends Fragment {
    private TextView textViewDeleteText;
    private Button buttonConfirm, buttonCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_item_fragment, container, false);

        textViewDeleteText = view.findViewById(R.id.textViewConfirmation);
        buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        return view;
    }

    private void deleteItem() {
        //ToDo - Implement
    }

    private void back() {
        //ToDo - Implement
    }
}
package com.tbb.taamcollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

public class AdminLoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_login_fragment, container, false);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        Button buttonReturn = view.findViewById(R.id.buttonReturn);
        TextView invalidLogin = view.findViewById(R.id.invalidLogin);
        TextView emptyPassUser = view.findViewById(R.id.emptyPassUser);
        TextInputEditText userText = view.findViewById(R.id.username);
        TextInputEditText passText = view.findViewById(R.id.password);

        AdminDatabase db = new AdminDatabase("adminLogins") {
            @Override
            void success() {
                AdminDatabase.loggedIn = true;
                invalidLogin.setVisibility(View.INVISIBLE);
                loadFragment(new HomeFragment());
            }

            @Override
            void failure() {
                invalidLogin.setVisibility(View.VISIBLE);
            }
        };

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userText.getText().toString();
                String password = passText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    emptyPassUser.setVisibility(View.VISIBLE);
                } else {
                    emptyPassUser.setVisibility(View.INVISIBLE);
                    db.authenticateLogin(username, password);
                }
            }
        });

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
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
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

import com.google.android.material.textfield.TextInputEditText;

public class AdminLoginView extends Fragment {
    String username, password;
    Button buttonLogin, buttonReturn;
    TextView invalidLogin, emptyPassUser;
    TextInputEditText userText, passText;

    AdminLoginPresenter presenter;
    AdminLoginView self = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_login_fragment, container, false);
        setVariables(view);
        presenter = new AdminLoginPresenter();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUsernamePassword();
                if (username.isEmpty() || password.isEmpty()) {
                    //presenter.emptyPassUser.setVisibility(View.VISIBLE);
                } else {
                    //presenter.emptyPassUser.setVisibility(View.INVISIBLE);
                    //presenter.authenticateLogin(username, password);
                }
            }
        });

        buttonReturn.setOnClickListener(v -> presenter.loadHome(self));

        return view;
    }

    private void setVariables(View view) {
        buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonReturn = view.findViewById(R.id.buttonReturn);
        invalidLogin = view.findViewById(R.id.invalidLogin);
        emptyPassUser = view.findViewById(R.id.emptyPassUser);
        userText = view.findViewById(R.id.username);
        passText = view.findViewById(R.id.password);
    }

    private void setUsernamePassword() {
        String username = userText.getText().toString();
        String password = passText.getText().toString();
    }
}

package com.tbb.taamcollection;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class AdminLoginView extends Fragment {
    String username, password;
    Button buttonLogin, buttonReturn;
    TextView invalidLogin, emptyPassUser;
    TextInputEditText userText, passText;
    CheckBox showPass;

    AdminLoginPresenter presenter;
    AdminLoginView self = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_login_fragment, container, false);
        setVariables(view);
        showPassword(showPass);
        presenter = new AdminLoginPresenter(this, new AdminLoginModel());

        buttonLogin.setOnClickListener(v -> {
            setUsernamePassword();
            presenter.doLogic(username, password, this);
        });
        passText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    setUsernamePassword();
                    presenter.doLogic(username, password, AdminLoginView.this);
                    return true;
                }
                return false;
            }
        });
        buttonLogin.setOnClickListener(v -> {
            setUsernamePassword();
            presenter.doLogic(username, password, this);
        });

        buttonReturn.setOnClickListener(v -> presenter.loadHome(self));

        return view;
    }

    void setEmptyPassUser(boolean visible) {
        emptyPassUser.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    void showPassword(CheckBox showPass){
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    passText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    passText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    void setIsValid(boolean valid) {
        invalidLogin.setVisibility(valid ? View.INVISIBLE : View.VISIBLE);
    }

    private void setVariables(View view) {
        buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonReturn = view.findViewById(R.id.buttonReturn);
        invalidLogin = view.findViewById(R.id.invalidLogin);
        emptyPassUser = view.findViewById(R.id.emptyPassUser);
        userText = view.findViewById(R.id.username);
        passText = view.findViewById(R.id.password);
        showPass = view.findViewById(R.id.showPass);
    }

    private void setUsernamePassword() {
        username = userText.getText().toString();
        password = passText.getText().toString();
    }
}
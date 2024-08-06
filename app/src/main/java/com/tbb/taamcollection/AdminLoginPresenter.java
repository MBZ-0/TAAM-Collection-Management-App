package com.tbb.taamcollection;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AdminLoginPresenter {
    AdminLoginModel model;
    AdminLoginView view;

    public AdminLoginPresenter(AdminLoginView view, AdminLoginModel model) {
        this.view = view;
        this.model = model;
    }

    public void authenticateLogin(String username, String password) {
        model.authenticateLogin(username, password, new AdminLoginModel.AuthCallback() {
            @Override
            public void onSuccess() {
                view.setIsValid(true);
                loadHome(view);
            }

            @Override
            public void onFailure() {
                view.setIsValid(false);
            }
        });
    }

    public boolean checkEmpty(String username, String password) {
        boolean isEmpty = username.isEmpty() || password.isEmpty();
        view.setEmptyPassUser(isEmpty);
        return isEmpty;
    }

    public void doLogic(String username, String password, Fragment view) {
        this.view.setEmptyPassUser(false);
        this.view.setIsValid(true);
        boolean empty = checkEmpty(username, password);
        if (!empty) {
            authenticateLogin(username, password);
        }
    }

    public void loadHome(Fragment current) {
        loadFragment(new HomeFragment(), current);
    }

    void loadFragment(Fragment toLoad, Fragment fromLoad) {
        FragmentTransaction transaction = fromLoad.getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, toLoad);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
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

    public boolean authenticateLogin(String username, String password) {
        boolean success = model.authenticateLogin(username, password);
        view.setIsValid(success);
        return success;
    }

    public boolean checkEmpty(String username, String password) {
        view.setEmptyPassUser(username.isEmpty() || password.isEmpty());
        return (username.isEmpty() || password.isEmpty());
    }

    public void doLogic(String username, String password, Fragment view) {
        this.view.setEmptyPassUser(false);
        this.view.setIsValid(true);
        boolean empty = checkEmpty(username, password);
        if (!empty) {
            boolean success = authenticateLogin(username, password);
            if (success) {
                loadHome(view);
            }
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

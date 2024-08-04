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
        view.setIsValid(model.authenticateLogin(username, password));
    }

    public void checkEmpty(String username, String password) {
        view.setEmptyPassUser(username.isEmpty() || password.isEmpty());
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

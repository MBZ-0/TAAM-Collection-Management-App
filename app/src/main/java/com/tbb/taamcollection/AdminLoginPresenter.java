package com.tbb.taamcollection;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AdminLoginPresenter {

    AdminLoginModel model;

    public AdminLoginPresenter(AdminLoginModel model) {
        this.model = model;
    }

    public boolean authenticateLogin(String username, String password) {
        return model.authenticateLogin(username, password);
    }

    public void loadHome(Fragment current) {
        loadFragment(new HomeFragment(), current);
    }

    public boolean checkEmpty(String username, String password) {
        return (username.isEmpty() || password.isEmpty());
    }

    private void loadFragment(Fragment toLoad, Fragment fromLoad) {
        FragmentTransaction transaction = fromLoad.getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, toLoad);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

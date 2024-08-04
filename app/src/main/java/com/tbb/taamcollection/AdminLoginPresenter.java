package com.tbb.taamcollection;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AdminLoginPresenter {
    public AdminLoginPresenter() {

    }

    public void authenticateLogin(String username, String password) {

    }

    public void loadHome(Fragment current) {
        loadFragment(new HomeFragment(), current);
    }

    private void loadFragment(Fragment toLoad, Fragment fromLoad) {
        FragmentTransaction transaction = fromLoad.getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, toLoad);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

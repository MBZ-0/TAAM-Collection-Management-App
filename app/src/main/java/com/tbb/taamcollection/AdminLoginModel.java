package com.tbb.taamcollection;

public class AdminLoginModel {
    AdminDatabase db;

    public AdminLoginModel() {}

    public void setDatabase(AdminLoginPresenter presenter) {
        db = new AdminDatabase("adminLogins") {
            @Override
            void success() {
                AdminDatabase.loggedIn = true;
                //presenter.invalidLogin.setVisibility(View.INVISIBLE);
                //loadFragment(new HomeFragment());
            }

            @Override
            void failure() {
                //presenter.invalidLogin.setVisibility(View.VISIBLE);
            }
        };
    }

    public void authenticateLogin(String username, String password) {
        db.authenticateLogin(username, password);
    }
}

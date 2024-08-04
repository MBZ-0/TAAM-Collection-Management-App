package com.tbb.taamcollection;

public class AdminLoginModel {
    AdminDatabase db;

    public AdminLoginModel() {}

    public void setDatabase(AdminLoginPresenter presenter) {
        db = new AdminDatabase("adminLogins") {
            @Override
            void success() {
                AdminDatabase.loggedIn = true;
            }

            @Override
            void failure() {
            }
        };
    }

    public boolean authenticateLogin(String username, String password) {
        db.authenticateLogin(username, password);
        return AdminDatabase.loggedIn;
    }
}

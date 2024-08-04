package com.tbb.taamcollection;

public class AdminLoginModel {
    AdminDatabase db;

    public AdminLoginModel() {
        db = new AdminDatabase("adminLogins");
    }

    public boolean authenticateLogin(String username, String password) {
        db.authenticateLogin(username, password);
        return AdminDatabase.loggedIn;
    }
}

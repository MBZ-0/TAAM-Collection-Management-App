package com.tbb.taamcollection;

public class AdminLoginModel {
    AdminDatabase db;
    private AuthCallback callback;

    public AdminLoginModel() {
        db = new AdminDatabase("adminLogins") {
            @Override
            void success() {
                AdminDatabase.loggedIn = true;
                if (callback != null) {
                    callback.onSuccess();
                }
            }

            @Override
            void failure() {
                AdminDatabase.loggedIn = false;
                if (callback != null) {
                    callback.onFailure();
                }
            }
        };
    }

    public void authenticateLogin(String username, String password, AuthCallback callback) {
        this.callback = callback;
        db.authenticateLogin(username, password);
    }

    public interface AuthCallback {
        void onSuccess();
        void onFailure();
    }
}
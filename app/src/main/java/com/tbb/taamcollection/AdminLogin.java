package com.tbb.taamcollection;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminLogin {
    private String username = "";
    private String password = "";

    AdminLogin(){
    }

    static HashMap<Integer, AdminLogin> convert(ArrayList<HashMap<String, Object>> a) {
        HashMap<Integer, AdminLogin> r = new HashMap<>();
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) != null) {
                AdminLogin n = new AdminLogin();
                HashMap<String, Object> k = a.get(i);
                n.username = (String) k.get("username");
                n.password = (String) k.get("password");
                r.put(i, n);
            }
        }
        return r;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
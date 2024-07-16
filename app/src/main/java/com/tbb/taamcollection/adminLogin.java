package com.tbb.taamcollection;

import java.util.ArrayList;
import java.util.HashMap;

public class adminLogin {
    private String username = "";
    private String password = "";

    adminLogin(){
    }

    static HashMap<Integer, adminLogin> convert(ArrayList<HashMap<String, Object>> a) {
        HashMap<Integer, adminLogin> r = new HashMap<>();
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) != null) {
                adminLogin n = new adminLogin();
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
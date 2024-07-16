package com.tbb.taamcollection;

import java.util.HashMap;
import java.util.Objects;

/***
 * class AdminBase
 *  Fields
 * HashMap allLogins: A hashmap of every login in the database since last update
 * Item query: Gets an item
 */
public class AdminBase extends Database{

    HashMap<Integer, adminLogin> allLogins;

    AdminBase(String name){
        super(name);
    }

    @Override
    void updateQuery(){
        allLogins = adminLogin.convert(preConvert);
    }

    adminLogin queryUsername(String username){
        if (loaded) {
            int i = 0;
            adminLogin temp = allLogins.get(i);
            while (temp != null) {
                if (Objects.equals(temp.getUsername(), username)) {
                    return temp;
                }
                i++;
                temp = allLogins.get(i);
            }
            return null;
        }
        return null;
    }

    boolean authenticateLogin(String username, String password){
        if (loaded) {
            adminLogin temp = queryUsername(username);
            if (temp != null) {
                return temp.getPassword().equals(password);
            }
            return false;
        }
        return false;
    }
}
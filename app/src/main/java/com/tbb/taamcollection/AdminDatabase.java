package com.tbb.taamcollection;

/***
 * class AdminBase
 *  Fields
 * HashMap allLogins: A hashmap of every login in the database since last update
 * Item query: Gets an item
 */
public class AdminDatabase extends Database{

    static Database db;
    static boolean loggedIn = false;
    AdminDatabase(String name){
        super(name);
        db = this;
    }

    void success(){
        System.out.println("SUCCESS");
    }
    void failure(){
        System.out.println("FAILURE");
    }


    void authenticateLogin(String username, String password){
        database.child(username).get().addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                System.out.println("FAIL");
            }else{
                String psw =(String) task.getResult().getValue();
                if(psw != null && psw.equals(password)){
                    success();
                }else {
                    failure();
                }
            }
        });
    }
}
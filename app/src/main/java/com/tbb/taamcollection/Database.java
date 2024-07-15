package com.tbb.taamcollection;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


/***
 * Abstract class Database
 *  Fields
 * string name: Name of the database reference
 * protected arraylist of hashmap preconvert: A local query of the database
 * bool loaded: Signals whether the local database is loaded or not
 *  Methods
 * abstract void updateQuery: Is called when the local database entry finishes loading
 * void updateDatabase: Call to update the database to the most up to date version
 * protected void addEntry: Adds an object to the database
 * protected object removeEntry: Removes an object from the database
 */
public abstract class Database {

    static DatabaseReference database;
    String name;
    protected ArrayList<HashMap<String, Object>> preConvert;

    boolean loaded = false;

    Database(String name){
        this.name = name;
        database = FirebaseDatabase.getInstance("https://taam-collection-management-app-default-rtdb.firebaseio.com/").getReference("db").child(name);
        updateDatabase();
    }

    void updateDatabase(){
        database.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>(){
            @Override
            //@SuppressWarnings("unchecked") //L no more stinky warning
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    System.out.println("FAIL");
                }else{
                    Object k = task.getResult().getValue();
                    if(k != null) {
                        preConvert= (ArrayList<HashMap<String, Object>>)k;
                        updateQuery();
                        loaded = true;
                        System.out.println("LOADED");
                    }
                }
            }
        });
    }

    abstract void updateQuery();

    protected void addEntry( Object obj, int id){
        database.child(Integer.toString(id)).setValue(obj);
    };

    protected void removeEntry( int id ){
        database.child(Integer.toString(id)).removeValue();
    };

//    Object queryEntry(int id, Database b){
//        if(b instanceof ItemDatabase) {
//            ItemDatabase that = (ItemDatabase) b;
//            if (that.allItems != null) {
//                return that.allItems.get(id);
//            }
//        }else{
//            //TODO: Add behavior for AdminBase
//        }
//        return null;
//    };

}
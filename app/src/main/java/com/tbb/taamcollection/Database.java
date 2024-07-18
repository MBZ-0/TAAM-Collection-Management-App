package com.tbb.taamcollection;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


/***
 * class Database
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
public class Database {
    DatabaseReference database;
    String name;

    boolean loaded = false;

    Database(String name){
        this.name = name;
        database = FirebaseDatabase.getInstance("https://taam-collection-management-app-default-rtdb.firebaseio.com/").getReference("db").child(name);

    }
    protected void addEntry( Object obj, String id){
        database.child(id).setValue(obj);
    };

    protected void removeEntry( String id ){
        database.child(id).removeValue();
    };


}
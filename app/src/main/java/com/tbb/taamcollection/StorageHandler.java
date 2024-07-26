package com.tbb.taamcollection;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StorageHandler {
    private FirebaseStorage storage;
    private StorageReference storageRef;

    StorageHandler(){
        storage = FirebaseStorage.getInstance("https://taam-collection-management-app-default-rtdb.firebaseio.com/");
        storageRef = storage.getReference();
    }

    //Store a file into the storage
    public void storeFile() {

    }

    //Query a given path and return it
    public void queryFile(String path) {

    }

    //Delete a given path
    public void removeFile(String path) {

    }
}
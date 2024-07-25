package com.tbb.taamcollection;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StorageHandler {
    private FirebaseStorage storage;
    private StorageReference storageRef;

    StorageHandler(String name){
        storage = FirebaseStorage.getInstance("https://taam-collection-management-app-default-rtdb.firebaseio.com/");
        storageRef = storage.getReference();
    }
}
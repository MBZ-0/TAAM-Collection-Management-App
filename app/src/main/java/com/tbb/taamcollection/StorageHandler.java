package com.tbb.taamcollection;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class StorageHandler {
    private FirebaseStorage storage = FirebaseStorage.getInstance("https://taam-collection-management-app-default-rtdb.firebaseio.com/");
    private StorageReference storageRef = storage.getReference();


}
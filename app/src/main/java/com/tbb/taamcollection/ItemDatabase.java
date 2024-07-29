package com.tbb.taamcollection;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ItemDatabase extends Database {

    static Database db;
    HashMap<Integer, Item> allItems;
    protected List<Object> preConvert;

    HashMap<Integer, Item> byLot;
    HashMap<String, LinkedList<Item>> byName;

    ItemDatabase(String name) {
        super(name);
        db = this;
        updateDatabase();
    }

    void updateDatabase() {
        database.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                System.out.println("FAIL");
            } else {
                Object k = task.getResult().getValue();
                if (k instanceof List) {
                    preConvert = (ArrayList<Object>) k;
                } else if (k instanceof HashMap) {
                    preConvert = new ArrayList<>(((HashMap<String, Object>) k).values());
                } else {
                    System.out.println("Unexpected data type: " + (k != null ? k.getClass().getName() : "null"));
                    preConvert = new ArrayList<>();
                }
                if (preConvert == null) {
                    preConvert = new ArrayList<>();
                }
                updateQuery();
                loaded = true;
            }
        });
    }

    void updateQuery() {
        allItems = Item.convert(preConvert);
    }

    void add(Item obj) {
        int id = nextId();
        addEntry(obj, Integer.toString(id));
        if (loaded) {
            allItems.put(id, obj);
        }
    }

    void remove(int id) {
        removeEntry(Integer.toString(id));
        if (loaded) {
            allItems.remove(id);
        }
    }

    LinkedList<Item> search(String name, String lot, String category, String period) {
        LinkedList<Item> r = new LinkedList<>();
        allItems.forEach((key, value) -> {
            boolean match = true;
            if (!lot.isEmpty() && !lot.equals(String.valueOf(value.getLotNumber()))) {
                match = false;
            }
            if (!name.isEmpty() && !name.equals(value.getName())) {
                match = false;
            }
            if (!category.isEmpty() && !category.equals(value.getCategory().getValue())) {
                match = false;
            }
            if (!period.isEmpty() && !period.equals(value.getPeriod().getValue())) {
                match = false;
            }
            if (match) {
                r.add(value);
            }
        });
        return r;
    }

    Item query(int id) {
        if (loaded) {
            return allItems.get(id);
        }
        return null;
    }

    int nextId() {
        int a = 0;
        while (allItems.get(a) != null) {
            a++;
        }
        return a;
    }
}

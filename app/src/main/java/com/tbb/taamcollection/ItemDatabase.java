package com.tbb.taamcollection;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.BiConsumer;

/***
 * class ItemDatabase
 *  Fields
 * HashMap allItems: A hashmap of every item in the database since last update
 * Item query: Gets an item
 */
public class ItemDatabase extends Database{

    static Database db;
    HashMap<Integer, Item> allItems;
    protected ArrayList<HashMap<String, Object>> preConvert;


    HashMap<Integer, Item> byLot;
    HashMap<String, LinkedList<Item>> byName;

    ItemDatabase(String name){
        super(name);
        db = this;
        updateDatabase();
    }
    void updateDatabase(){
        //@SuppressWarnings("unchecked") //L no more stinky warning
        database.get().addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                System.out.println("FAIL");
            }else{
                Object k = task.getResult().getValue();
                if(k != null) {
                    preConvert= (ArrayList<HashMap<String, Object>>)k;
                    updateQuery();
                    loaded = true;
                }
            }
        });
    }

    void updateQuery(){
        allItems = Item.convert(preConvert);
    }

    void add(Item obj){
        int id = nextId();
        addEntry(obj, Integer.toString(id) );
        if(loaded){
            allItems.put(id, obj);
        }
    }

    void remove(Item obj, int id){
        removeEntry(Integer.toString(id));
        if(loaded){
            allItems.remove(id);
        }
    }

    LinkedList<Item> search(String name, String lot, String category, String period){
        LinkedList<Item> r = new LinkedList<>();
        allItems.forEach((key, value) -> {
//            if(value.getName().contains(s) || Integer.toString(value.getLotNumber()).contains(s)){
//                r.add(value);
//            }
            boolean match = true;
            if (!(lot.isEmpty()) && Integer.parseInt(lot) != value.getLotNumber()){
                match = false;
            }
            if (!name.isEmpty() && !(name.equals(value.getName()))){
                match = false;

            }
            if (!category.isEmpty() && !(category.equals(value.getCategory().getValue()))){
                match = false;
            }
            if (!period.isEmpty() && !(period.equals(value.getPeriod().getValue()))){
                match = false;
            }
            if(match){
                r.add(value);
            }

        });
        return r;
    }

    Item query(int id){
        if (loaded) {
            return allItems.get(id);
        }
        return null;
    }

    int nextId(){
        int a = 0;
        while(allItems.get(a) != null){
            a ++;
        }
        return a;
    }
}
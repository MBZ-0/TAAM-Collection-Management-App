package com.tbb.taamcollection;

import java.util.HashMap;

/***
 * class ItemDatabase
 *  Fields
 * HashMap allItems: A hashmap of every item in the database since last update
 * Item query: Gets an item
 */
public class ItemDatabase extends Database{

    HashMap<Integer, Item> allItems;
    ItemDatabase(String name){
        super(name);
    }

    @Override
    void updateQuery(){
        allItems = Item.convert(preConvert);
    }

    void add( Item obj, int id){
        addEntry(obj, id);
        if(loaded){
            allItems.put(id, obj);
        }
    }

    void remove( Item obj, int id){
        removeEntry(id);
        if(loaded){
            allItems.remove(id);
        }
    }

    Item query(int id){
        if (loaded) {
            return allItems.get(id);
        }
        return null;
    }

}
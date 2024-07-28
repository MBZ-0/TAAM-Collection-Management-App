package com.tbb.taamcollection;

import android.media.Image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Item {
    private int id = 0;
    private int lotNumber = 0;
    private String name = "", description = "";
    private Period period = Period.Ji;
    private Category category = Category.Bronze;
    private String url = "";

    public Item() {}

    Item(ItemDatabase db){
        id = db.nextId();
    }

    Item(int id){
        this.id = id;
    }

    static HashMap<Integer, Item> convert(ArrayList<HashMap<String, Object>> a){
        a.removeAll(Collections.singleton(null));
        HashMap<Integer, Item> r = new HashMap<>();
        for(int i = 0; i < a.size(); i ++){
            if(a.get(i) != null) {
                HashMap<String, Object> k = a.get(i);
                Item n = new Item( Math.toIntExact((long)k.get("id")));
                n.lotNumber = Math.toIntExact((long)k.get("lotNumber"));
                n.name = (String)k.get("name");
                n.description = (String)k.get("description");
                n.period = Period.fromLabel((String)k.get("period"));
                n.category = Category.fromLabel((String)k.get("category"));
                n.url = (String)k.get("url");
                r.put(i, n);
            }
        }
        return r;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLotNumber(int lotNumber) {
        this.lotNumber = lotNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public int getLotNumber() {
        return lotNumber;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Period getPeriod() {
        return period;
    }

    public Category getCategory() {
        return category;
    }

    public String getUrl() { return url; }

    //0 for image, 1 for video, -1 for neither
    public int getUrlStatus() { if (url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".png") || url.endsWith(".gif") || url.endsWith(".bmp") || url.endsWith(".svg")) {
        return 0;
    } else if (url.endsWith(".mp4") || url.endsWith(".avi") || url.endsWith(".mov") || url.endsWith(".mkv") || url.endsWith(".flv") || url.endsWith(".wmv")) {
        return 1;
    } else {
        return -1;
    }}
}
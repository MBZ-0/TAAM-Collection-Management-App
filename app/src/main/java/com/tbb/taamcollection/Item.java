package com.tbb.taamcollection;

import android.media.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class Item {
    private int id = 0;
    private int lotNumber = 0;
    private String name = "", description = "";
    private Period period = Period.Ji;
    private Category category = Category.Bronze;

    Image img;

    Item(){
    }

    static HashMap<Integer, Item> convert(ArrayList<HashMap<String, Object>> a){
        HashMap<Integer, Item> r = new HashMap<>();
        for(int i = 0; i < a.size(); i ++){
            if(a.get(i) != null) {
                Item n = new Item();
                HashMap<String, Object> k = a.get(i);
                n.id = Math.toIntExact((long)k.get("id"));
                n.lotNumber = Math.toIntExact((long)k.get("id"));
                n.name = (String)k.get("name");
                n.description = (String)k.get("description");
                n.period = Period.fromLabel((String)k.get("period"));
                n.category = Category.fromLabel((String)k.get("category"));
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

    public void setImg(Image img) {
        this.img = img;
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

    public Image getImg() {
        return img;
    }
}
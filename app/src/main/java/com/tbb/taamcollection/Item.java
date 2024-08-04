package com.tbb.taamcollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Item {
    private int id = 0;
    private int lotNumber = 0;
    private String name = "";
    private String description = "";
    private Period period = Period.Ji;
    private Category category = Category.Bronze;
    private String imageUrl = "";
    private String videoUrl = "";

    public Item() {}

    Item(ItemDatabase db) {
        id = db.nextId();
    }

    Item(int id) {
        this.id = id;
    }

    static HashMap<Integer, Item> convert(List<Object> a) {
        a.removeAll(Collections.singleton(null));
        HashMap<Integer, Item> r = new HashMap<>();
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) != null && a.get(i) instanceof HashMap) {
                HashMap<String, Object> k = (HashMap<String, Object>) a.get(i);
                int id = Math.toIntExact((long) k.get("id"));
                Item n = new Item(id);
                n.lotNumber = Math.toIntExact((long) k.get("lotNumber"));
                n.name = (String) k.get("name");
                n.description = (String) k.get("description");
                n.period = Period.fromLabel((String) k.get("period"));
                n.category = Category.fromLabel((String) k.get("category"));
                n.imageUrl = (String) k.get("imageUrl");
                n.videoUrl = (String) k.get("videoUrl");
                r.put(id, n);
            }
        }
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (lotNumber != item.lotNumber) return false;
        if (!name.equals(item.name)) return false;
        if (!description.equals(item.description)) return false;
        if (period != item.period) return false;
        return category == item.category;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + lotNumber;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + period.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }

    // Just using this to debug the issues related to the Search Fragment
    @Override
    public String toString(){
        return "Name: " + name + "\nLot: " + lotNumber +
                "\nCategory: " + category + "\nPeriod: " + period;
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

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

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

    public String getImageUrl() { return imageUrl; }

    public String getVideoUrl() { return videoUrl; }

}

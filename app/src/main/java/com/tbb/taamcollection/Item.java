package com.tbb.taamcollection;

import android.media.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Item {
    private int id = 0;
    private int lotNumber = 0;
    private String name = "";
    private String description = "";
    private Period period = Period.Ji;
    private Category category = Category.Bronze;

    Image img;

    public Item() {}

    Item(ItemDatabase db) {
        id = db.nextId();
    }

    Item(int id) {
        this.id = id;
    }

    static HashMap<Integer, Item> convert(ArrayList<HashMap<String, Object>> a) {
        a.removeAll(Collections.singleton(null));
        HashMap<Integer, Item> r = new HashMap<>();
        for (HashMap<String, Object> k : a) {
            if (k != null) {
                Integer id = (k.get("id") instanceof Long) ? Math.toIntExact((Long) k.get("id")) : null;
                Integer lotNumber = (k.get("lotNumber") instanceof Long) ? Math.toIntExact((Long) k.get("lotNumber")) : null;
                String name = (String) k.get("name");
                String description = (String) k.get("description");
                Period period = Period.fromLabel((String) k.get("period"));
                Category category = Category.fromLabel((String) k.get("category"));

                if (id != null && lotNumber != null) {
                    Item n = new Item(id);
                    n.setLotNumber(lotNumber);
                    n.setName(name);
                    n.setDescription(description);
                    n.setPeriod(period);
                    n.setCategory(category);
                    r.put(id, n);
                } else {
                    System.out.println("Invalid data for item: " + k);
                }
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

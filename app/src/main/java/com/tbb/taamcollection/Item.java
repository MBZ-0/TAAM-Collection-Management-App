package com.tbb.taamcollection;

import android.media.Image;

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

    Image img;

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
        for (Object obj : a) {
            if (obj instanceof HashMap) {
                HashMap<String, Object> k = (HashMap<String, Object>) obj;
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

package com.tbb.taamcollection;
public enum Category {
    Jade("Jade"),
    Paintings("Paintings"),
    Calligraphy("Calligraphy"),
    Rubbings("Rubbings"),
    Bronze("Bronze"),
    BrassCopper("Brass and copper"),
    GoldSilver("Gold and silver"),
    Lacquer("Lacquer"),
    Enamels("Enamels");

    private final String label;
    private Category(String label){
        this.label = label;
    }
    public String getValue(){
        return this.label;
    }
    public static Category fromLabel(String label){
        for(Category v: Category.values()){
            if(v.getValue().equals(label)){
                return v;
            }
        }
        return Category.Jade;
    }

}
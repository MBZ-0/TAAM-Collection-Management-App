package com.tbb.taamcollection;

public enum Period {
    Xia("Xia"),
    Shang("Shang"),
    Zhou("Zhou"),
    Chuanqiu("Chuangiu"),
    Zhanggou("Zhangguo"),
    Qin("Qin"),
    Hang("Hang"),
    Shangou("Shangou"),
    Ji("Ji"),
    SouthNorth("South and north"),
    Shui("Shui"),
    Tang("Tang"),
    Liao("Liao"),
    Song("Song"),
    Jin("Jin"),
    Yuan("Yuan"),
    Ming("Ming"),
    Qing("Qing"),
    Modern("Modern");

    private final String label;
    private Period(String label){
        this.label = label;
    }
    public String getValue(){
        return this.label;
    }
    public static Period fromLabel(String label){
        for(Period v: Period.values()){
            if(v.getValue().equals(label)){
                return v;
            }
        }
        return Period.Xia;
    }
}
package com.example.shazzerhaat;

public class Note {
    private String name, number, nid, address, family,area,district,time, AnyOutsider,sick;

    public Note(){

    }
    public Note(String name,String number,String nid,String address,String family, String area, String district,String time,String AnyOutsider,String sick){
        this.name = name;
        this.number = number;
        this.nid = nid;
        this.address = address;
        this.family = family;
        this.area = area;
        this.district = district;
        this.time = time;
        this.AnyOutsider = AnyOutsider;
        this.sick = sick;
    }


    public String getName(){
        return name;
    }
    public String getNid(){
        return nid;
    }
    public String getNumber(){
        return number;
    }
    public String getAddress(){
        return address;
    }
    public String getFamily(){
        return family;
    }
    public String getArea(){
        return area;
    }
    public String getDistrict(){
        return district;
    }
    public  String getTime(){
        return time;
    }
    public String getAnyOutsider(){
        return AnyOutsider;
    }
    public  String getSick(){
        return sick;
    }
}

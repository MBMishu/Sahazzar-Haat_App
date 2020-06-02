package com.example.shazzerhaat;

public class Note_donation {

    private String name, number, address, time,sick;
    String donnation_amount,other, TrxnID,sanitizer;

    public Note_donation(String donner, String donner_number, String donner_address, String donner_amount, String donner_other, String donner_trxnId, String time, String donner_sick, String donner_food) {
        this.name = donner;
        this.number = donner_number;
        this.address = donner_address;
        this.donnation_amount = donner_amount;
        this.time = time;
        this.other = donner_other;
        this.TrxnID = donner_trxnId;
        this.sick = donner_sick;
        this.sanitizer = donner_food;
    }
    public String getAddress(){
        return address;
    }
    public  String getTime(){
        return time;
    }

    public  String getSick(){
        return sick;
    }
    public String getSanitizer(){
        return sanitizer;
    }
    public String getDonnation_amount(){
        return donnation_amount;
    }
    public  String getTrxnID(){
        return TrxnID;
    }
    public  String getName(){
        return name;
    }
    public  String getNumber(){
        return number;
    }
    public  String getOther(){
        return other;
    }
}

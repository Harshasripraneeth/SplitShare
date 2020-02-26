package com.pressure.splitshare;

public class Mate {
private String name;
private String tel;
private int amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Mate(String name, String tel, int amount) {
        this.name = name;
        this.tel = tel;
        this.amount = amount;
    }
}

package com.neona.todolist.database;

public class ShoppingData {
    int price;
    int ID;
    String name;
    int bought;

    public ShoppingData(int ID, String name, int price, int bought) {
        this.price = price;
        this.ID = ID;
        this.name = name;
        this.bought = bought;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isBought() {
        return bought;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }
}

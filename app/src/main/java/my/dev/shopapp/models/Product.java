package my.dev.shopapp.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String Name;
    private double Price;
    private int Count;

    public Product( String name, double price){
        Name = name;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }
    public int getCount() {
        return Count;
    }
    public void AddProduct(){
        Count++;
    }
    public void RemoveProduct(){
        if(Count>0)
        Count--;
    }
    public double GetCost(){
        return Count*Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}

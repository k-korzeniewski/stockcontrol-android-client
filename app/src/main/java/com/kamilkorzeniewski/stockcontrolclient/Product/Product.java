package com.kamilkorzeniewski.stockcontrolclient.Product;

public class Product {

    Long id;
    String name;
    int quantity;
    String code;
    float price;



    public Product(Long id, String name, int quantity, String code, float price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.code = code;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", code='" + code + '\'' +
                ", price=" + price +
                '}';
    }
}

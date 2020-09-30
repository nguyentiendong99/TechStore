package com.example.demo.Product.Entity;

import com.example.demo.Home.Entity.Category;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    private int id_category;
    private String name;
    private String image;
    private double price;
    private String title;
    private boolean highlight;
    private boolean new_product;
//    @JoinColumn(name = "selling_product")
//    private boolean selling_products;
    private String detail;
    @ManyToOne
    @JoinColumn(name = "id_category")
    Category category;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getId_category() {
//        return id_category;
//    }
//
//    public void setId_category(int id_category) {
//        this.id_category = id_category;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public boolean isNew_product() {
        return new_product;
    }

    public void setNew_product(boolean new_product) {
        this.new_product = new_product;
    }

//    public boolean isSelling_products() {
//        return selling_products;
//    }
//
//    public void setSelling_products(boolean selling_products) {
//        this.selling_products = selling_products;
//    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

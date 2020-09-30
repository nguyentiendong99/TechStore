package com.example.demo.Cart.Entity;

import com.example.demo.Product.Entity.Product;

public class CartItem {
    private int quantity;
    private double totalPrice;
    private Product product;

    public CartItem() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

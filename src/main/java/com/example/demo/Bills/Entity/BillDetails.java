package com.example.demo.Bills.Entity;

import javax.persistence.*;

@Entity
@Table(name = "bill_detail")
public class BillDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private int id_product;
    private int id_bills;
    private int quantity;
    private double total;

    public BillDetails() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_bills() {
        return id_bills;
    }

    public void setId_bills(int id_bills) {
        this.id_bills = id_bills;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

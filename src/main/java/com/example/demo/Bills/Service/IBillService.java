package com.example.demo.Bills.Service;

import com.example.demo.Bills.Entity.Bills;
import com.example.demo.Cart.Entity.CartItem;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface IBillService {
    int AddBills(Bills bills);
    void AddBillsDetails(HashMap<Integer , CartItem> cart);
}

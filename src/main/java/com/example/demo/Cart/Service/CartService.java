package com.example.demo.Cart.Service;

import com.example.demo.Cart.Entity.CartItem;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface CartService {
    HashMap<Integer, CartItem> AddCart(Integer id , HashMap<Integer,CartItem> cart);
//    HashMap<Integer,CartItem> EditCart(Integer id , int quantity , HashMap<Integer,CartItem> cart);
    HashMap<Integer,CartItem> DeleteCart(Integer id , HashMap<Integer,CartItem> cart);
    int TotalQuantity(HashMap<Integer,CartItem> cart);
    double TotalPrice(HashMap<Integer,CartItem> cart);
}

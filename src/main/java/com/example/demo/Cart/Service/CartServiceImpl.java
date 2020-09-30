package com.example.demo.Cart.Service;

import com.example.demo.Cart.Entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDAO cartDAO = new CartDAO();

    @Override
    public HashMap<Integer, CartItem> AddCart(Integer id, HashMap<Integer, CartItem> cart) {
        return cartDAO.AddCart(id , cart);
    }

//    @Override
//    public HashMap<Integer, CartItem> EditCart(Integer id, int quantity, HashMap<Integer, CartItem> cart) {
//        return cartDAO.EditCart(id , quantity , cart);
//    }


    @Override
    public HashMap<Integer, CartItem> DeleteCart(Integer id, HashMap<Integer, CartItem> cart) {
        return cartDAO.DeleteCart(id , cart);
    }

    @Override
    public int TotalQuantity(HashMap<Integer, CartItem> cart) {
        return cartDAO.TotalQuantity(cart);
    }

    @Override
    public double TotalPrice(HashMap<Integer, CartItem> cart) {
        return cartDAO.TotalPrice(cart);
    }

}

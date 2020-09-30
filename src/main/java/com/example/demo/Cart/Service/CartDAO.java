package com.example.demo.Cart.Service;

import com.example.demo.Product.Entity.Product;
import com.example.demo.Product.Service.ProductService;
import com.example.demo.Cart.Entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Repository
@Transactional
public class CartDAO {
    @Autowired
    private ProductService service;

    public HashMap<Integer, CartItem> AddCart(int id, HashMap<Integer, CartItem> cart) {
        CartItem itemCart = new CartItem();
        Product Product = service.GetProduct(id);
        if (Product != null && cart.containsKey(id)) {
            itemCart = cart.get(id);
            itemCart.setQuantity(itemCart.getQuantity() + 1);
            itemCart.setTotalPrice(itemCart.getQuantity() * itemCart.getProduct().getPrice());
        } else {
            itemCart.setProduct(Product);
            itemCart.setQuantity(1);
            itemCart.setTotalPrice(Product.getPrice());
        }
        cart.put(id, itemCart);
        return cart;
    }

    public HashMap<Integer, CartItem> EditCart(int id , int quantity ,HashMap<Integer, CartItem> cart) {
        CartItem itemCart = new CartItem();
        itemCart = cart.get(id);
        itemCart.setQuantity(itemCart.getQuantity() + quantity);
        itemCart.setTotalPrice(itemCart.getQuantity() * itemCart.getProduct().getPrice());
        cart.put(id, itemCart);
        return cart;
    }
//    public HashMap<Integer,CartItem> EditCart(Integer id , int quantity , HashMap<Integer,CartItem> cart){
//        CartItem itemCart = new CartItem();
//        if (cart.containsKey(id)){
//            itemCart = cart.get(id);
//            itemCart.setQuantity(quantity);
//            double totalPrice = quantity * itemCart.getProduct().getPrice();
//            itemCart.setTotalPrice(totalPrice);
//        }
//        cart.put(id, itemCart);
//        return cart;
//    }

    public HashMap<Integer, CartItem> DeleteCart(Integer id, HashMap<Integer, CartItem> cart) {
        if (cart == null) {
            return cart;
        }
        if (cart.containsKey(id)) {
            cart.remove(id);
        }
        return cart;
    }

    public int TotalQuantity(HashMap<Integer, CartItem> cart) {
        int totalQuantity = 0;
        for (Map.Entry<Integer, CartItem> itemCart : cart.entrySet()) {
            totalQuantity += itemCart.getValue().getQuantity();
        }
        return totalQuantity;
    }

    public double TotalPrice(HashMap<Integer, CartItem> cart) {
        double totalPrice = 0;
        for (Map.Entry<Integer, CartItem> itemCart : cart.entrySet()) {
            totalPrice += itemCart.getValue().getTotalPrice();
        }
        return totalPrice;
    }
}

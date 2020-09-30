package com.example.demo.Bills.Service;

import com.example.demo.Bills.Entity.BillDetails;
import com.example.demo.Bills.Entity.Bills;
import com.example.demo.Cart.Entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BillsServiceImpl implements IBillService {
    @Autowired
    private BillsDAO billsDAO;
    @Override
    public int AddBills(Bills bills) {
        return billsDAO.AddBills(bills);
    }

    @Override
    public void AddBillsDetails(HashMap<Integer, CartItem> carts) {
        int idBills = billsDAO.GetIDLastBills();
        for (Map.Entry<Integer , CartItem> itemcart : carts.entrySet()){
            BillDetails billDetails = new BillDetails();
            billDetails.setId_bills(idBills);
            billDetails.setId_product(itemcart.getValue().getProduct().getId());
            billDetails.setQuantity(itemcart.getValue().getQuantity());
            billDetails.setTotal(itemcart.getValue().getTotalPrice());
            billsDAO.AddBillDetail(billDetails);
        }
    }
}

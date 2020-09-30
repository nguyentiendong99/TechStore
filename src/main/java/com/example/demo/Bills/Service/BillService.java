package com.example.demo.Bills.Service;

import com.example.demo.Bills.Entity.Bills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {
    @Autowired
    private BillRepository repository;
    public List<Bills> getBills(int id_user){
        return repository.getBillsById_user(id_user);
    }
    public List<Bills> list(){
        return (List<Bills>) repository.findAll();
    }
}


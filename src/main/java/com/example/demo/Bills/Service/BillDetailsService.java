package com.example.demo.Bills.Service;

import com.example.demo.Bills.Entity.BillDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BillDetailsService {
    @Autowired
    private BillDetailsRepository repository;
    public List<BillDetails> List(int id_bills){
        return repository.getBillDetailsById_bills(id_bills);
    }
//    public List<BillDetails> listAll(){
//        return (List<BillDetails>) repository.findAll();
//    }
}

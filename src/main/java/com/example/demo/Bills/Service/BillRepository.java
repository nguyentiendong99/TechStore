package com.example.demo.Bills.Service;

import com.example.demo.Bills.Entity.Bills;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bills , Integer> {
    @Query(value = "select b from Bills b where b.id_user = ?1")
    List<Bills> getBillsById_user(int id_user);
}

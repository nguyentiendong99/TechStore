package com.example.demo.Bills.Service;

import com.example.demo.Bills.Entity.BillDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailsRepository extends CrudRepository<BillDetails , Integer> {
    @Query(value = "select d from BillDetails d where d.id_bills = ?1")
    List<BillDetails> getBillDetailsById_bills(int id_bills);
}

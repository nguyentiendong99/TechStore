package com.example.demo.Bills.Controller;

import com.example.demo.Bills.Entity.BillDetails;
import com.example.demo.Bills.Service.BillDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BillDetailsController {
    @Autowired
    private BillDetailsService service;
    @GetMapping("/bills/{id_bills}")
    public String showBillsDetail(Model model , @PathVariable("id_bills") int id_bills){
        List<BillDetails> billDetails = service.List(id_bills);
        model.addAttribute("billDetails", billDetails);
        return "billDetails";
    }
}

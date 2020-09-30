package com.example.demo.Bills.Controller;


import com.example.demo.Security.MyUserDetails;
import com.example.demo.Bills.Entity.Bills;
import com.example.demo.Bills.Service.BillDetailsService;
import com.example.demo.Bills.Service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class BillsController {
    @Autowired
    private BillService service;
    @GetMapping("/bills")
    public String ShowBills(@AuthenticationPrincipal MyUserDetails userDetails,
                             Model model){
        model.addAttribute("users" , userDetails);
        MyUserDetails user = (MyUserDetails) model.getAttribute("users");
        if (user == null){
            return "login";
        }
        int id_user = user.getUser().getId();
        List<Bills> bills = service.getBills(id_user);
        model.addAttribute("bills" , bills);
        return "bills";
    }
    @Autowired
    private BillDetailsService billDetailsService;
    @GetMapping("/bills/export")
    public void exportToCsv(Model model
            ,@AuthenticationPrincipal MyUserDetails userDetails,
                            HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        model.addAttribute("users" , userDetails);
        MyUserDetails user = (MyUserDetails) model.getAttribute("users");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
        String currentDataTime = dateFormat.format(new Date());
        String filename = "user" + currentDataTime + ".csv";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=" + filename;
        response.setHeader(headerKey, headerValue);
        int id_user = user.getUser().getId();
        List<Bills> bills = service.getBills(id_user);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);
        String[] csvHearder = {"Name",  "address", "phone"  , "quantity" , "total" , "note"};
        String[] nameMapping = {"name", "address", "phone" , "quantity" , "total" , "note"};
        csvWriter.writeHeader(csvHearder);
        for (Bills bill : bills) {
            csvWriter.write(bill, nameMapping);
        }
        csvWriter.close();
    }
}

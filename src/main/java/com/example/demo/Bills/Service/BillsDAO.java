package com.example.demo.Bills.Service;

import com.example.demo.Bills.Entity.BillDetails;
import com.example.demo.Bills.Entity.Bills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BillsDAO  {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public int AddBills(Bills bills){
        StringBuffer sql = new StringBuffer();
        sql.append("insert into bills ");
        sql.append("( ");
        sql.append(" `id_user` ,`phone` , `name` , `address` , `quantity`  ,`total` ,  `note` ");
        sql.append(")");
        sql.append("values ");
        sql.append("( ");
        sql.append( "'" + bills.getId_user() + "', ");
        sql.append(" '" + bills.getPhone() +"' ,");
        sql.append(" '" + bills.getName() +"' ,");
        sql.append(" '" + bills.getAddress() +"' ,");
        sql.append(" " + bills.getQuantity() +" ,");
        sql.append(" " + bills.getTotal() +" ,");
        sql.append(" '" + bills.getNote() +"'");
        sql.append(");");
        int insert = jdbcTemplate.update(sql.toString());
        return insert;
    }
    public int GetIDLastBills(){
        StringBuffer sql = new StringBuffer();
        sql.append("select MAX(id) from bills ;");
        int id = jdbcTemplate.queryForObject(sql.toString(), new Object[]{} , Integer.class);
        return id;
    }
    public int AddBillDetail(BillDetails billDetail){
        StringBuffer sql = new StringBuffer();
        sql.append("insert into billdetail ");
        sql.append("( ");
        sql.append("id_product, ");
        sql.append("id_bills, ");
        sql.append("quantity, ");
        sql.append("total ");
        sql.append(")");
        sql.append("values ");
        sql.append("( ");
        sql.append(" " + billDetail.getId_product() + ", ");
        sql.append(" " + billDetail.getId_bills() + ", ");
        sql.append(" " + billDetail.getQuantity() + ", ");
        sql.append(" " + billDetail.getTotal() + " ");
        sql.append(")");
        int insert = jdbcTemplate.update(sql.toString());
        return insert;
    }
}

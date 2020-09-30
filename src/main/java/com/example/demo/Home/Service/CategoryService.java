package com.example.demo.Home.Service;

import com.example.demo.Home.Entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;
    public List<Category> listAll(  ){
        return (List<Category>) repository.findAll();
    }
}

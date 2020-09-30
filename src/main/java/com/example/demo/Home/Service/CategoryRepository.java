package com.example.demo.Home.Service;

import com.example.demo.Home.Entity.Category;
import com.example.demo.Product.Entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category , Integer> {
    List<Product> findProductsById(int id);
}

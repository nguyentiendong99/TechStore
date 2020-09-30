package com.example.demo.Product.Service;

import com.example.demo.Home.Entity.Category;
import com.example.demo.Product.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product , Integer> {
    List<Product> getProductByCategory(Category category);
//    @Query(value = "select c from Product c where c.selling_products = true order by rand()")
//    Page<Product> getProductBySelling_products(Pageable pageable);
    @Query(value = "select c from Product c where c.new_product = true order by rand()")
    Page<Product> getProductByNew_product(Pageable pageable);
    @Query(value = "select c from Product c where c.new_product = true order by rand()")
    List<Product> getProductByNew_product(Product product);
    @Query(value = "select c from Product c where c.highlight = true order by rand()")
    Page<Product> getProductByHighlight(Pageable pageable);
    @Query(value = "select c from Product c where c.highlight = true order by rand()")
    List<Product> getProductByHighlight(Product product);
    @Query(value = "select c from Product c where c.name like '%' || :keyword || '%' ")
    List<Product> findByName(@Param("keyword") String keyword);
    @Query(value = "select p from Product p where p.category = '4' order by rand() ")
    Page<Product> getProductByTV(Pageable pageable);
    @Query(value = "select p from Product p where p.category = '1' order by rand() ")
    Page<Product> getProductByPhones(Pageable pageable);
    @Query(value = "select p from Product p where p.category = '3' order by rand() ")
    Page<Product> getProductByLaptop(Pageable pageable);
    @Query(value = "select p from Product p where p.category = '2' order by rand() ")
    Page<Product> getProductByClock(Pageable pageable);
}

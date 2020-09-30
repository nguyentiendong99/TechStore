package com.example.demo.Product.Service;

import com.example.demo.Home.Entity.Category;
import com.example.demo.Product.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository repository;
    public List<Product> list(){
        return (List<Product>) repository.findAll();
    }
    public Page<Product> getTV(int pageNumber){
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,8 , sort);
        return repository.getProductByTV(pageable);
    }
    public Page<Product> getPhones(int pageNumber){
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,8 , sort);
        return repository.getProductByPhones(pageable);
    }
    public Page<Product> getLaptops(int pageNumber){
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,8 , sort);
        return repository.getProductByLaptop(pageable);
    }
    public Page<Product> getClocks(int pageNumber){
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,8 , sort);
        return repository.getProductByClock(pageable);
    }
    public Page<Product> ListAllProducts(int pageNumber){
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,6 , sort);
        return repository.findAll(pageable);
    }
    public Page<Product> ListAll(int pageNumber){
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,8 , sort);
        return repository.getProductByHighlight(pageable);
    }
    public Page<Product> ListNew(int pageNumber){
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,8 , sort);
        return repository.getProductByNew_product(pageable);
    }
    public void SaveProduct(Product product){
        repository.save(product);
    }
    public Product GetProduct(int id){
        return repository.findById(id).get();
    }
    public void DeleteProduct(int id){
        repository.deleteById(id);
    }
    public List<Product> GetIdCategory(Category category){
        return repository.getProductByCategory(category);
    }
    public List<Product> GetAllNewProduct(Product product){
        return repository.getProductByNew_product(product);
    }
    public List<Product> GetAllProductHighlight(Product product){
        return repository.getProductByHighlight(product);
    }
    public List<Product> findByProduct(String keyword){
        return repository.findByName(keyword);
    }

}

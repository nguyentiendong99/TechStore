package com.example.demo.Home.controller;

import com.example.demo.Cart.Entity.CartItem;
import com.example.demo.Home.Entity.Category;
import com.example.demo.Home.Service.CategoryService;
import com.example.demo.Product.Entity.Product;
import com.example.demo.Product.Service.ProductService;
import com.example.demo.oauth.UserOauth2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private ProductService service;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/")
    public String Products(@ModelAttribute("product") Product product, Model model, HttpSession session) {
        HashMap<Integer, CartItem> cart = (HashMap<Integer, CartItem>)
                session.getAttribute("cart");
        listByPage(model, 1);
        ShowCategory(model);
        if (cart != null) {
            int quantity = (int) session.getAttribute("totalQuantityCart");
            double Price = (double) session.getAttribute("totalPrice");
            model.addAttribute("quantity", quantity);
            model.addAttribute("Price", Price);
        }
        return "index";
    }
    @RequestMapping("/infoLogin")
    public String InfoLogin(Authentication authentication , Model model){
        UserOauth2 userOauth2 = (UserOauth2) authentication.getPrincipal();
        String email = userOauth2.getEmail();
        String name = userOauth2.getName();
        String phone = userOauth2.getPhone();
        model.addAttribute("email" , email);
        model.addAttribute("name" , name);
        model.addAttribute("phone" , phone);
        return "infoLogin";
    }

    @RequestMapping("/category")
    public String ShowCategory(Model model) {
        List<Category> categories = categoryService.listAll();
        model.addAttribute("category", categories);
        return "styles/popularitems";
    }

    @RequestMapping("/shop")
    public String Shop(Model model) {
        listByPage(model, 1);
        ShowCategory(model);
        return "shop";
    }

    @GetMapping(value = "/page/{pageNumber}")
    public ModelAndView listByPage(Model model, @PathVariable("pageNumber") int currentPage) {
        ModelAndView modelAndView = new ModelAndView();
        Page<Product> page = service.ListNew(currentPage);
        Page<Product> page1 = service.ListAll(currentPage);
        List<Product> products1 = page1.getContent();
        List<Product> products = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("new_product", products);
        model.addAttribute("feature_product", products1);
        modelAndView.setViewName("styles/new_arrive");
        modelAndView.setViewName("styles/feature_product");
        modelAndView.setViewName("shop");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/category/{category}")
    public ModelAndView GetCategory(@PathVariable("category") Category category, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = service.GetIdCategory(category);
        model.addAttribute("productCategory", products);
        ShowCategory(model);
        modelAndView.setViewName("productCategory");
        return modelAndView;
    }

    @RequestMapping(value = "/search")
    public ModelAndView search(Model model, @Param("keyword") String keyword) {
        ModelAndView modelAndView = new ModelAndView();
        ShowCategory(model);
        List<Product> products = service.findByProduct(keyword);
        modelAndView.addObject("productSearch", products);
        modelAndView.setViewName("searchProduct");
        return modelAndView;
    }

    @RequestMapping("/contact")
    public String Contact() {
        return "contact";
    }

    @RequestMapping("/about")
    public String About(Model model) {
        ShowCategory(model);
        return "about";
    }
}

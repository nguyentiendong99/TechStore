package com.example.demo.Cart.Controller;

import com.example.demo.Security.MyUserDetails;
import com.example.demo.Bills.Entity.Bills;
import com.example.demo.Cart.Entity.CartItem;
import com.example.demo.Bills.Service.BillsServiceImpl;
import com.example.demo.Cart.Service.CartServiceImpl;
import com.example.demo.oauth.UserOauth2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class CartController {
    @Autowired
    private CartServiceImpl cartService;

    @RequestMapping("/cart")
    public ModelAndView Cart(HttpSession session, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        HashMap<Integer, CartItem> cart = (HashMap<Integer, CartItem>)
                session.getAttribute("cart");
        if (cart != null) {
            int quantity = (int) session.getAttribute("totalQuantityCart");
            double Price = (double) session.getAttribute("totalPrice");
            model.addAttribute("quantity", quantity);
            model.addAttribute("Price", Price);
        }
        modelAndView.setViewName("cart");
        return modelAndView;
    }

    @RequestMapping("AddCart/{id}")
    public String AddCart(HttpSession session, HttpServletRequest request, @PathVariable("id") Integer id) {
        HashMap<Integer, CartItem> cart = (HashMap<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        cart = cartService.AddCart(id, cart);
        session.setAttribute("cart", cart);
        session.setAttribute("totalQuantityCart", cartService.TotalQuantity(cart));
        session.setAttribute("totalPrice", cartService.TotalPrice(cart));
        return "redirect:" + request.getHeader("Referer");
    }

//    @RequestMapping("/EditCart/{id}")
//    public String EditCart(HttpServletRequest request, HttpSession session, @PathVariable("id") Integer id , @RequestParam("quantity") int quantity) {
//        HashMap<Integer, CartItem> cart = (HashMap<Integer, CartItem>) session.getAttribute("cart");
//        cart = cartService.EditCart(id,quantity, cart);
//        session.setAttribute("quantity" , quantity);
//        session.setAttribute("cart", cart);
//        session.setAttribute("totalQuantityCart", cartService.TotalQuantity(cart));
//        session.setAttribute("totalPrice", cartService.TotalPrice(cart));
//        return "redirect:" + request.getHeader("Referer");
//    }

    @RequestMapping("/DeleteCart/{id}")
    public String DeleteCart(HttpServletRequest request, HttpSession session, @PathVariable("id") int id) {
        HashMap<Integer, CartItem> cart = (HashMap<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        cart = cartService.DeleteCart(id, cart);
        session.setAttribute("cart", cart);
        session.setAttribute("totalQuantityCart", cartService.TotalQuantity(cart));
        session.setAttribute("totalPrice", cartService.TotalPrice(cart));
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping(value = "/checkout")
    public ModelAndView Checkout(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView("checkout");
        model.addAttribute("users", userDetails);
        MyUserDetails user = (MyUserDetails) model.getAttribute("users");
        Bills bills = new Bills();
        if (user != null) {
            bills.setId_user(user.getUser().getId());
            bills.setName(user.getUser().getName());
            bills.setAddress(user.getUser().getAddress());
            bills.setPhone(user.getUser().getPhone());
        } else {
            modelAndView.setViewName("login");
        }
        modelAndView.addObject("bills", bills);
        return modelAndView;
    }

    @Autowired
    BillsServiceImpl billsService;

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String CheckoutBills(HttpSession session, @ModelAttribute("bills") Bills bills) {
        bills.setQuantity((Integer) session.getAttribute("totalQuantityCart"));
        bills.setTotal((Double) session.getAttribute("totalPrice"));
        if (billsService.AddBills(bills) > 0) {
            HashMap<Integer, CartItem> cart = (HashMap<Integer, CartItem>) session.getAttribute("cart");
            billsService.AddBillsDetails(cart);
        }
        session.removeAttribute("cart");
        return "redirect:/";
    }
}

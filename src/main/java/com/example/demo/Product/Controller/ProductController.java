package com.example.demo.Product.Controller;

import com.example.demo.Product.Entity.Product;
import com.example.demo.Product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
public class ProductController {
    @Autowired
    private ProductService service;
    @RequestMapping("/newProduct")
    public String NewProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "newProduct";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String SaveProduct(@ModelAttribute("product") Product product) {
        service.SaveProduct(product);
        return "redirect:/stock";
    }
    @RequestMapping("/delete/{id}")
    public String DeleteProduct(@PathVariable("id") int id) {
        service.DeleteProduct(id);
        return "redirect:/";
    }
    @RequestMapping("/editProduct/{id}")
    public ModelAndView showViewEditProduct(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("editProduct");
        Product product = service.GetProduct(id);
        mav.addObject("editProduct", product);
        return mav;
    }

    @RequestMapping("/getProduct/{id}")
    public ModelAndView showViewProduct(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("productDetails");
        Product product = service.GetProduct(id);
        mav.addObject("productDetails", product);
        return mav;
    }
    @RequestMapping("/feature_product")
    public String ShowFeatureProduct(Model model , @ModelAttribute("product") Product product){
        List<Product> features = service.GetAllProductHighlight(product);
        model.addAttribute("feature_product" , features);
        return "feature_product";
    }
    @RequestMapping("/new_product")
    public String ShowNewProduct(Model model , @ModelAttribute("product") Product product){
        List<Product> news = service.GetAllNewProduct(product);
        model.addAttribute("new_product" , news);
        return "new_product";
    }
    @RequestMapping("/stock")
    public String ShowAll(Model model){
        return ListAllProducts(model , 1);
    }
    @GetMapping("/product/export")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
        String currentDataTime = dateFormat.format(new Date());
        String filename = "product" + currentDataTime + ".csv";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=" + filename;
        response.setHeader(headerKey, headerValue);
        List<Product> products = service.list();
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);
        String[] csvHearder = {"ID", "Name", "image" , "detail" , "price"};
        String[] nameMapping = {"id", "name", "image" , "detail" , "price"};
        csvWriter.writeHeader(csvHearder);
        for (Product product : products) {
            csvWriter.write(product, nameMapping);
        }
        csvWriter.close();
    }
    @GetMapping("/pageProduct/{pageNumber}")
    public String ListAllProducts(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Product> page = service.ListAllProducts(currentPage);
        int totalProduct = (int) page.getTotalElements();
        int totalPage = page.getTotalPages();
        List<Product> products = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalProduct", totalProduct);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("Products", products);
        return "stock";
    }

    @RequestMapping("/Tivi")
    public String ShowTV(Model model) {
        return ListPageTivi(model, 1);
    }

    @GetMapping("/pageTivi/{pageNumber}")
    public String ListPageTivi(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Product> tivi = service.getTV(currentPage);
        int totalTivi = (int) tivi.getTotalElements();
        int totalPage = tivi.getTotalPages();
        int SizePage = tivi.getSize();
        List<Product> products = tivi.getContent();
        model.addAttribute("size", SizePage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalTivi", totalTivi);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("tiviProducts", products);
        return "products/tivi";
    }

    @GetMapping("/pagePhones/{pageNumber}")
    public String ListPagePhones(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Product> phones = service.getPhones(currentPage);
        int totalPhones = (int) phones.getTotalElements();
        int totalPage = phones.getTotalPages();
        int SizePage = phones.getSize();
        List<Product> products = phones.getContent();
        model.addAttribute("size", SizePage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPhones", totalPhones);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("phoneProducts", products);
        return "products/phones";
    }

    @RequestMapping("/Phones")
    public String ShowPhones(Model model) {
        return ListPagePhones(model, 1);
    }

    @GetMapping("/pageLaptop/{pageNumber}")
    public String ListPageLaptops(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Product> laptops = service.getLaptops(currentPage);
        int totalLaptops = (int) laptops.getTotalElements();
        int totalPage = laptops.getTotalPages();
        int SizePage = laptops.getSize();
        List<Product> products = laptops.getContent();
        model.addAttribute("size", SizePage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalLaptops", totalLaptops);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("laptopProducts", products);
        return "products/laptops";
    }

    @RequestMapping("/Laptops")
    public String ShowLaptop(Model model) {
        return ListPageLaptops(model, 1);
    }

    @GetMapping("/pageClock/{pageNumber}")
    public String ListPageClocks(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Product> clock = service.getClocks(currentPage);
        int totalLaptops = (int) clock.getTotalElements();
        int totalPage = clock.getTotalPages();
        int SizePage = clock.getSize();
        List<Product> products = clock.getContent();
        model.addAttribute("size", SizePage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalClocks", totalLaptops);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("clockProducts", products);
        return "products/clocks";
    }

    @RequestMapping("/Clocks")
    public String ShowClocks(Model model) {
        return ListPageClocks(model, 1);
    }
}

package com.example.demo.User.Controller;

import com.example.demo.Security.MyUserDetails;
import com.example.demo.User.Entity.User;
import com.example.demo.User.Entity.Utility;
import com.example.demo.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService service;
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/listUser")
    public String ListUser(Model model) {
        return ListPage(model, 1);
    }

    @GetMapping("/pageUser/{pageNumber}")
    public String ListPage(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<User> page = service.ListAll(currentPage);
        int totalUser = (int) page.getTotalElements();
        int totalPage = page.getTotalPages();
        int SizePage = page.getSize();
        List<User> users = page.getContent();
        model.addAttribute("size", SizePage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalUser", totalUser);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("user", users);
        return "infoUser";
    }
    @GetMapping("/getUser/{id}")
    public ModelAndView EditUser(@PathVariable("id") int id){
        ModelAndView model = new ModelAndView();
        User user = service.GetUser(id);
        model.addObject("user" , user);
        model.setViewName("SettingUser");
        return model;
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String SaveUser(@ModelAttribute("user") User user) {
        service.SaveUser(user);
        return "redirect:/listUser";
    }

    @GetMapping("/user_home")
    public ModelAndView viewUserHome(@AuthenticationPrincipal MyUserDetails user,
                                     Model model) {
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("users", user);
        MyUserDetails userDetails = (MyUserDetails) model.getAttribute("users");
        model.addAttribute("user", userDetails.getUsername());
        modelAndView.setViewName("editUser");
        return modelAndView;
    }

    @RequestMapping("/deleteUser/{id}")
    public String DeleteUser(HttpServletRequest request, @PathVariable("id") int id) {
        service.DeleteUser(id);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping("/register")
    public ModelAndView Register(Model model , HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("register");
//        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
//        verifyRecaptcha(gRecaptchaResponse);
        model.addAttribute("register", new User());
        model.addAttribute("pageTitle", "Registration Account");
        return modelAndView;
    }

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public String createAccount(@ModelAttribute("user") User user, Model
            model, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        service.registerUser(user);
        String siteUrl = Utility.getSiteUrl(request);
        service.sendVerificationEmail(user, siteUrl);
        model.addAttribute("pageTitle", "Register Success");
        return "register_success";
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, Model model) {
        boolean verified = service.verify(code);
        String pageTitle = verified ? "Verification Successed !" : "Verification Failed";
        model.addAttribute("pageTitle", pageTitle);
        return (verified ? "verify_success" : "verify_failed");
    }

    @GetMapping("/infoUser")
    public ModelAndView GetInfoUser() {
        ModelAndView model = new ModelAndView("infoUser");
        return model;
    }

    @GetMapping("/user/export")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");
        String currentDataTime = dateFormat.format(new Date());
        String filename = "user" + currentDataTime + ".csv";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=" + filename;
        response.setHeader(headerKey, headerValue);
        List<User> users = service.listAll();
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);
        String[] csvHearder = {"User Name", "email", "address", "phone"};
        String[] nameMapping = {"name", "email", "address", "phone"};
        csvWriter.writeHeader(csvHearder);
        for (User user : users) {
            csvWriter.write(user, nameMapping);
        }
        csvWriter.close();
    }

    @Autowired
    JavaMailSender javaMailSender;

    @PostMapping("/send")
    public String submitContactForm(HttpServletRequest request,
                                    @RequestParam("attachment") MultipartFile multipartFile) throws MessagingException, UnsupportedEncodingException {
        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String mailSubject = fullName + " has sent a message";
        String mailContent = "<p><b>Send name</b> : " + fullName + "</p>";
        mailContent += "<p><b>Sender E-Mail </b>: " + email + "</p>";
        mailContent += "<p><b>Subject </b>: " + subject + "</p>";
        mailContent += "<p><b>Content </b>: " + content + "</p>";
        helper.setFrom("dongnguyen@gmail.com", "Mobile Shop");
        helper.setTo("dong190699@gmail.com");
        helper.setSubject(mailSubject);
        helper.setText(mailContent, true);
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            InputStreamSource source = new InputStreamSource() {
                @Override
                public InputStream getInputStream() throws IOException {
                    return multipartFile.getInputStream();
                }
            };
            helper.addAttachment(fileName, source);
        }
        javaMailSender.send(message);
        return "message";
    }
}

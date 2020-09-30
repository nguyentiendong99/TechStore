package com.example.demo.User.Service;

import com.example.demo.User.AuthenticationProvider;
import com.example.demo.User.Entity.User;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private UserRepository repository;
    private void encoderPassword(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
    public User registerUser(User user){
        encoderPassword(user);
        user.setCreatedTime(new Date());
        user.setEnabled(false);
        String randomCode = RandomString.make(12);
        user.setVerificationCode(randomCode);
        return repository.save(user);
    }
    public void sendVerificationEmail(User user , String siteUrl) throws UnsupportedEncodingException, MessagingException {
        String subject = "Please verify your register account ";
        String senderName = "Shop Mobile";
        String mailContent = "<p> Dear " + user.getName() + " , </p>";
        mailContent += "<p> Please click link below to verify your registration account </p>";
        String verifyUrl = siteUrl + "/verify?code=" + user.getVerificationCode();
        mailContent += "<h3><a href=\"" + verifyUrl + "\">VERIFY</a></h3>";
        mailContent += "<p>Thank you <br> The Mobile Shop</p>";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("dong19069999@gmail.com" , senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent , true);
        javaMailSender.send(message);
    }
    public boolean verify(String verificationCode){
        User user = repository.findByVeritification(verificationCode);
        user.setUpdatedTime(new Date());
        if (user == null || user.isEnabled()){
            return false;
        }
        else {
            repository.enable(user.getId());
            return true;
        }
    }

    public Page<User> ListAll(int pageNumber) {
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
        return repository.findAll(pageable);
    }
    public List<User> listAll(){
        return (List<User>) repository.findAll();
    }

    public void SaveUser(User user) {
        repository.save(user);
    }

    public User GetUser(int id) {
        return repository.findById(id).get();
    }
    public User GetUserByEmail(String email){
        return repository.getUsersByEmail(email);
    }

//    public User GetUserByEmail(String email){
//        return repository.getUserByEmail(email);
//    }

    public void DeleteUser(int id) {
        repository.deleteById(id);
    }

    public void createNewUserAfterOauthSuccess(String email ,  String name , AuthenticationProvider provider) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setEnabled(true);
        user.setCreatedTime(new Date());
        user.setAuthProvider(provider);
        repository.save(user);
    }

    public void updateUserAfterLogin(User user, String name, AuthenticationProvider provider) {
        user.setName(name);
        user.setAuthProvider(provider);
        repository.save(user);
    }
}

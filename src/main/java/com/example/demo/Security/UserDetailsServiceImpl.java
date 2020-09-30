package com.example.demo.Security;

import com.example.demo.User.Entity.User;
import com.example.demo.User.Service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getUserByMail(email);
        if (user == null){
            throw new UsernameNotFoundException("Could not find User");
        }
        return new MyUserDetails(user);
    }

}

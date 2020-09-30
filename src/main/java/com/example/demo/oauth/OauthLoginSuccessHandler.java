package com.example.demo.oauth;

import com.example.demo.User.AuthenticationProvider;
import com.example.demo.User.Entity.User;
import com.example.demo.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OauthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserOauth2 userOauth2 = (UserOauth2) authentication.getPrincipal();
        String email = userOauth2.getEmail();
        User user = userService.GetUserByEmail(email);
        String name = userOauth2.getName();
        if (user == null){
            // register new User
            userService.createNewUserAfterOauthSuccess(email , name , AuthenticationProvider.GOOGLE);
        }
        else {
            // update User
            userService.updateUserAfterLogin(user , name , AuthenticationProvider.GOOGLE);
        }
        System.out.println("User's Email : " + email);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

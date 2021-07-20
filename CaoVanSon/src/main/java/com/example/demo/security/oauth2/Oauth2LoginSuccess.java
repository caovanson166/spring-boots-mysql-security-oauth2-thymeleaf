package com.example.demo.security.oauth2;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.user.Provider;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
@Service
public class Oauth2LoginSuccess extends SimpleUrlAuthenticationSuccessHandler{
	@Autowired
	private UserRepository repo;
//	@Autowired
//	private CustomOAuth2User
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication vetify) throws IOException, ServletException {
		CustomOAuth2User oauth2=(CustomOAuth2User) vetify.getPrincipal();
		String email=oauth2.getEmail();
		System.out.println(email);        
		String name=oauth2.getFullName();
		User user = repo.getUserByUsername(email);
			if(user==null) {
				User u=new User();
				u.setEmail(email);
				u.setName(name);
				repo.save(u);
				response.sendRedirect("/User/login");
			}else {
				User u=repo.findByEmail(email);
				u.setName(name);
				u.setProvider(Provider.GOOGLE);
				repo.save(u);
				response.sendRedirect("/User/login");
			}
	
		super.onAuthenticationSuccess(request, response, vetify);
	
	}
	
	
}

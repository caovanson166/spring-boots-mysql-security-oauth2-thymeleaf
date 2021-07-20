package com.example.demo.security.oauth2;



import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

import com.example.demo.user.Provider;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
	 
@Service
public class UserService {	 
	    @Autowired
	    private UserRepository repo;
	     
	    	public void create(String email,String name) {
			User u=new User();
			u.setEmail(email);
			u.setName(name);
			u.setProvider(Provider.GOOGLE);
			repo.save(u);
		}
	     
	}
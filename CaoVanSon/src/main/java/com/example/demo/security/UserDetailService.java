package com.example.demo.security;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.user.UserRepository;
import com.example.demo.user.User;
@Service
@Transactional
public class UserDetailService implements UserDetailsService{
	@Autowired
	private UserRepository userRepo;
//	@Transactional
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        Set<Product> products = user.getProducts();
//        for (Product role : products) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
        return new UserDetail(user);
	}
	
	public void updateResetPasswordToken(String token, String email) {
        User customer = userRepo.findByEmail(email);
        if (customer != null) {
            customer.setReset_password_token(token);
            userRepo.save(customer);
        } 
    }
     
    public User getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }
     
    public void updatePassword(User customer, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);
         
        customer.setReset_password_token(null);
        userRepo.save(customer);
    }
}

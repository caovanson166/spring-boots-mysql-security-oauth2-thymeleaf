package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.address.Address;
import com.example.demo.address.AddressRepository;
import com.example.demo.product.iphone.Iphone;
import com.example.demo.product.iphone.IphoneRepository;
import com.example.demo.product.laptop.Laptop;
import com.example.demo.product.laptop.LaptopRepository;
import com.example.demo.user.Provider;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AddressRepository adRepo;
	@Autowired
	private IphoneRepository Iphone_repo;
	@Autowired
	private LaptopRepository Laptop_repo;
	@GetMapping("/")
	public String viewHomePage(Model model) {
		List<Address> address=adRepo.findAll();
		model.addAttribute("user", new User());
		model.addAttribute("ad",address);
		return "index";
	}
	@PostMapping("/User/save")
	public String taoacc(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setProvider(Provider.User);
		userRepo.save(user);
		
		
		return "redirect:/?son=true";
	}
	@GetMapping("/User/login")
	public String trangChu(Model model) {		
		List<Iphone> list=Iphone_repo.findAll();
		List<Laptop> Laptop=Laptop_repo.findAll();
		model.addAttribute("list", list);
		model.addAttribute("Laptop", Laptop);
		return "trangChu";
	}
}

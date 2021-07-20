package com.example.demo.product.iphone;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.example.demo.product.km.TangKem;

@Entity
public class Iphone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private float price;
	private String brand;
	private String traGop;
	private String giamGia;
	private String thongSo;
	private String NhaCungCap;
	private String timeSale;
	@ManyToMany
	@JoinTable(
			name="Iphone_TangKem",
			joinColumns = @JoinColumn(name="Iphone_id"),
			inverseJoinColumns = @JoinColumn(name="TangKem_id")
			)
	Set<TangKem> tangKem=new HashSet<>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getTraGop() {
		return traGop;
	}
	public void setTraGop(String traGop) {
		this.traGop = traGop;
	}
	public String getGiamGia() {
		return giamGia;
	}
	public void setGiamGia(String giamGia) {
		this.giamGia = giamGia;
	}
	public String getThongSo() {
		return thongSo;
	}
	public void setThongSo(String thongSo) {
		this.thongSo = thongSo;
	}
	public String getNhaCungCap() {
		return NhaCungCap;
	}
	public void setNhaCungCap(String nhaCungCap) {
		NhaCungCap = nhaCungCap;
	}
	public String getTimeSale() {
		return timeSale;
	}
	public void setTimeSale(String timeSale) {
		this.timeSale = timeSale;
	}
	public Set<TangKem> getTangKem() {
		return tangKem;
	}
	public void setTangKem(Set<TangKem> tangKem) {
		this.tangKem = tangKem;
	}
	
	
}

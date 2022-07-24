package com.offerfinder;

public class Product {
	private String name;
	private float price;
	private String quantity;
	
	//read about constructor
	public Product(String name, float price,String quantity) {
		this.name = name;
		this.price = price;
		this.quantity=quantity;
	}
	
	//read about getter setter oops
	public String getName() {
		return this.name;
	}
	public float getPrice() {
		return this.price;
	}
	public String getQuantity() {
		return this.quantity;
	}
}

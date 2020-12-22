package com.xiagang.bean;

public class ProductImage {
	public static String ALL = "type_all";
	public static String DETAIL = "type_detail";
	public static String SINGLE = "type_single";

	private int id;
	private Product product;
	private String type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "ProductImage [product=" + product + ", type=" + type + "]";
	}
	

}

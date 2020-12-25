package com.xiagang.comparator;

import com.xiagang.bean.Product;

import java.util.Comparator;

public class ProductDateComparator implements Comparator<Product> {

	public ProductDateComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Product p1, Product p2) {
		// TODO Auto-generated method stub
		int result = p2.getCreateDate().compareTo(p1.getCreateDate());
		return result;
	}

}

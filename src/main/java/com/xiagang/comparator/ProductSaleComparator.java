package com.xiagang.comparator;

import com.xiagang.bean.Product;

import java.util.Comparator;

public class ProductSaleComparator implements Comparator<Product> {

	public ProductSaleComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Product p1, Product p2) {
		// TODO Auto-generated method stub
		int result = p1.getSaleCount() - p2.getSaleCount();
		return result;
	}

}

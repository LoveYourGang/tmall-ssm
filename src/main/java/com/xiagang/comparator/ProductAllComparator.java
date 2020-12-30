package com.xiagang.comparator;

import com.xiagang.bean.Product;

import java.util.Comparator;

public class ProductAllComparator implements Comparator<Product> {

	public ProductAllComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Product p1, Product p2) {
		// TODO Auto-generated method stub
		int result = p1.getReviewCount()*p1.getSaleCount() - p1.getReviewCount()*p1.getSaleCount();
		return result;
	}

}

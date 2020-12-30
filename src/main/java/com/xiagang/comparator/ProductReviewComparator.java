package com.xiagang.comparator;

import com.xiagang.bean.Product;

import java.util.Comparator;

public class ProductReviewComparator implements Comparator<Product> {

	public ProductReviewComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Product p1, Product p2) {
		// TODO Auto-generated method stub
		int result = p2.getReviewCount() - p1.getReviewCount();
		return result;
	}

}

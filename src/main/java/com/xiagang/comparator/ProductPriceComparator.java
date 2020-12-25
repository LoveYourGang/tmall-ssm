package com.xiagang.comparator;

import com.xiagang.bean.Product;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<Product> {

	public ProductPriceComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Product p1, Product p2) {
		int result = (int) (p1.getPromotePrice() - p2.getPromotePrice());
		// TODO Auto-generated method stub
		return result ;
	}

}

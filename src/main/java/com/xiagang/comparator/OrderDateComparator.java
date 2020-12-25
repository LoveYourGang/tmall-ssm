package com.xiagang.comparator;

import com.xiagang.bean.Order;
import java.util.Comparator;

public class OrderDateComparator implements Comparator<Order> {

	public OrderDateComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Order o1, Order o2) {
		// TODO Auto-generated method stub
		return o2.getCreateDate().compareTo(o1.getCreateDate());
	}

}

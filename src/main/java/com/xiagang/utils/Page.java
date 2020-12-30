package com.xiagang.utils;

public class Page {
	private int start;
	private int count;
	private int total;
	private String param;

	public Page(int start, int count) {
		// TODO Auto-generated constructor stub
		this.start = start;
		this.count = count;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public int getTotalPage() {
		int pages; 
		if(total != 0 && total % count == 0) {
			pages = total / count;
		} else {
			pages = total / count + 1;
		}
		return pages;
	}
	
	public int getLast() {
		int last = 0;
		if(total % count == 0) {
			last = total - count;
		} else {
			last = total - total % count;
		}
		return last<0? 0: last;
	}
	
	public boolean isHasPrevious() {
		if(start <= 0)
			return false;
		return true;
	}
	
	public boolean isHasNext() {
		if(total - start <= count) 
			return false;
		return true;
	}
}

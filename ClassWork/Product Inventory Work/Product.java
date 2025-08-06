package com.aditya.productmanagement;

import java.util.*;

public class Product {
	private int prodid;
	private String prodname;
	private int prodquantity;
	private int prodrate;
	
	public Product(int prodid, String prodname, int prodquantity, int prodrate ) {
		this.prodid=prodid;
		this.prodname=prodname;
		this.prodquantity=prodquantity;
		this.prodrate=prodrate;
		
	}

	public int getProdid() {
		return prodid;
	}

	public void setProdid(int prodid) {
		this.prodid = prodid;
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public int getProdquantity() {
		return prodquantity;
	}

	public void setProdquantity(int prodquantity) {
		this.prodquantity = prodquantity;
	}

	public int getProdrate() {
		return prodrate;
	}

	public void setProdrate(int prodrate) {
		this.prodrate = prodrate;
	}

}

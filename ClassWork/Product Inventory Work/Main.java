package com.aditya.productmanagement;

import java.util.*;

class Inventory{
	private ArrayList<Product> products;
	
	public Inventory() {
		products= new ArrayList<Product>();
	}
	public void addProduct(Product product) {
		products.add(product);
	}
	
	public void displayProducts() {
		System.out.println("Inventory List");
		for(Product products : products) {
			System.out.println("Id: " + products.getProdid());
			System.out.println("Product Name: " + products.getProdname());
			System.out.println("Product Quantity: " + products.getProdquantity());
			System.out.println("Product Price: " + products.getProdrate());
		}
	}
	//To check if the product inventory is less than 140
	public void LowInventory() {
		for(Product product : products) {
			if(product.getProdquantity()<140) {
				System.out.println(product.getProdname()+" is low on quantity");
			}
		}
	}
	//To check the total products in the inventory
	public int TotQuantity() {
		int sum=0;
		for(Product product : products) {
			sum+=product.getProdquantity();
			
		}
		return sum;
	}
	//To delete a product
	public void RemoveProduct(Product product) {
		products.remove(product);
	}
	//Random product generation
	public Product RandomProduct() {
		Random r= new Random();
		return products.get(r.nextInt(products.size()));
	}
	
	//Search by ID
	public Product SearchbyID(int id) {
		for(Product product : products) {
			if(product.getProdid() == id) {
				return product;
			}
		}
		return null;
	}
	

}
public class Main {
	public static void main(String[] args) {
		Inventory inventory = new Inventory();
		
		//Create new products
		Product product1=new Product(1,"Shirt",200,2000);
		Product product2=new Product(2,"Pant",210,2000);
		Product product3=new Product(3,"Watches",180,2000);
		Product product4=new Product(4,"Caps",80,2000);
		Product product5=new Product(5,"Belts",120,2000);
		
		//Adding the products in the inventory
		inventory.addProduct(product1);
		inventory.addProduct(product2);
		inventory.addProduct(product3);
		inventory.addProduct(product4);
		inventory.addProduct(product5);
		
		inventory.displayProducts();
		
		System.out.println("Check Low Inventory products");
		inventory.LowInventory();
		
		System.out.println("Total Inventory Items: " + inventory.TotQuantity());
		
		Product random=inventory.RandomProduct();
		System.out.println("\nRandomly Picked Product:");
		System.out.println("Id: " + random.getProdid());
		System.out.println("Product Name: " + random.getProdname());
		System.out.println("Product Quantity: " + random.getProdquantity());
		System.out.println("Product Price: " + random.getProdrate());
		
		int searchID=3;
		System.out.println("\nSearching for product with ID: " + searchID);
		Product found = inventory.SearchbyID(searchID);
		System.out.println("\nRandomly Picked Product:");
		System.out.println("Id: " + found.getProdid());
		System.out.println("Product Name: " + found.getProdname());
		System.out.println("Product Quantity: " + found.getProdquantity());
		System.out.println("Product Price: " + found.getProdrate());
		
		
	
	}
}

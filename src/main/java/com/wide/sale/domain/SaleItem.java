package com.wide.sale.domain;

public class SaleItem {
	private int quantity;
	private int price;
	private Item item;
	
	public SaleItem(Item item, int quantity) {
		this.item = item;
		this.price = item.getPrice();
		this.quantity = quantity;
	}
	
	public Item getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int totalPrice() {
		return this.quantity * this.price;
	}
	
}

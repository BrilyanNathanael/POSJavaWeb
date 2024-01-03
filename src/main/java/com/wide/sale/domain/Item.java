package com.wide.sale.domain;

public class Item {
	private String itemCode;
	private int price;
	private String description;
	private String type;
	private boolean taxable;
	
	public Item(String itemCode, int price, String description, String type, boolean taxable) {
		super();
		this.itemCode = itemCode;
		this.price = price;
		this.description = description;
		this.type = type;
		this.taxable = taxable;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isTaxable() {
		return taxable;
	}

	public void setTaxable(boolean taxable) {
		this.taxable = taxable;
	}
}

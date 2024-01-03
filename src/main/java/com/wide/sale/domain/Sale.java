package com.wide.sale.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Sale {
	private int saleNumber;
	private String transDate;
	private Cashier cashier;
	private List<SaleItem> salesItem = new ArrayList<SaleItem>();
	private Payment payment;
	private int tax;
	
	public Sale(String transDate, Cashier cashier) {
		this.transDate = transDate;
		this.cashier = cashier;
	}
	
	public Sale(int saleNumber, String transDate, Cashier cashier, Payment payment) {
		this.saleNumber = saleNumber;
		this.transDate = transDate;
		this.cashier = cashier;
		this.payment = payment;
	}
	
	public Sale() {
		
	}

	public int getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(int saleNumber) {
		this.saleNumber = saleNumber;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	
	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
	
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	public int getTax() {
		return tax;
	}
	
	public void setTax(int tax) {
		this.tax = tax;
	}

	public void addSaleItem(Item item, int quantity) {
		SaleItem si = new SaleItem(item, quantity);
		
		salesItem.add(si);	
	}
	
	public List<SaleItem> getSaleItems() {
        List<SaleItem> clonedList = new ArrayList<SaleItem>();
        clonedList.addAll(salesItem);
        return clonedList;
	}
	
	public void setSaleItems(List<SaleItem> saleItems) {
		this.salesItem = saleItems;
	}
	
	public int totalPrice() {
		int totalPrice = 0;
		
		Iterator<SaleItem> itSaleItem = salesItem.iterator();
		
		while(itSaleItem.hasNext()) {
			SaleItem si = itSaleItem.next();
			totalPrice = totalPrice + si.totalPrice();
		}
		
		return totalPrice;
	}
	
	public int calculateTax() {
		int itemTax = 0;
		
		Iterator<SaleItem> itSaleItem = salesItem.iterator();
		
		while(itSaleItem.hasNext()) {
			SaleItem si = itSaleItem.next();
			if(si.getItem().isTaxable()) {
				itemTax = itemTax + ((int) (si.totalPrice() * Tax.tax));
			}
		}
		
		return itemTax;
	}
	
	public boolean finish() {
		if(this.getPayment().validate()) {
			return true;
		}
		else return false;
	}
}

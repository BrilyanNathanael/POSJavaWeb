package com.wide.sale.domain;

public abstract class Payment {
	
	protected int amount;
	protected int cashInHand;

	public Payment(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getCashInHand() {
		return cashInHand;
	}

	public void setCashInHand(int cashInHand) {
		this.cashInHand = cashInHand;
	}
	
	public abstract boolean validate();

}

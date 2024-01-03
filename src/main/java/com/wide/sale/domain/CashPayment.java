package com.wide.sale.domain;

public class CashPayment extends Payment {
	
	public CashPayment(int amount) {
		super(amount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		if(this.getCashInHand() >= this.getAmount()) {
			return true;			
		}
		return false;
	}
	
	public int change() {
		int change = this.getCashInHand() - this.getAmount();
		return change;
	}

}

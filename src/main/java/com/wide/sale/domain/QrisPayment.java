package com.wide.sale.domain;

public class QrisPayment extends Payment {

	public QrisPayment(int amount) {
		super(amount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		if(generateQR().equals("012345678")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private String generateQR() {
		return "012345678";
	}

}

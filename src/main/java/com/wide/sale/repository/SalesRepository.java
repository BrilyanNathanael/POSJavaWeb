package com.wide.sale.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.wide.sale.domain.CashPayment;
import com.wide.sale.domain.Cashier;
import com.wide.sale.domain.Database;
import com.wide.sale.domain.Item;
import com.wide.sale.domain.Payment;
import com.wide.sale.domain.QrisPayment;
import com.wide.sale.domain.Sale;
import com.wide.sale.domain.SaleItem;

public class SalesRepository {

	public void save(Sale sale) {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		try {
			Class.forName(Database.driver);
//			
			conn = DriverManager.getConnection(Database.jdbcUrl, Database.username, Database.password);
			conn.setAutoCommit(false);
			
//			Memasukkan ke Sale
			String sqlQuerySale = "INSERT INTO sale (trans_date, cashier, payment, amount, cash_in_hand, tax) values (?,?,?,?,?,?)";
			
			PreparedStatement stm = conn.prepareStatement(sqlQuerySale);
			
			String payment;
			int cashInHand = 0;
			if(sale.getPayment() instanceof CashPayment) {
				payment = "Cash";
				cashInHand = sale.getPayment().getCashInHand();
			}else {
				payment = "Qris";
			}
			
			stm.setString(1, sale.getTransDate());
			stm.setString(2, sale.getCashier().getName());
			stm.setString(3, payment);
			stm.setInt(4, sale.getPayment().getAmount());
			stm.setInt(5, cashInHand);
			stm.setInt(6, sale.getTax());
			
			int result = stm.executeUpdate();
			
			if(result > 0) {
				
				String querySelectId = "SELECT sale_number FROM sale ORDER BY sale_number DESC LIMIT 1";
				PreparedStatement stm2 = conn.prepareStatement(querySelectId);
				
				ResultSet result2 = stm2.executeQuery();
				
				if(result2.next()) {
					int sale_number = result2.getInt("sale_number");
					for(SaleItem si : sale.getSaleItems()) {
//						Memasukkan data Sale Item
						String sqlQuery = "INSERT INTO sale_item (sale_number, item_id, quantity, price) values (?,?,?,?)";
						
						PreparedStatement stm3 = conn.prepareStatement(sqlQuery);
						
						stm3.setInt(1, sale_number);
						stm3.setString(2, si.getItem().getItemCode());
						stm3.setInt(3, si.getQuantity());
						stm3.setInt(4, si.getPrice());
						
						int result3 = stm3.executeUpdate();
					}
				}
			}
			conn.commit();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.getStackTrace();
				}				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.getStackTrace();
			}
		}
		
	}

	public Sale findByNumber(int number) {
		// TODO Auto-generated method stub
		Connection conns = null;
		
		try {
			Class.forName(Database.driver);
			
			conns = DriverManager.getConnection(Database.jdbcUrl, Database.username, Database.password);
			
//			conn.setAutoCommit(true);
			
			String querySale = "SELECT * FROM sale WHERE sale_number=?";
			PreparedStatement stm = conns.prepareStatement(querySale);
			
			stm.setInt(1, number);
			ResultSet rs = stm.executeQuery();
			
			if(rs.next()) {
				Cashier c = new Cashier(rs.getString("cashier"));
				Sale s = new Sale(rs.getString("trans_date"), c);
				
				String query = "SELECT * FROM sale_item JOIN item ON sale_item.item_id = item.item_code WHERE sale_item.sale_number=?";
				PreparedStatement stm2 = conns.prepareStatement(query);
				
				stm2.setInt(1, number);
				ResultSet rs2 = stm2.executeQuery();
				
				int totalPrice = 0;
				
				while(rs2.next()) {
					boolean tax;
					if(rs2.getInt("taxable") == 0) {
						tax = true;
					}
					else {
						tax = false;
					}
					
					int subPrice = rs2.getInt("quantity") * rs2.getInt("price");
					totalPrice = totalPrice + subPrice;
					
					Item itm = new Item(rs2.getString("item_code"), rs2.getInt(5), rs2.getString("description"), rs2.getString("type"), tax);
					
					s.addSaleItem(itm, rs2.getInt("quantity"));
					
				}
				
				Payment p;
				int taxCalculate = s.calculateTax();
				if(rs.getString("payment").equals("Cash")) {
					p = new CashPayment(totalPrice + taxCalculate);
					p.setCashInHand(rs.getInt("cash_in_hand"));
				}
				else {
					p = new QrisPayment(totalPrice + taxCalculate);
				}
				
				s.setPayment(p);
				
				return s;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
		}
		return null;
	}
	
	public List<Sale> findAll(){
		List<Sale> listSales = new ArrayList<Sale>();
		Connection conn = null;
		try {
			Class.forName(Database.driver);
			conn = DriverManager.getConnection(Database.jdbcUrl, Database.username, Database.password);
			String querySale = "SELECT * FROM sale";
			
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(querySale);
			
			while (rs.next()) {
				int saleNumber = rs.getInt("sale_number");
				String transDate = rs.getString("trans_date");
				String cashier = rs.getString("cashier");
				String payment = rs.getString("payment");
				int cashInHand = rs.getInt("cash_in_hand");
				int amount = rs.getInt("amount");
				int tax = rs.getInt("tax");
				
				Cashier c = new Cashier(cashier);
				Payment p;
				
				if("Cash".equals(payment)) {
					p = new CashPayment(amount);
					p.setCashInHand(cashInHand);
				}
				else {
					p = new QrisPayment(amount);
				}
				
				Sale sl = new Sale(saleNumber, transDate, c, p);
				sl.setTax(tax);
				
				String query = "SELECT * FROM sale_item JOIN item ON sale_item.item_id = item.item_code WHERE sale_item.sale_number=?";
				PreparedStatement stm = conn.prepareStatement(query);
				
				stm.setInt(1, sl.getSaleNumber());
				ResultSet rs2 = stm.executeQuery();
				
				while(rs2.next()) {
					boolean taxable;
					if(rs2.getInt("taxable") == 0) {
						taxable = true;
					}
					else {
						taxable = false;
					}
					
					Item item = new Item(rs2.getString("item_code"), rs2.getInt("price"), rs2.getString("description"), rs2.getString("type"), taxable);
					
					sl.addSaleItem(item, rs2.getInt("quantity"));
				}
				listSales.add(sl);
			}
			return listSales;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
}

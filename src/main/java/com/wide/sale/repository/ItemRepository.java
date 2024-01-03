package com.wide.sale.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wide.sale.domain.Database;
import com.wide.sale.domain.Item;

public class ItemRepository {
	
	public List<Item> findAll(){
		List<Item> listItem = new ArrayList<Item>();
			
		Connection conn = null;
		try {
//			Load Driver
			Class.forName(Database.driver);
//			
//			Get connection
			conn = DriverManager.getConnection(Database.jdbcUrl, Database.username, Database.password);

			String query = "SELECT * FROM item";
			
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				String itemCode = rs.getString("item_code");
				int price = rs.getInt("price");
				String description = rs.getString("description");
				String type = rs.getString("type");
				
				boolean taxable;
				if(rs.getInt("taxable") == 0) {
					taxable = true;
				}
				else {
					taxable = false;
				}
				
				listItem.add(new Item(itemCode, price, description, type, taxable));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listItem;
	}
	
	public void insertItem(Item item) {
		Connection conn = null;
		
		try {
			Class.forName(Database.driver);
			
			conn = DriverManager.getConnection(Database.jdbcUrl, Database.username, Database.password);
			
			String queryItem = "INSERT INTO item (item_code, price, description, type, taxable) values (?,?,?,?,?)";
			
			PreparedStatement stm = conn.prepareStatement(queryItem);
			
			int taxable = 0;
			if(item.isTaxable()) {
				taxable = 0;
			}
			else if(!item.isTaxable()) {
				taxable = -1;
			}
			
			stm.setString(1, item.getItemCode());
			stm.setInt(2, item.getPrice());
			stm.setString(3, item.getDescription());
			stm.setString(4, item.getType());
			stm.setInt(5, taxable);
			
			stm.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Item findByCode(String code) {
		Connection conn = null;
		
		try {
//			Load Driver
			Class.forName(Database.driver);
//			
//			Get connection
			conn = DriverManager.getConnection(Database.jdbcUrl, Database.username, Database.password);

			String query = "SELECT * FROM item where item_code=?";
			
			PreparedStatement stm = conn.prepareStatement(query);
			
			stm.setString(1, code);
			ResultSet rs = stm.executeQuery();
			
			if (rs.next()) {
				String itemCode = rs.getString("item_code");
				int price = rs.getInt("price");
				String description = rs.getString("description");
				String type = rs.getString("type");
				
				boolean taxable;
				if(rs.getInt("taxable") == 0) {
					taxable = true;
				}
				else {
					taxable = false;
				}
				
				Item item = new Item(itemCode, price, description, type, taxable);
				return item;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void updateItem(Item item) {
		Connection conn = null;
		
		try {
			Class.forName(Database.driver);
			
			conn = DriverManager.getConnection(Database.jdbcUrl, Database.username, Database.password);
			
			String queryItem = "UPDATE item SET price=?, description=?, type=?, taxable=? WHERE item_code=?";
			
			PreparedStatement stm = conn.prepareStatement(queryItem);
			
			int taxable = 0;
			if(item.isTaxable()) {
				taxable = 0;
			}
			else if(!item.isTaxable()) {
				taxable = -1;
			}
			
			stm.setInt(1, item.getPrice());
			stm.setString(2, item.getDescription());
			stm.setString(3, item.getType());
			stm.setInt(4, taxable);
			stm.setString(5, item.getItemCode());
			
			stm.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

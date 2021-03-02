package com;
import java.sql.*;

public class Item {
	
	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test","root","");
			
			System.out.println("Successfully connected");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	/////////////////////////////////////////////////////////////////////////
	
	//insert itme
	public String insertItem(String code, String name, String price, String desc) {
		String output = "";
		
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database";
			}
			
			String query = "INSERT INTO items (itemID,itemCode,itemName,itemPrice,itemDesc) VALUES(?,?,?,?,?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc);
			
			preparedStmt.execute();
			con.close();
			output = "Data Inserted Successfully";
			
		}
		catch(Exception e) {
			output = "Error while inserting data";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//read data for the table
	public String readItems() {
		String output = "";
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database for reading data.";
			}
			
			output = "<table border='1'>"
					+ "<tr>"
					+ "<th>Item Code</th>"
					+ "<th>Item Name</th>"
					+ "<th>Item Price</th>"
					+ "<th>Item Description</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th>"
					+ "</tr>";
			
			String query = "SELECT * FROM items";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");
				
				output += "<tr><td>" +itemCode+ "</td>";
				output += "<td>" +itemName+ "</td>";
				output += "<td>" +itemPrice+ "</td>";
				output += "<td>" +itemDesc+ "</td>";
				
				output += "<td>"
						+ "<form method='post' action='items.jsp'>"
						+ "<input name='itemID' type='hidden' value='"+itemID+"'>"
						+ "<input name='action' value='select' type='hidden'>"
						+ "<input name='btnUpdate' type='submit' value='Update'>"
						+ "</form>"
						+ "</td>"
						+ "<td>"
						+ "<form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'>"
						+ "<input name='action' value='delete' type='hidden'>"
						+ "<input name='itemID' type='hidden' value='"+itemID+"'>"
						+ "</form>"
						+ "</td>"
						+ "</tr>";
			}
			con.close();
			
			output += "</table>";
			
		}
		catch(Exception e) {
			output = "Error while reading data.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	//update item
	public String updateItem(int ID,String code, String name, String price, String desc) {
		
		String output = "";
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database";
			}
			
			String query = "UPDATE items "
						  + "SET itemCode=?, itemName=?, itemPrice=?, itemDesc=? "
						  + "WHERE itemID = ?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setString(1,code);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(price));
			preparedStmt.setString(4, desc);
			preparedStmt.setInt(5, ID);
			
			preparedStmt.executeUpdate();
			con.close();
			output = "Values updated successfully";
			
		}
		catch(Exception e) {
			output = "Error while updating";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//retrieve a selected item to update
	public String readSelectedItem(int id) {
		String output = "";
		
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database to retrive a selected item";
				
			}
			String query = "SELECT * FROM items WHERE itemID = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, id);
			ResultSet rs = preparedStmt.executeQuery();
			
			while(rs.next()) {
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");
				
				output += "<form method=post action=items.jsp>"
						+ "<input name='action' value='update' type='hidden'>"
						+ "Item ID : '"+itemID+"'<br>"
						+ "Item Code : <input name=itemCode type=text value='"+itemCode+"'><br>"
						+ "Item Name : <input name=itemName type=text value='"+itemName+"'><br>"
						+ "Item Price : <input name=itemPrice type=text value='"+itemPrice+"'><br>"
						+ "Item Description : <input name=itemDesc type=text value='"+itemDesc+"'><br>"
						+ "<input name='itemID' type='hidden' value='"+itemID+"'>"
					    + "<input name=btnSubmit type=submit value=Update>"
					    + "</form>";
			}
			con.close();
			//output += "</table>";
		}
		catch(Exception e) {
			output = "Error while reading the selected item";
			System.err.println(e.getMessage());
			
		}
		return output;
	}
	
	//delete
	public String deleteItem(int ID) {
		String output = "";
		
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database to delete";
				
			}
			
			String query = "DELETE FROM items WHERE itemID = ?";
						 
			PreparedStatement prepareStmt = con.prepareStatement(query);
			
			prepareStmt.setInt(1,ID);
			prepareStmt.execute();
			con.close();
			output = "Item Deleted Successfully";
		}
		catch(Exception e) {
			output = "Error while deleting the item";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
/*
	public String insertItem(String code, String name, String price, String desc) {
		String output = "";
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database";
			}
			
			String query = "INSERT INTO items (itemID,itemCode,itemName,itemPrice,itemDesc) VALUES(?,?,?,?,?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2,code);
			preparedStmt.setString(3,name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc);
			
			preparedStmt.execute();
			con.close();
			
			output = "Inserted Successfully";
			
		}
		catch(Exception e) {
			
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	
	public String readItems() {
		String output = "";
		
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for reading";
				
			}
			
			output = "<table border='1'><tr><th>Item Code</th>"
					+ "<th>Item Name</th><th>Item Price</th>"
					+ "<th>Item Description</th>"
					+ "<th>Update</th><th>Remove</th></tr>";
					
			String query = "SELECT * FROM items";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				String itemID = Integer.toString(rs.getInt("itemID")); 
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");
				
				//adding rows into html table
				output += "<tr><td>" + itemCode + "</td>";
				output += "<td>" + itemName + "</td>";
				output += "<td>" + itemPrice + "</td>";
				output += "<td>" + itemDesc + "</td>";
				
				//Buttons
				output += "<td><form method='post' action='items.jsp'>"
						+ "<input name='action' value='select' type='hidden'>"
						+ "<input name='btnUpdate'"
						+ "type='button' value='Update'>"
						+ "<input name='itemIdForUpdate' type='hidden' value='"+itemID+"'>"
								+ "</form></td>"
						
						
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove'"
						+ "type='submit' value='Remove'>"
						
						+ "<input name='itemID' type='hidden'"
						+ "value='"+itemID+"'>" + "</form></td></tr>";
				
			}
		}
		catch(Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deleteItem(String id) {
		String output = "";
		
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting the database to delete items.";
			}
			
			String query = "DELETE FROM items WHERE itemID = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			int ID = Integer.parseInt(id);
			//binding the id
			
			preparedStmt.setInt(1,ID);
			
			//execute the statement
			preparedStmt.executeUpdate();
			con.close();
			output = "Item deleted successfully";
		}
		catch(Exception e) {
			output = "Error while deleting";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String readOneItem(int ID) {
		
		//int ID = Integer.parseInt(id);
		String output = "";
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while fetching data to update.";
			}
			
			String query = "SELECT * FROM items WHERE itemID = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1,ID);
			
			ResultSet rs = preparedStmt.executeQuery();
			
			while(rs.next()) {
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");
				
				output += "<form method=post action=items.jsp>"
						//+ "<input name='action' value='update' type='hidden'>"
						+ "Item ID : '"+itemID+"'<br>"
						+ "Item Code : <input name=itemCode type=text value='"+itemCode+"'><br>"
						+ "Item Name : <input name=itemName type=text value='"+itemName+"'><br>"
						+ "Item Price : <input name=itemPrice type=text value='"+itemPrice+"'><br>"
						+ "Item Decription : <input name=itemDesc typr=text value='"+itemDesc+"'><br>"
						+ "<input name='itemIdForUpdate' type='hidden' value='"+itemID+"'>"
						+ "<input name='btnUpdate' type='submit' value='Update'>"
						+ "</form>";
			}
			con.close();
			
			
			
		}
		catch(Exception e){
			output = "Error while reading items to update";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}*/
	
	
		
	

}
	
	
	
	
	
	
	
	
	

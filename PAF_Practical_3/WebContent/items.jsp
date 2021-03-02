<%@ page import="com.Item" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
   
    
    
    <%
    	if(request.getParameter("itemCode") != null){
    		Item itemObj = new Item();
    		
    		String stsMsg = itemObj.insertItem(request.getParameter("itemCode"),
    											request.getParameter("itemName"),
    											request.getParameter("itemPrice"),
    											request.getParameter("itemDesc"));
    		
    		session.setAttribute("statusMsg", stsMsg);
    		
    	}
    
    	if(request.getParameter("itemID") != null){
    		Item itemObj = new Item();
    		
    		String stsMsg = itemObj.deleteItem(request.getParameter("itemID"));
    		
    		session.setAttribute("statusMsg", stsMsg);
    		
    	}
    	
    	
    	
    	
    
    	
    	
    	
    %>  
    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>Items Management</h1>
	<!--  
	<form method="post" action="items.jsp">
		Item Code : <input name="itemCode" type="text"><br>
		Item Name : <input name="itemName" type="text"><br>
		ItemPrice : <input name="itemPrice" type="text"><br>
		Item Description : <input name="itemDesc" type="text"><br>
		<input name="btnSubmit" type="submit" value="Save">
	</form>
	-->
	
	<%--
		if(request.getParameter("itemIdForUpdate") == null)){
			out.print("<form method='post' action='items.jsp'>"
					+"Item Code : <input name='itemCode' type='text'><br>"
					+"Item Name : <input name='itemName' type='text'><br>"
					+"ItemPrice : <input name='itemPrice' type='text'><br>"
					+"Item Description : <input name='itemDesc' type='text'><br>"
					+"<input name='btnSubmit' type='submit' value='Save'>"
					+"</form>");
		}
		else{
			Item itemObj = new Item();
			out.print(itemObj.readOneItem(request.getParameter("itemIdForUpdate")));
		}
	
	--%>
	<%
		if(request.getParameter("action") != null){
			if(request.getParameter("action").toString().equalsIgnoreCase("select")){
				Item itemObj = new Item();
				out.print(itemObj.readOneItem(Integer.parseInt(request.getParameter("itemIdForUpdate"))));
			}
		}
		else{
			out.print("<form method='post' action='items.jsp'>"
					+"Item Code : <input name='itemCode' type='text'><br>"
					+"Item Name : <input name='itemName' type='text'><br>"
					+"ItemPrice : <input name='itemPrice' type='text'><br>"
					+"Item Description : <input name='itemDesc' type='text'><br>"
					+"<input name='btnSubmit' type='submit' value='Save'>"
					
					
					+"</form>");
		}
	%>
	
	
	
	
	<%
		out.print(session.getAttribute("statusMsg"));
	%>
	<br>
	<%
		Item itemObj = new Item();
		out.print(itemObj.readItems());
	%>
	




</body>
</html>
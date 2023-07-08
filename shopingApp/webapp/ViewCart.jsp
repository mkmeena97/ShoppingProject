
<%@ page import="java.sql.* , java.util.*, entities.User" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<%
			Connection con = (Connection)application.getAttribute("jdbccon");
			List<Integer> selectedProducts = (List<Integer>)session.getAttribute("cart");
			User u = (User)session.getAttribute("loggedinuser");
			
			if(selectedProducts==null)
			{ %>
			
			
				<h3> Your Cart Is Empty</h3>
				<h3>Want to add Items?</h3>
				<h3><a href='home'>Click Here </a></h3>
				
			<% }
			else
			{ %>
				<h2><%= u.getFirstname() %>'s Cart Item(s)</h2>
				<table border='2' >
				
				<% 
					PreparedStatement ps = null;
					ResultSet rs = null;
					try {
					ps=con.prepareStatement("select * from product where p_id=?");
					int cnt=0;
					float totalprice=0;
					for(int n : selectedProducts)
					{
						ps.setInt(1, n);
						rs = ps.executeQuery();
						if(rs.next())
						{ %>
							<tr>
							<td> <%= (++cnt) %> </td> 
							<td> <%= rs.getString(2) %> </td>
							<td><%= rs.getString(4)%></td>
							<td><a href='deleteItem?pid="+rs.getInt(1)+"'>Remove</a></td>
							<% totalprice+=Float.parseFloat(rs.getString(4)); %>
							</tr>
						
						<% }
					}
					
					session.setAttribute("totalprice", totalprice);                // saving total price into session
					%>
					<tr>
					<td colspan='2'> Total Price </td>
					<td > <%=totalprice%> </td>
					</tr>
					</table>
						
				
				
					<a href='confirmcart'>Confirm Order</a><br/>
					<a href='home'>Add more Items</a><br/>
					
					
					
					
				<% }
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				
			}%>
		
</body>
</html>
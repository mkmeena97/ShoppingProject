package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;


@WebServlet("/confirmcart")
public class ConfirmCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	
	public void init(ServletConfig config) throws ServletException {
		con = (Connection)config.getServletContext().getAttribute("jdbccon");
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("loggedinuser");
		
		
		String uid = u.getUserid();
		java.sql.Timestamp ts = new java.sql.Timestamp(new java.util.Date().getTime());
		float totalprice = (float)session.getAttribute("totalprice");
		
		PreparedStatement ps=null;
		
		try 
		{
			 ps = con.prepareStatement("insert into shopping (user_id,shoppingDate,totalprice) values(?,?,?)");
			 ps.setString(1, uid);
			 ps.setTimestamp(2, ts);
			 ps.setFloat(3,totalprice);
			 int n = ps.executeUpdate();
			 if(n>0)
			 {
				 out.print("Congrets.... "+u.getFirstname()+"</br>");
				 out.print("<p>Your order is confirmed</P>"); 
				 out.print("<p>Your bill will be emailed at "+u.getEmail()+"</P>"); 
				 out.print("<p>You will receive message on "+u.getContact()+"</P>"); 
				 
				 out.print("</br><a href='logout'>Logout</a>");
				 out.print("</br></br>Want to set new Order? <a href='home'> Click Here </a>");
				 
			 }
			 
				 
			 
		} 
		catch (SQLException e)
		{
			
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

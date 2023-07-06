package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/insert")
public class insertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 Connection con ; 
     
     
	   
	    @Override
		public void init(ServletConfig config) throws ServletException {
			super.init(config);
			
			//Throws not possible in overridden methods  so try catch block needed
					try
					{
						Class.forName("com.mysql.cj.jdbc.Driver");
						String jdbcUrl="jdbc:mysql://localhost:3306/shoppingdb";
						con=DriverManager.getConnection(jdbcUrl,"root","mkmeena97");
						System.out.println("connection done");
						
					} 
					catch (ClassNotFoundException | SQLException e ) 
					{
						e.printStackTrace();
					}
		}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		String uid=request.getParameter("uid");
		String pwd =request.getParameter("pwd");
		String fname=request.getParameter("fname");
		String mname=request.getParameter("mname");
		String lname=request.getParameter("lname");
		String email=request.getParameter("email");
		String mobile=request.getParameter("contact");
		
		try 
		{
			PreparedStatement ps = con.prepareStatement("insert into users values (?,?,?,?,?,?,?)");
			ps.setString(1,uid);
			ps.setString(2, pwd);
			ps.setString(3, fname);
			ps.setString(4, mname);
			ps.setString(5, lname);
			ps.setString(6, email);
			ps.setString(7, mobile);
			
			int n=ps.executeUpdate();
			if(n>0)
			{
				out.println("<h3>Data inserted into succesfully</h3>");
			}
			else
			{
				out.println("<h3>insertion failed !!!!!!!!!!!!</h3>");
			}
			
			
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
	}

}

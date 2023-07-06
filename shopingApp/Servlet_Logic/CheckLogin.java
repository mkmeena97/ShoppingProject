package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/loginCheck")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
     Connection con ; 
     
     
   
    @Override
	public void init(ServletConfig config) throws ServletException {
		con = (Connection)config.getServletContext().getAttribute("jdbccon");
		System.out.println("in login check");
				
	}


	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		String uid=request.getParameter("uid");
		String pwd=request.getParameter("pwd");
		
		System.out.println("in do post");
		//create and execute query
		PreparedStatement ps=null;
		ResultSet rs=null;
		try 
		{
			ps= con.prepareStatement("select * from users where u_id=? and password=?");
			ps.setString(1, uid);
			ps.setNString(2, pwd);
			
			rs=ps.executeQuery();
			System.out.println(rs);
			
			//Login success
			if(rs.next())
			{
				RequestDispatcher rd = request.getRequestDispatcher("/home");
				rd.forward(request, response);
			}
			//login failed
			else
			{
				response.sendRedirect("/ShoppingApp/Login.jsp");
			}
			
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
					rs.close();
					ps.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
	}

}

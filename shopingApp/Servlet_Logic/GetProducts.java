package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/getproducts")
public class GetProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection con;
	
	public void init(ServletConfig config) throws ServletException {
		con=(Connection)config.getServletContext().getAttribute("jdbccon");
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int	cid = Integer.parseInt(request.getParameter("cid"));
		PreparedStatement ps=null;
		ResultSet rs = null;
		
		try
		{
			ps=con.prepareStatement("select p_name from product where cat_id=?");
			ps.setInt(1, cid);
			
			rs = ps.executeQuery();
			
			
				while(rs.next())
				{
					out.println("<p>"+rs.getString(1)+"</p></br>");
				}
			
			
			
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}

}

package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/viewcart")
public class ViewCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
		Connection con;
	
	public void init(ServletConfig config) throws ServletException {
		con=(Connection)config.getServletContext().getAttribute("jdbccon");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession();
			List<Integer> selectedProducts = (List<Integer>)session.getAttribute("cart");
			
			if(selectedProducts==null)
			{
				out.println("<p> No Items In The Cart</p>");
				out.print("</br><a href='home'>Select Products</a></br>");
				
			}
			else
			{
				/*out.println("<ul>");
				for(int n : sps)
				{
					out.println("<li>"+n+"</li>");
				}
				out.println("</ul>");*/
				
				out.print("<table border=1>");
				
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
						{
							out.println("<tr>");
							out.print("<td>"+(++cnt)+"</td>");
							out.print("<td>"+rs.getString(2)+"</td>");
							out.print("<td>"+rs.getString(4)+"</td>");
							out.print("<td><a href='deleteItem?pid="+rs.getInt(1)+"'>Remove</a></td>");
							totalprice+=Float.parseFloat(rs.getString(4));
							out.println("</tr>");
						}
					}
					
					session.setAttribute("totalprice", totalprice);                // saving total price into session
					
					out.print("<tr>");
					out.print("<td colspan='2'>Total Price</td>");
					out.print("<td >"+totalprice+"</td>");
					out.print("</tr>");
					out.print("</table>");
					out.print("</br></br><a href='confirmcart'>Confirm Order</a>");
					out.print("</br></br><a href='home'>Add more Items</a>");
					
					
					
					
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

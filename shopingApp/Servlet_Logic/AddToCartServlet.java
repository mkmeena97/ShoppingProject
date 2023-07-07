package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		//getting product id of selected product 
		int spid = Integer.parseInt(request.getParameter("selectedProduct"));
		
		
		
		HttpSession session = request.getSession();
		List<Integer> products = (List<Integer>)session.getAttribute("cart");
		
		if(products==null)          //only for first selection of product (creating Arraylist for cart)
		{
			products = new ArrayList<>();
		}
		products.add(spid);         //otherwise adding the products into cart
		
		//Saving details into the session for further use.
		session.setAttribute("cart", products);
		
		
		out.print("</br>Product "+spid+" is Added in the cart");
		out.print("</br>There are "+products.size()+" item(s) in the cart");
		out.print("</br><a href='viewcart'>View Cart</a>.");
		out.print("</br><a href='home'>Go back to categories</a>");
		
		
	}

}

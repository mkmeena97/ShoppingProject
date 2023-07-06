package listeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ShoppinListener implements ServletContextListener {

    Connection con ;
    

	
    public void contextDestroyed(ServletContextEvent sce)  { 
    	try 
		{
			con.close();
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
    }

	
    public void contextInitialized(ServletContextEvent sce)  { 
    	//reading context level parameter from web.xml file
    			String driver=sce.getServletContext().getInitParameter("driverclass");
    			String jdbcUrl= sce.getServletContext().getInitParameter("jdbcurl");
    			String user = sce.getServletContext().getInitParameter("user");
    			String pwd = sce.getServletContext().getInitParameter("password");
    			
    			System.out.println(jdbcUrl);
    			try {
    				Class.forName(driver);
    				con=DriverManager.getConnection(jdbcUrl, user, pwd);
    				sce.getServletContext().setAttribute("jdbccon", con);
    				
    				System.out.println("Connection is set as context level Attribute by listner");
    			} 
    			catch (Exception e) 
    			{
    				
    				e.printStackTrace();
    			}
    }
	
}

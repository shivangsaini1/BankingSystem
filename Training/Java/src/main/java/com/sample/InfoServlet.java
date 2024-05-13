package com.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InfoServlet
 */
@WebServlet("/InfoServlet")
public class InfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
//		String name = request.getParameter("name");
		String name = (String) request.getSession().getAttribute("name");
		int  balance = Integer.parseInt(request.getParameter("balance"));
				
		 // Set up response content type and writer
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
      //loading the driver
      	String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        
		//specify the url,username and password
		String dbUrl = "jdbc:mysql://localhost:3306/training";
		String username = "Indradev";
		String password = "Ind@12!23";
				
		try {
			// Load the JDBC driver
            Class.forName(jdbcDriver);
            System.out.println("JDBC driver loaded successfully");

            // Establish a connection
            Connection con = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Connected to the database");
            
            String query = "INSERT INTO account (id, name, balance) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            
         // Set parameters
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, balance);
            
            int count = ps.executeUpdate();
            if(count>0) {
            	out.println("<h1> Inserted </h1>");
            }
            else
            	out.println("<h1> Insertion failed <h1>");

			
		}
		catch(Exception e){
			out.println("<h1>Exception : "+e.getMessage()+" </h1>");
		}
		
		
		
		
		out.println("<h1>Num = "+id+" </h1>");
		out.println("<h1>Name = "+name+" </h1>");
		out.println("<h1>Balance = "+balance+" </h1>");


	}

}

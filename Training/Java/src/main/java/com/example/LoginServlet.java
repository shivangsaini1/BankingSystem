package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String url = "jdbc:mysql://localhost:3306/training";
    private static final String user = "Indradev";
    private static final String pass = "Ind@12!23";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        boolean login = false;

        String name = request.getParameter("uname");
        String password = request.getParameter("password");

        try {
        	Connection connection = DriverManager.getConnection(url, user, pass);
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Login WHERE name = ? AND password = ?");

            pstmt.setString(1, name);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    login = true;
                    request.getSession().setAttribute("name", name);
                    // String username = (String) request.getSession().getAttribute("name"); in another servlet
                    request.getRequestDispatcher("Bank.jsp").forward(request, response);
                } else {
                    login = false;
                    request.setAttribute("Login", true);
                    request.getRequestDispatcher("SignUp.jsp").forward(request, response);
                }
            }

        } catch (SQLException e) {
            System.err.println("Failed to connect: " + e.getMessage());
        }	
		
	}

}

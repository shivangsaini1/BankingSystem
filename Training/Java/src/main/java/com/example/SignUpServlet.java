package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	
	private static final String url = "jdbc:mysql://localhost:3306/training";
    private static final String user = "Indradev";
    private static final String pass = "Ind@12!23";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		boolean inserted = false;
		String name = request.getParameter("uname");
		String password = request.getParameter("password");
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = DriverManager.getConnection(url, user, pass);
			
			String query = "INSERT INTO Login(name,password) values(?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                inserted = true;
            }
            request.setAttribute("inserted", inserted);
            request.getRequestDispatcher("SignUp.jsp").forward(request, response);
			
		}catch(SQLException e){
			out.println("Unable to connect: " + e.getMessage());
		}finally {
            try {
                if (connection != null)
                    connection.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
                System.err.println("SQL Exception" + e.getMessage());
            }
        }
	}

}

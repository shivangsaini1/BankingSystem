package com.sample;

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

@WebServlet("/AccountServlet")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final String url = "jdbc:mysql://localhost:3306/training";
    private final String user = "Indradev";
    private final String password = "Ind@12!23";

    public AccountServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int balance = Integer.parseInt(request.getParameter("balance"));
        
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = connection.prepareStatement("INSERT INTO account(id, name, balance) VALUES (?, ?, ?)")) {
            
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, balance);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                out.println("Success");
            } else {
                out.println("Failed to insert account");
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect: " + e.getMessage());
            out.println("Error: " + e.getMessage());
        } finally {
            out.close();
        }
    }

}
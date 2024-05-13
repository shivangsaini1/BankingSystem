package com.sample;

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
 * Servlet implementation class InfoServlet
 */
@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int balance = Integer.parseInt(request.getParameter("balance")); 
        String name = (String) request.getSession().getAttribute("name");
        
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/training";
        String username = "Indradev";
        String password = "Ind@12!23";

        try {
            // Register JDBC driver
            Class.forName(jdbcDriver);

            // Open a connection
            Connection connection = DriverManager.getConnection(dbUrl, username, password);

            // Prepare statement for checking balance
            String checkQuery = "SELECT balance FROM account WHERE name = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1,name);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("balance"); // Correct data type
                String updateQuery = "UPDATE account SET balance = (balance + ?) WHERE name = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                updateStmt.setDouble(1, balance); // Use setDouble for balance
                updateStmt.setString(2, name);
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    out.println("Deposit successful. New balance: " + (currentBalance + balance));
                } else {
                    out.println("Failed to update balance.");
                }
            } else {
                out.println("No account found with the given id.");
            }

            // Close resources
            rs.close();
            checkStmt.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Failed to execute the deposit operation. Error: " + e.getMessage());
        }

        // Print Id and Deposit after database operations
        out.println("<h1>Id = " + name + " </h1>");
        out.println("<h1>Deposit = " + balance + " </h1>");

    }

}

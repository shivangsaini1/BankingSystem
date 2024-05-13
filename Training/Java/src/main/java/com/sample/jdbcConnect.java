package com.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class jdbcConnect {
    private static Connection connection = null;
    private static final String url = "jdbc:mysql://localhost:3306/training";
    private static final String user = "Indradev";
    private static final String password = "Ind@12!23";

    public static void main(String args[]) {
        connectToDatabase();
        int option = -1;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Welcome");
            System.out.println("1. Create new Account.");
            System.out.println("2. Delete Account.");
            System.out.println("3. Update Account");
            System.out.println("4. Display");
            System.out.println("Enter your choice.");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    Insert();
                    break;
                case 2:
                    Delete();
                    break;
                case 3:
                    System.out.println("1.Withdraw");
                    System.out.println("2. Deposit");
                    System.out.println("Enter your choice:");
                    int c = sc.nextInt();
                    switch (c) {
                        case 1:
                            Withdraw();
                            break;
                        case 2:
                            Deposit();
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;
                case 4:
                	Display();
                		break;
                default:
                    System.out.println("Invalid choice.");
            }
            System.out.println("Enter 0 to exit or any other number to continue:");
            option = sc.nextInt();
        } while (option != 0);
    }

    private static void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database. Error: " + e.getMessage());
        }
    }

    private static void Insert() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Details");
            System.out.println("Enter Id: ");
            int id = sc.nextInt();
            System.out.println("Enter name: ");
            String name = sc.next();
            System.out.println("Enter Balance: ");
            int balance = sc.nextInt();

            String query = "INSERT INTO account(id,name,balance) values(?,?,?)";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, balance);
            pstmt.executeUpdate();
            System.out.println("Data Inserted");

        } catch (SQLException e) {
            System.err.println("Failed to execute the insert query: " + e.getMessage());
        }
    }

    private static void Delete() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter name");
            String name = sc.next();

            String query = "DELETE FROM account WHERE name = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data Deleted");
            } else {
                System.out.println("No data found for deletion.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to execute the delete query: " + e.getMessage());
        }
    }

    private static void Withdraw() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter name:");
            String name = sc.next();
            System.out.println("Enter withdrawal amount:");
            int balance = sc.nextInt();

            String query = "SELECT balance FROM account WHERE name = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getInt("balance");
                if (balance <= currentBalance) {
                    String updateQuery = "UPDATE account SET balance = (balance - ?) WHERE name = ?";
                    PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                    updateStmt.setInt(1, balance);
                    updateStmt.setString(2, name);
                    int rowsAffected = updateStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Withdrawal successful. New balance: " + (currentBalance - balance));
                    } else {
                        System.out.println("Failed to update balance.");
                    }
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("No account found with the given name.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to execute the withdrawal operation. Error: " + e.getMessage());
        }
    }

    private static void Deposit() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter name:");
            String name = sc.next();
            System.out.println("Enter deposit amount:");
            int balance = sc.nextInt();

            String checkQuery = "SELECT balance FROM account WHERE name = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getInt("balance");
                String updateQuery = "UPDATE account SET balance = (balance + ?) WHERE name = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                updateStmt.setInt(1, balance);
                updateStmt.setString(2, name);
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Deposit successful. New balance: " + (currentBalance + balance));
                } else {
                    System.out.println("Failed to update balance.");
                }
            } else {
                System.out.println("No account found with the given name.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to execute the deposit operation. Error: " + e.getMessage());
        }
    }
    private static void Display() {
    	try {
    		String DisplayQuery = "SELECT *FROM account";
    		PreparedStatement DisplayStmt = connection.prepareStatement(DisplayQuery);
    		
    		ResultSet rs = DisplayStmt.executeQuery();
    		
    		while(rs.next()) {
    			int id = rs.getInt("id");
    			String name = rs.getString("name");
    			int balance = rs.getInt("balance");
    			
    			System.out.println("Id: " +id+ " Name: "+name+ " Balance: "+balance);
    			
    		}
    	}catch(SQLException e) {
    		System.err.println("Failed to display data: " + e.getMessage());
    	}
    }
}

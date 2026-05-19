package com.dtc.bus.system.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBConnection {

	  private static final String URL = "jdbc:mysql://localhost:3306/dtc_bus_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	    private static final String USER = "root";
	    private static final String PASSWORD = "0807";

	    private static Connection connection;

	    static {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            System.out.println("Driver Loaded");
	        } catch (ClassNotFoundException e) {
	            throw new RuntimeException("MySQL Driver not found!", e);
	        }
	    }


	    public static Connection getConnection() throws SQLException {
	        if (connection == null || connection.isClosed()) {
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	        
	        }
	        return connection;
	    }
	}
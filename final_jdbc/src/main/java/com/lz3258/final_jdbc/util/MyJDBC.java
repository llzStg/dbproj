package com.lz3258.final_jdbc.util;

import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor
public class MyJDBC {
    private static Connection con = null;
    private static final String url="jdbc:mysql://localhost:3307/final_db";
    private static final String user="root";
    private static final String password="root";
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            con= DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(con!=null) {
            System.out.println("connected successfully");
        }
        else {
            System.out.println("connected failed");
        }
        return con;
    }


    public static void close(Connection connection) {
        if(connection!=null) {
            try {
                connection.close();
                System.out.println("database has been cut off");
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

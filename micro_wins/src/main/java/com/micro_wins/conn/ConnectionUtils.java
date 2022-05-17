package com.micro_wins.conn;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 11/05/2022 - 10:09 AM
 */
public class ConnectionUtils {

    //Whether check data connected successfully using below codes.
    public static Connection getConnection() throws SQLException,
            ClassNotFoundException {
        // Using Oracle
        // You may be replaced by other Database.
        return MySQLConnUtils.getMySQLConnection();
    }

    //
    // Test Connection ...
    //
    public static void main(String[] args) throws SQLException,
            ClassNotFoundException {

        System.out.println("Get connection ... ");

        // Get a Connection object
        Connection conn = ConnectionUtils.getConnection();

        System.out.println("Get connection " + conn);

        System.out.println("Done!");
    }
}
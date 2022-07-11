package exorpg.utils;

import java.sql.*;

public class DBManager {
    public static Connection connection = null;
    private static String user = "M2I";
    private static String password = "H3ll0M2I";

    private static String database = "florin_rpg";
    private static String url = "jdbc:mysql://51.68.227.19:3306/" + database;

    public static void init() {
        try {
            DBManager.connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
        }
    }

    public static void close() {
        try {
            DBManager.connection.close();
        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
        }
    }

    public static ResultSet execute(String sql) {
        ResultSet result = null;
        try {
            Statement statement = DBManager.connection.createStatement();
            result = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
        }
        return result;
    }

    public static int executeUpdate(String sql) {
        int result = -1;
        try {
            Statement statement = DBManager.connection.createStatement();
            result = statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("SQLException:" + ex.getMessage());
            System.out.println("SQLState:" + ex.getSQLState());
            System.out.println("VendorError:" + ex.getErrorCode());
        }
        return result;
    }

}

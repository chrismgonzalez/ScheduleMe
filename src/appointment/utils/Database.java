package appointment.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U0676g";

    //JDBC URL

    private static final String jdbcURL = protocol + vendor + ipAddress;

    // JDBC driver and db credentials
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;

    private static final String userName = "U0676g"; //Username
    private static final String password = "53688693143"; //password

    public Database() {};

    //start a DB connection
    public static Connection connect()
    {
        try{
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection successful!");

        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
            System.out.println("Not Connected");
        }
        return conn;
    }
    //close connection to the database when the application window closes
    public static void disconnect()
    {
        try {
            conn.close();
            System.out.println("Disconnected from database!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

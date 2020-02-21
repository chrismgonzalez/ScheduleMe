package appointment.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U0676g";

    //JDBC URL

    private static final String jdbcURL = protocol + vendor + ipAddress;

    // JDBC driver and db credentials
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U0676g"; //Username
    private static final String password = "53688693143"; //password

    //connect to the database on application launch
    public static Connection startConnection()
    {
        try{
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");

        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        return conn;
    }
    //close connection to the database when the application window closes
    public static void closeConnection()
    {
        try {
            conn.close();
            System.out.println("Connection closed!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

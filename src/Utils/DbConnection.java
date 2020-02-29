package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.ZoneId;

public class DbConnection {

    // DB URL and credentials
    private static final String URL = "jdbc:mysql://3.227.166.251/U0676g";
    private static final String USER = "U0676g"; //Username
    private static final String PASSWORD = "53688693143"; //password
    public static final ZoneId DB_TIME_ZONE = ZoneId.of("UTC");

    // singleton pattern instantiation

    private static DbConnection sInstance;
    private final Connection connection;

    //start a DB connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful!");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Not Connected");
        }
        return conn;
    }

    private DbConnection(Connection connection) {
        this.connection = connection;
    }

    public static DbConnection getInstance() {
        if(sInstance == null) {
            synchronized (DbConnection.class) {
                if (sInstance == null) {
                    sInstance = new DbConnection(getConnection());
                }
            }
        }
        return sInstance;
    }

    public Connection connection() {
        return connection;
    }
    //close connection to the database when the application window closes
    public void close() {
        try {
            connection().close();
            System.out.println("Disconnected from database!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

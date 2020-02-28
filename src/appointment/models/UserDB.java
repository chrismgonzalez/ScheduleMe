package appointment.models;

import appointment.utils.Database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class UserDB {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    //attempt to login to the database

    public static Boolean login(String userName, String password) {
        try {
            Statement statement = Database.connect().createStatement();
            String query = "SELECT * FROM user WHERE userName='" + userName + "' AND password='" + password + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                currentUser = new User();
                currentUser.setUsername(results.getString("username"));
                statement.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }
}

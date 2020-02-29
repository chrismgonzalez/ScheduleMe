package DAO;

import DataModels.User;
import javafx.collections.ObservableList;

public interface UserDao {
    Integer getUserIdByUserNameAndPassword(String userName, String password);
    ObservableList<User> getAllActiveUsers();

}

package DataModels;

public class User {
    //info about user here
    private int userId;
    private String userName;
    private boolean active;

    public User(int userId, String userName, boolean active) {
        this.userId = userId;
        this.userName = userName;
        this.active = active;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive (boolean active) {
        this.active = active;
    }

    public String getUserName() {
        return userName;
    }

    public void setUsername(String userName) {

        this.userName = userName;
    }

    @Override
    public String toString() {
        return userName;
    }
}

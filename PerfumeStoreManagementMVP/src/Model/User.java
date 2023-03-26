package Model;

public class User {
    private int userId;
    private String username;
    private String password;
    private UserType userType;
    private int managerId;
    private int storeID;

    public User(int userId, String username, String password, UserType userType, int managerId, int storeID) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.managerId = managerId;
        this.storeID = storeID;

    }

    public User( String username, String password, UserType userType, int managerId, int storeID) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.managerId = managerId;
        this.storeID = storeID;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }


}

package Model;




public class User implements Observer {
    private int userId;
    private String username;
    private String password;
    private UserType userType;
    private int managerId;
    private int storeID;

    private String account = "";


    public User(int userId, String username, String password, UserType userType, int managerId, int storeID) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.managerId = managerId;
        this.storeID = storeID;

    }

    public User(int userId, String username, String password, UserType userType) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userType = userType;

    }

    public User(){}

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


    @Override
    public void update() {
        System.out.println("Hey, " + username + "! The admin updated this account: " + account);
    }

    @Override
    public void setAccount(String account){
        this.account = account;
    }
}

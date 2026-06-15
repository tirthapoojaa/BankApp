package model;

public abstract class User {

    protected int userId;

    protected String name;

    protected String username;

    protected String password;

    public User() {
    }

    public User(
            int userId,
            String name,
            String username,
            String password) {

        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
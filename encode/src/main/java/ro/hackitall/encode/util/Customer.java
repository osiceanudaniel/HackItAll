package ro.hackitall.encode.util;

/**
 * Created by Andrei-Daniel Ene on 3/24/2018.
 */
public class Customer {
    private String userId;
    private String email;

    public Customer() {}

    public Customer(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

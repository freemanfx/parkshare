package ro.parkshare.parkshare.api;

import static ro.parkshare.parkshare.helper.ValidationHelper.validateStringMinSize;

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = validateStringMinSize("Username", username, 1);
        this.password = validateStringMinSize("Password", password, 1);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

package ro.parkshare.parkshare.api;

public class UserAuthentication {
    private Long userId;
    private String authToken;

    public UserAuthentication(Long userId, String authToken) {
        this.userId = userId;
        this.authToken = authToken;
    }

    public UserAuthentication() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

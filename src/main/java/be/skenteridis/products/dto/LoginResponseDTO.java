package be.skenteridis.products.dto;

public class LoginResponseDTO {
    private final boolean success = true;
    private final String message = "Logged in successfully!";
    private String username;
    private String token;

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package org.example.dto;

public class TokenResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String message;

    public TokenResponse(String accessToken, String message) {
        this.accessToken = accessToken;
        this.message = message;
    }

    // Getters e Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
package com.project.lostfound.entity;

public class UserRequest {
    private String username;
    private String password;
    private String role;

    public User toUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        return user;
    }

    public String getRole() {
        return this.role;
    }
    
    public String getUsername() {
        return this.username;
    }

	public String getPassword() {
        return this.password;
    }
}

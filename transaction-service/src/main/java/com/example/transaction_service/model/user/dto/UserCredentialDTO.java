package com.example.transaction_service.model.user.dto;

import com.example.transaction_service.model.user.entity.User;
import com.example.transaction_service.model.user.role.entity.UserRole;

import java.util.List;
import java.util.Objects;

public class UserCredentialDTO {
    private User user;
    private List<UserRole> userRole;

    public UserCredentialDTO(User user, List<UserRole> userRole) {
        this.user = user;
        this.userRole = userRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserCredentialDTO that = (UserCredentialDTO) o;
        return Objects.equals(user, that.user) && Objects.equals(userRole, that.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, userRole);
    }

    @Override
    public String toString() {
        return "UserCredential{" +
                "user=" + user +
                ", userRole=" + userRole +
                '}';
    }
}

package com.example.blocking_service.model.role.enumeration;

public enum RoleEnumeration {
    ROLE_USER(1), ROLE_ADMIN(2);
    private long id;

    RoleEnumeration(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

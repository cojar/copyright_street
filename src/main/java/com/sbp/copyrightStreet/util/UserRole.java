package com.sbp.copyrightStreet.util;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("admin"),
    MEMBERSHIP("membership"),
    PREMIUM("premium"),
    ARTIST("artist"),
    USER("user");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
package com.sbp.copyrightStreet.boundedContext.notice;

import lombok.Getter;

@Getter
public enum NoticeRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    NoticeRole(String value){
        this.value = value;
    }
    private String value;
}

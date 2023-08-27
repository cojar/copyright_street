package com.sbp.copyrightStreet.base.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UploadResponse {
    private String status;
    private String imageUrl;

    public UploadResponse(String status, String imageUrl) {
        this.status = status;
        this.imageUrl = imageUrl;
    }
}

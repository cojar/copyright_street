package com.sbp.copyrightStreet.boundedContext.email;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private String message;
    private List<MultipartFile> attachedFiles;
    private String recipientEmail;
}
package com.sbp.copyrightStreet.boundedContext.file;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Component
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String primaryPath;

    private String secondaryPath;

    private String uploader;

    private String date;

    private String ext;

    @CreatedDate
    private LocalDateTime createDate;
}
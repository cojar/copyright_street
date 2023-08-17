package com.sbp.copyrightStreet.boundedContext.file;

import com.sbp.copyrightStreet.boundedContext.artist.Artist;
import jakarta.persistence.*;
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

    @ManyToOne
    private Artist artist;

    @CreatedDate
    private LocalDateTime createDate;
}
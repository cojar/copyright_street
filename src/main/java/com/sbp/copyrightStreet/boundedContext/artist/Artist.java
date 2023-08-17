package com.sbp.copyrightStreet.boundedContext.artist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column

    private String username;

    @Column
    private String email;


    private String phoneNumber;


    @Column(columnDefinition = "TEXT")
    private String introDetail;


    @Column
    private String portfolio;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}


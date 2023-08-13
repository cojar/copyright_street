package com.sbp.copyrightStreet.boundedContext.artist;
import com.sbp.copyrightStreet.boundedContext.file.File;
import com.nimbusds.openid.connect.sdk.claims.Address;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "artist")
    private List<File> files;

    @Column
    private String portfolio;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}


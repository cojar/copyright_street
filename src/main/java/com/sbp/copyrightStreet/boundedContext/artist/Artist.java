package com.sbp.copyrightStreet.boundedContext.artist;

import com.sbp.copyrightStreet.boundedContext.file.File;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Member username;

    @OneToOne
    private Member email;

    @OneToOne
    private Member phoneNumber;

    //파일업로드
//   @OneToOne
//   private File portfolio;

    @Column(columnDefinition = "TEXT")
    private String introDetail;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}


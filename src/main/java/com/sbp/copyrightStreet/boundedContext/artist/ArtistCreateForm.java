package com.sbp.copyrightStreet.boundedContext.artist;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArtistCreateForm {
    @NotEmpty
    private String username;

    private String introDetail; // 신청내용

    @NotEmpty
    private String phoneNumber;

    private String email;

    @Column
    private MultipartFile portfolio;

}

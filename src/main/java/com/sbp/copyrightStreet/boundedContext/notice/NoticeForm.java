package com.sbp.copyrightStreet.boundedContext.notice;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeForm {
    @Size(min = 1, max = 25)
    @NotEmpty(message = "제목은 필수입니다.")
    private String title;
    @NotEmpty(message = "내용은 필수입니다.")
    private String content;
    @CreatedDate
    private LocalDateTime createDate;
}
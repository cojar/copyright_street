package com.ll.exam.project.borad;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
    @NotEmpty(message = "제목은 필수항목입니다.")
    @Size
    private String title;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}

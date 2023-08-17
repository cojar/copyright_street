package com.sbp.copyrightStreet.boundedContext.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Getter
@Setter
public class MemberModifyForm {
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotEmpty(message = "새 비밀번호를 입력해주세요.")
    private String newPW;

    @NotEmpty(message = "새 비밀번호를 다시 입력해주세요.")
    private String newPW2;
}

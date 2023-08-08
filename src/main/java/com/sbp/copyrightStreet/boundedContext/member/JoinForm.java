package com.sbp.copyrightStreet.boundedContext.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinForm {
    @NotBlank // 비어있지 않아야 하고, 공백으로만 이루어 지지도 않아야 한다.
    private  String username;
    @NotBlank
    private  String loginId;
    @NotBlank
    @Size(min = 4, max = 30)
    private  String password;
    @NotBlank
    @Size(min = 4, max = 30)
    private  String password2;
    @NotBlank
    private  String phoneNumber;

    @NotBlank
    private  String email;
}

package com.sbp.copyrightStreet.boundedContext.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter // joinForm.getUsername() 이런 코드 가능하게
public class MemberForm {
    @NotBlank // 비어있지 않아야 하고, 공백으로만 이루어 지지도 않아야 한다.
    @Size(min = 4, max = 30) // 4자 이상, 30자 이하
    private  String username;
    @NotBlank
    @Size(min = 4, max = 30)
    private  String password;
}
package com.sbp.copyrightStreet;

import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestApp {
    private  final MemberService memberService;
    public static String run(String exp) {
        boolean needToPlus = exp.contains("+");

        String[] bits = null;
        if (needToPlus) {
            bits = exp.split("\\+");
        }

        String a = bits[0];
        String b = bits[1];
        if (needToPlus) {
            return "a" + "b";
        }
        throw new RuntimeException("올바른식이 아닙니다.");
    }
}

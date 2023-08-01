package com.sbp.copyrightStreet.boundedContext.artist;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/artist")
public class ArtistController {
    private final ArtistService artistService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public String register(Model model, ArtistCreateForm artistCreateForm, Principal principal) {

        Member memberInfo = this.memberService.getUserByLoginId(principal.getName());
        artistCreateForm.setUsername(memberInfo.getUsername());
        String username = principal.getName();
        Member member = memberService.getUserByLoginId(username);
        model.addAttribute("username", member.getUsername());
        model.addAttribute("email", member.getEmail());
        return "artist_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public String register(Model model,
                           @Valid ArtistCreateForm artistCreateForm,
                           BindingResult bindingResult, Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            return "registration-page";
        }

        Member memberInfo = this.memberService.getUserByLoginId(principal.getName());
        Member email = this.memberService.getUserByEmail(memberInfo.getEmail());

        // 아티스트 기본 정보 저장
        Artist artist = this.artistService.create(memberInfo,
                artistCreateForm.getPhoneNumber(),
                email,
                artistCreateForm.getIntroDetail());

        return String.format("redirect:/member/%s", memberInfo.getId());
    }
}

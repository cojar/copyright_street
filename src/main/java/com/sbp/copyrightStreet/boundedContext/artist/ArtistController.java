package com.sbp.copyrightStreet.boundedContext.artist;

import com.sbp.copyrightStreet.DataNotFoundException;
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
        Member member = memberService.getUserByLoginId(principal.getName());
        artistCreateForm.setUsername(member.getUsername());

        return "artist/artist_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public String register(Model model,
                           @Valid ArtistCreateForm artistCreateForm,
                           BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            return "registration-page";
        }

        String username = principal.getName();
        Member memberInfo = memberService.getUserByLoginId(username);

        if (memberInfo == null) {
            model.addAttribute("error", "유효하지 않은 사용자 정보입니다.");
            return "error-page";
        }

        Member email = memberService.getUserByEmail(memberInfo.getEmail());
        Artist artist = artistService.create(memberInfo,
                artistCreateForm.getPhoneNumber(),
                email,
                artistCreateForm.getIntroDetail());

        return String.format("redirect:/member/%s", memberInfo.getId());
    }
}
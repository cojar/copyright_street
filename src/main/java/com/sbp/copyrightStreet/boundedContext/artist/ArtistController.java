package com.sbp.copyrightStreet.boundedContext.artist;

import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;  // Import Log4j's Logger
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.Principal;


@RequiredArgsConstructor
@Controller
@RequestMapping("/artist")
public class ArtistController {
    private final ArtistService artistService;
    private final MemberService memberService;
    private static final Logger LOGGER = LogManager.getLogger(ArtistController.class);

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public String register(Model model, ArtistCreateForm artistCreateForm, Principal principal) {
        String logInUsername = principal.getName();
        artistCreateForm.setUsername(logInUsername);
        return "artist/form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public String register(Model model,
                           @Valid ArtistCreateForm artistCreateForm,
                           BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            return "artist/form";
        }

        Artist artist = artistService.create(artistCreateForm.getUsername(),
                artistCreateForm.getPhoneNumber(),
                artistCreateForm.getEmail(),
                artistCreateForm.getIntroDetail());

        model.addAttribute("successArtistRegistration", true);
        LOGGER.info("Artist registration successful: " + model.getAttribute("successArtistRegistration"));
        return "redirect:/";
    }
}
package com.sbp.copyrightStreet.boundedContext.mypage;

import com.sbp.copyrightStreet.base.dto.UploadResponse;
import com.sbp.copyrightStreet.boundedContext.cart.CartService;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberModifyForm;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import com.sbp.copyrightStreet.boundedContext.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;
    private StoreService storeService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myProfile")
    public String mypage(Model model, Principal principal) {
        Member member = this.memberService.getUser(principal.getName());
        model.addAttribute("member", member);
        return "mypage/mypage";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyPassword")
    public String modifyPassword(MemberModifyForm memberModifyForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "mypage/mypage";
        }

        Member member = this.memberService.getUser(principal.getName());
        if (!memberService.confirmPassword(memberModifyForm.getPassword(), member)) {
            bindingResult.rejectValue("password", "passwordInCorrect",
                    "현재 비밀번호를 바르게 입력해주세요.");
            return "mypage/mypage";
        }

        // 비밀번호와 비밀번호 확인에 입력한 문자열이 서로 다르면 다시 입력 하도록
        if (!memberModifyForm.getNewPW().equals(memberModifyForm.getNewPW2())) {
            bindingResult.rejectValue("newPW2", "passwordInCorrect",
                    "입력한 비밀번호가 일치하지 않습니다.");
            return "mypage/mypage";
        }
        memberService.modifyPassword(memberModifyForm.getNewPW(), member);

        return "redirect:/mypage/myProfile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/uploadProfileImage")
    public ResponseEntity<UploadResponse> uploadProfileImage(@RequestParam("profileImage") MultipartFile file, Principal principal) {
        try {
            // 이미지 파일 종류 확인
            if (!isValidImageFile(file)) {
                return ResponseEntity.badRequest().body(new UploadResponse("error", "Invalid image file type"));
            }

            // 저장 경로 설정
            String UPLOAD_DIR = "ProfileImg/";
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + principal.getName() + "_" + file.getOriginalFilename());
            Files.write(path, bytes);

            // 사용자 프로필 이미지 URL 업데이트
            String imageUrl = "/ProfileImg/" + principal.getName() + "_" + file.getOriginalFilename();
            memberService.updateProfileImage(principal.getName(), imageUrl);

            return ResponseEntity.ok(new UploadResponse("success", imageUrl));

        } catch (Exception e) {
            // 이미지 업로드 중 오류 발생
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UploadResponse("error", "Image upload failed"));
        }

    }

    private boolean isValidImageFile(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"));
    }
}
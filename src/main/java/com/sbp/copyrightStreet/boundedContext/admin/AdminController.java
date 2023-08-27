package com.sbp.copyrightStreet.boundedContext.admin;

import com.sbp.copyrightStreet.boundedContext.article.borad.Board;
import com.sbp.copyrightStreet.boundedContext.article.borad.BoardService;
import com.sbp.copyrightStreet.boundedContext.artist.Artist;
import com.sbp.copyrightStreet.boundedContext.artist.ArtistService;
import com.sbp.copyrightStreet.boundedContext.cart.Cart;
import com.sbp.copyrightStreet.boundedContext.cart.CartService;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import com.sbp.copyrightStreet.boundedContext.product.Product;
import com.sbp.copyrightStreet.boundedContext.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm")
public class AdminController {

    private final BoardService boardService;

    private final MemberService memberService;
    private final ArtistService artistService;

//    private final NoticeService noticeService;
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
    public String index() {
        return "redirect:/adm/home/main";
    }

    @GetMapping("/home/main")
    @PreAuthorize("hasAuthority('ADMIN')") // admin 권한을 가진 사람만 접근 가능하다는 뜻
    public String showMain() {
        return "adm/home/main";
    }

    @GetMapping("/list")
    public String List(Model model) {
        List<Member> memberList = this.memberService.getAll();
        List<Board> articleList = this.boardService.getAll();
        List<Product> productList = this.productService.getAll();
        model.addAttribute("memberList", memberList);
        model.addAttribute("articleList", articleList);
        model.addAttribute("productList", productList);
        return "admin/list";
    }

    @GetMapping("/artistList")
    public String artistList(Model model) {
        List<Artist> artistList = this.artistService.getAll();
        model.addAttribute("artistList", artistList);
        return "admin/artistList";
    }
    @GetMapping("/artistList/create")
    public String artistListCreate(Model model) {
        List<Cart> cartList = this.cartService.getAll();
        model.addAttribute("cartList", cartList);
        return "admin/cartList";
    }
}

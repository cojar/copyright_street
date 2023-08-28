package com.sbp.copyrightStreet.base.initData;

import com.sbp.copyrightStreet.boundedContext.member.MemberService;
import com.sbp.copyrightStreet.boundedContext.product.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdInitData implements BeforeInitData{
    @Bean
    CommandLineRunner initData(MemberService memberService, ProductService productService){
        return args -> {
            beforeInit();
            String password= "{noop}1234";

            memberService.join("admin","admin","1234","user1@test.com","01012345678");
            memberService.join("문창빈","admin2",password,"user2@test.com","01012345677");
            memberService.join("김다은","admin3",password,"user3@test.com","01012345676");
            memberService.join("나현아","admin4",password,"user4@test.com","01012345675");
        };
    }
}

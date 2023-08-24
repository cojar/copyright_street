package com.sbp.copyrightStreet.base.initData.initData;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestInitData implements BeforeInitData{
    @Bean
    CommandLineRunner initData(){
        return args -> {
            beforeInit();
        };
    }
}

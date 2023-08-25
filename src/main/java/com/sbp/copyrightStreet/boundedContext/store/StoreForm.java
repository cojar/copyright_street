package com.sbp.copyrightStreet.boundedContext.store;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreForm {

    @NotEmpty
    private String title; // 상품이름

    @NotEmpty
    private String price;  // 상품가격

    @NotEmpty
    private String content; // 상품설명

    private String category;

    @NotEmpty
    private String filepath;

    @NotEmpty
    private String filename;

}

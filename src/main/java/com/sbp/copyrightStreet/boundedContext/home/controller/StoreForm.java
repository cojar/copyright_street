package com.sbp.copyrightStreet.boundedContext.home.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreForm {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String filepath;

    @NotEmpty
    private String filename;

}

package com.sbp.copyrightStreet.boundedContext.home.controller;

import com.sbp.copyrightStreet.boundedContext.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Data
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private String content;

    private LocalDateTime create_Date;

    private String filepath;

    private String filename;

    public String getFile(){
        return filepath.replaceAll("/Users/munchangbin/Downloads/copyright_street/src/main/resources/static","");
    }

}

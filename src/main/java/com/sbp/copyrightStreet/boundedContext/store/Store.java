package com.sbp.copyrightStreet.boundedContext.store;

import com.sbp.copyrightStreet.boundedContext.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    private int hitCount;

    private String category;
    public String getFile(){
        return filepath.replaceAll("/Users/munchangbin/Downloads/copyright_street/src/main/resources/static","");
    }


}

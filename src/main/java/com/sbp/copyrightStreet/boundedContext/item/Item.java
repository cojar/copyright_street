package com.sbp.copyrightStreet.boundedContext.item;

import com.sbp.copyrightStreet.boundedContext.file.File;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "item")
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String itemName;//상품이름

    @Column
    private int price; //상품 가격

//    @Column
//    private File itemImage; //상품이미지 구현예정

    @Column
    private String introDetail; // 상품 설명

    private LocalDateTime regTime; //상품 등록시간

    private LocalDateTime modifyTime; //상품 수정시간
}

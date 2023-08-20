package com.sbp.copyrightStreet.boundedContext.store;

import com.sbp.copyrightStreet.boundedContext.cart.Cart;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;


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

    private String price;

    private LocalDateTime create_Date;

    private String filepath;

    private String filename;

    private int hitCount;

    private String category;
    public String getFile(){
        return filepath.replaceAll("/Users/munchangbin/Downloads/copyright_street/src/main/resources/static","");

    }
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Cart> cartList;

    @OneToMany
    private List<Store> storeList;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // Store와 Member 간의 관계 설정

}

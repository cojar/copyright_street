    package com.sbp.copyrightStreet.boundedContext.cart;


    import com.sbp.copyrightStreet.boundedContext.member.Member;
    import com.sbp.copyrightStreet.boundedContext.product.Product;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import com.sbp.copyrightStreet.boundedContext.store.Store;
    import org.springframework.data.annotation.CreatedDate;

    import java.time.LocalDateTime;

    @Getter
    @Setter
    @Entity
    public class Cart {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        private String content;

        private String filepath;

        private String filename;

        private int hitCount;

        private String category;

        @ManyToOne
        private Member member;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "store_id")
        private Product product;

        public String getFile() {
            return filepath.replaceAll("/Users/munchangbin/Downloads/copyright_street/src/main/resources/static", "");


        }
        private LocalDateTime createDate;

        private LocalDateTime modifyDate;
    }

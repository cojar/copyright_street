    package com.sbp.copyrightStreet.boundedContext.cart;


    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import com.sbp.copyrightStreet.boundedContext.store.Store;

    import java.time.LocalDateTime;

    @Getter
    @Setter
    @Entity
    public class Cart {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String title;

        private String content;

        private LocalDateTime create_Date;

        private String filepath;

        private String filename;

        private int hitCount;

        private String category;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "store_id")
        private Store store;

        public String getFile() {
            return filepath.replaceAll("/Users/munchangbin/Downloads/copyright_street/src/main/resources/static", "");
        }
    }

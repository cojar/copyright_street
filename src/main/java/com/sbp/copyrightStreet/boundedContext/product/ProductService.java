package com.sbp.copyrightStreet.boundedContext.product;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    public Long create(String name, String description, int price, MultipartFile thumbnail) {
        String thumbnailRelPath = "product/" + UUID.randomUUID().toString() + ".jpg";
        File thumbnailFile = new File(genFileDirPath +"/" + thumbnailRelPath);

        thumbnailFile.mkdir();

        try {
            thumbnail.transferTo(thumbnailFile);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setDescription(description);
        p.setThumbnailImg(thumbnailRelPath);
        p.setCreateDate(LocalDateTime.now());
        this.productRepository.save(p);
        Product createdProduct = this.productRepository.save(p);
        return createdProduct.getId();
    }


    public Page<Product> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 30, Sort.by(sorts));
        return this.productRepository.findAllByKeyword(kw, pageable);
    }

    public List<Product> getList() {
        return this.productRepository.findAll();
    }
    public Product getProduct(Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RuntimeException("product not found");
        }
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();

    }
}

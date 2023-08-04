package com.sbp.copyrightStreet.boundedContext.home.controller;


import com.sbp.copyrightStreet.boundedContext.home.controller.Cart.Cart;
import com.sbp.copyrightStreet.boundedContext.home.controller.Cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final CartRepository cartRepository;

    public static void increaseHitCount() {

    }


    public void create(String title, String content, String category, MultipartFile file) throws Exception {
        String projectPath = System.getProperty("user.dir") + "//src//main//resources//static//files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File savefile = new File(projectPath, fileName);
        file.transferTo(savefile);
        Store store = new Store();
        store.setTitle(title);
        store.setContent(content);
        store.setCategory(category);
        store.setCreate_Date(LocalDateTime.now());
        store.setFilepath(savefile.toString());
        store.setFilename(fileName);
        this.storeRepository.save(store);
    }


    public Page<Store> getList(int page, String kw) {
        Pageable pageable = PageRequest.of(page , 3); // page 값 1 감소
        return this.storeRepository.findAll(pageable);

    }

    public Store getStore(Integer id) {
        Optional<Store> store = this.storeRepository.findById(id);
        if (store.isPresent()) {
            Store store1 = store.get();
            store1.setHitCount(store1.getHitCount() + 1);
            this.storeRepository.save(store1);
            return store1;
        } else {
            throw new DataNotFoundException("store not found");
        }
    }

    public void modify(Integer id, String title, String content, String category, MultipartFile file) throws Exception {
        Store store = getStore(id);
        if (store == null) {
            throw new Exception("해당 id가 없습니다");
        }
        String projectPath = System.getProperty("user.dir") + "//src//main//resources//static//files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File savefile = new File(projectPath, fileName);
        file.transferTo(savefile);
        store.setTitle(title);
        store.setContent(content);
        store.setCategory(category);
        store.setFilename(fileName);
        store.setFilepath(savefile.toString());
        this.storeRepository.save(store);
    }

    public void delete(Store store) {

        this.storeRepository.delete(store);
    }



    public void cart(Integer id) {
        Optional<Store> store = this.storeRepository.findById(id);
        if (store.isPresent()) {
            Store store1 = store.get();
            Cart cart = new Cart();
            cart.setTitle(store1.getTitle());
            cart.setContent(store1.getContent());
            cart.setCreate_Date(LocalDateTime.now());
            cart.setFilepath(store1.getFilepath());
            cart.setFilename(store1.getFilename());
            cart.setCategory(store1.getCategory());
            this.cartRepository.save(cart);
        }
    }
}

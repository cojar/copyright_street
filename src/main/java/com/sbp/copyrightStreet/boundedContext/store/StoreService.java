package com.sbp.copyrightStreet.boundedContext.store;


import com.sbp.copyrightStreet.boundedContext.article.borad.Board;
import com.sbp.copyrightStreet.boundedContext.cart.Cart;
import com.sbp.copyrightStreet.boundedContext.cart.CartRepository;
import com.sbp.copyrightStreet.boundedContext.cart.CartService;
import com.sbp.copyrightStreet.boundedContext.home.controller.DataNotFoundException;
import com.sbp.copyrightStreet.boundedContext.member.Member;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;

    public static void increaseHitCount() {

    }


    public void create(Member member,String title, String content, String category, MultipartFile file) throws Exception {
        String projectPath = System.getProperty("user.dir") + "//src//main//resources//static//files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File savefile = new File(projectPath, fileName);
        file.transferTo(savefile);
        Store store = new Store();
        store.setMember(member);
        store.setTitle(title);
        store.setContent(content);
        store.setCategory(category);
        store.setCreate_Date(LocalDateTime.now());
        store.setFilepath(savefile.toString());
        store.setFilename(fileName);
        this.storeRepository.save(store);
    }

    private Specification<Store> search(String kw) {
        return new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                return cb.like(root.get("title"), "%" + kw + "%");
            }
        };
    }


    public Page<Store> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("create_Date"));

        Pageable pageable = PageRequest.of(page, 3); // page 값 1 감소
        Specification<Store> spec = search(kw);
        return this.storeRepository.findAll(spec,pageable);
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

//    @Transactional
//    public void delete(Integer storeId) {
//        Store store = this.getStore(storeId);
//
//        List<Cart> cartList = this.cartRepository.findByStoreId(storeId);
//        this.cartRepository.deleteAll(cartList);
//
//        this.storeRepository.delete(store);
//    }


    public Store cart(Integer id) {
        Optional<Store> store = this.storeRepository.findById(id);
        if (store.isPresent()) {
            Store store1 = store.get();
            Cart cart = new Cart();
            cart.setTitle(store1.getTitle());
            cart.setContent(store1.getContent());
            cart.setCreateDate(LocalDateTime.now());
            cart.setFilepath(store1.getFilepath());
            cart.setFilename(store1.getFilename());
            cart.setCategory(store1.getCategory());
//            cart.setStore(store1);
            this.cartRepository.save(cart);
        }
        return null;
    }
    public List<Store> list(Member member ){

        return this.storeRepository.findByMember(member);
    }
}

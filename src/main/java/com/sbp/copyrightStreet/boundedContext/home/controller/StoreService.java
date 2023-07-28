package com.sbp.copyrightStreet.boundedContext.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
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

    public void create(String title, String content , MultipartFile file) throws Exception {
        String projectPath = System.getProperty("user.dir") + "//src//main//resources//static//files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File savefile = new File(projectPath,fileName);
        file.transferTo(savefile);

        Store store = new Store();
        store.setTitle(title);
        store.setContent(content);
        store.setCreate_Date(LocalDateTime.now());
        store.setFilepath(savefile.toString());
        store.setFilename(fileName);
        this.storeRepository.save(store);
    }


    public List<Store> getList() {
        return this.storeRepository.findAll();
    }

    public Store getStore(Integer id) {
        Optional<Store> store = this.storeRepository.findById(id);
        if(store.isPresent()){
            return store.get();
        }else{
            throw new DataNotFoundException("store not found");
        }
    }

    public void modify(Integer id,String title , String content,MultipartFile file)throws Exception {
        Store store = getStore(id);
        if(store == null){
            throw new Exception("해당 id가 없습니다");
        }
        String projectPath = System.getProperty("user.dir") + "//src//main//resources//static//files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File savefile = new File(projectPath,fileName);
        file.transferTo(savefile);
        store.setTitle(title);
        store.setContent(content);
        store.setFilename(fileName);
        store.setFilepath(savefile.toString());
        this.storeRepository.save(store);
    }
}

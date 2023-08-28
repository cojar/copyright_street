package com.sbp.copyrightStreet.boundedContext.artist;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;
    @Value("${custom.portfolioPath}")
    private String portfolioDirPath;

    //포트폴리오 추가예정
    public Artist create(String username, String phoneNumber, String email, String introDetail, MultipartFile portfolio) {
        String portfolioRelPath = "portfolio/" + UUID.randomUUID().toString() + ".jpg";
        File thumbnailFile = new File(portfolioDirPath +"/" + portfolioRelPath);

        thumbnailFile.mkdir();

        try {
            portfolio.transferTo(thumbnailFile);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        Artist artist = new Artist();
        artist.setUsername(username);
        artist.setEmail(email);
        artist.setPhoneNumber(phoneNumber);
        artist.setPortfolio(portfolioRelPath);
        artist.setIntroDetail(introDetail);
        artist.setCreateDate(LocalDateTime.now());
        this.artistRepository.save(artist);
        return artist;
    }

    public boolean isRegistered(String username) {

        return this.artistRepository.findByUsername(username).isPresent();
    }

    public List<Artist> getAll() {
       return this.artistRepository.findAll();
    }
}

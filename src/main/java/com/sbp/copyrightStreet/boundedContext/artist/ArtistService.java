package com.sbp.copyrightStreet.boundedContext.artist;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    //포트폴리오 추가예정
    public Artist create(String username, String phoneNumber, String email, String introDetail) {

        Artist artist = new Artist();
        artist.setUsername(username);
        artist.setEmail(email);
        artist.setPhoneNumber(phoneNumber);
        artist.setIntroDetail(introDetail);
        artist.setCreateDate(LocalDateTime.now());
        this.artistRepository.save(artist);
        return artist;
    }
    public boolean isRegistered(String username) {
        return this.artistRepository.findByUsername(username).isPresent();
    }
}

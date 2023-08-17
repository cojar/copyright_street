package com.sbp.copyrightStreet.boundedContext.artist;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByUsername(String username);
}

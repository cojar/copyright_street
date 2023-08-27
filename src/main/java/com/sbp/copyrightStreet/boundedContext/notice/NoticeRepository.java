package com.sbp.copyrightStreet.boundedContext.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    Page<Notice> findAll(Pageable pageable);// 페이징 기능

    Optional<Notice> findTopByIdLessThanOrderByIdDesc(Integer id);// 이전글이 있다면 가져오는 기능(?)

    Optional<Notice> findTopByIdGreaterThanOrderByIdAsc(Integer id);// 다음글이 있다면 가져오는 코드 (?)


}
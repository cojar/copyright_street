package com.sbp.copyrightStreet.boundedContext.notice;

import com.sbp.copyrightStreet.boundedContext.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public void Create(String title, String content, Member admin) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setCreateDate(LocalDateTime.now());
        notice.setAdmin(admin);
        this.noticeRepository.save(notice);
    }

    public Page<Notice> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.noticeRepository.findAll(pageable);
    }

    public Notice Detail(Integer id) {
        Optional<Notice> notice = this.noticeRepository.findById(id);
        if (notice.isPresent()) {
            Notice notices = notice.get();
            notices.setHitCount(notices.getHitCount() + 1);
            this.noticeRepository.save(notices);
            return notices;
        } else {
            return null;
        }
    }

    public static void increaseHitCount() {//조회수
    }

    public void Modify(Notice notice, String title, String content) {
        notice.setTitle(title);
        notice.setContent(content);
        this.noticeRepository.save(notice);
    }

    public void Delete(Notice notice) {
        this.noticeRepository.delete(notice);
    }

    public Notice PreviousNotice(Integer id) {//이전글
        return noticeRepository.findTopByIdLessThanOrderByIdDesc(id).orElse(null);
    }

    public Notice NextNotice(Integer id) {//다음글
        return noticeRepository.findTopByIdGreaterThanOrderByIdAsc(id).orElse(null);
    }
}

package com.sbp.copyrightStreet.boundedContext.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public void Create(String title, String content, String admin) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setCreateDate(LocalDateTime.now());
        notice.setAdmin(admin);
        this.noticeRepository.save(notice);
    }
    public List<Notice> List() {
        return this.noticeRepository.findAll();
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
    public static void increaseHitCount() {

    }

    public void Modify(Notice notice,String title, String content) {
        notice.setTitle(title);
        notice.setContent(content);
        this.noticeRepository.save(notice);
    }

    public void Delete(Notice notice) {
        this.noticeRepository.delete(notice);
    }


}

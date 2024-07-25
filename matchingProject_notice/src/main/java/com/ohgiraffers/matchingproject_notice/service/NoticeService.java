package com.ohgiraffers.matchingproject_notice.service;

import com.ohgiraffers.matchingproject_notice.model.dto.NoticeDTO;
import com.ohgiraffers.matchingproject_notice.model.entitiy.Notice;
import com.ohgiraffers.matchingproject_notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;


@Service
public class NoticeService {

    private static NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;

    }

    @Transactional
    public int regist(NoticeDTO noticeDTO) {
        List<Notice> notices = noticeRepository.findAll();
        for (Notice notice : notices) {
            if (notice.getNoticeTitle().equals(noticeDTO.getNoticeTitle())) {
                return 0;
            }
        }
                Notice saveNotice = new Notice();
                saveNotice.setNoticeTitle(noticeDTO.getNoticeTitle());
                saveNotice.setNoticeContent(noticeDTO.getNoticeContent());
                saveNotice.setCreateDate(new Date());
                Notice result = noticeRepository.save(saveNotice);

                return result != null ? 1 : 0;
            }




        }




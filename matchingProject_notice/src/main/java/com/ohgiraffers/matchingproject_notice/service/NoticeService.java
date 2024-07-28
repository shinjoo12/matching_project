package com.ohgiraffers.matchingproject_notice.service;

import com.ohgiraffers.matchingproject_notice.model.dto.NoticeDTO;
import com.ohgiraffers.matchingproject_notice.model.entitiy.Notice;
import com.ohgiraffers.matchingproject_notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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


    public List<Notice> getAllPosts() {
        return noticeRepository.findAll();
    }

    public NoticeDTO getPostById(int id) {
        Optional<Notice> noticeOptional = noticeRepository.findById(id);
        if (noticeOptional.isPresent()) {
            Notice notice = noticeOptional.get();
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setId(notice.getId());
            noticeDTO.setNoticeTitle(notice.getNoticeTitle());
            noticeDTO.setNoticeContent(notice.getNoticeContent());
            return noticeDTO;
        }
        return null;
    }
//
//    // 게시글 수정
//    public NoticeDTO updateNotice(int id, NoticeDTO boardDTO) {
//        // 게시글 업데이트 로직
//        // 게시글 ID를 사용하여 데이터베이스에서 게시글을 조회
//        Notice updatePost = noticeRepository.findById(id).get();
//        // optionalPost.isPresent() 메서드는 optionalPost가 값(게시글)을 가지고 있는지 확인
//
//        if (updatePost == null) {
//            return null;
//        }
//        // optionalPost가 값(게시글)을 가지고 있다면, 해당 게시글 객체를 get으로 가져옴
//        // 클라이언트가 전송한 업데이트된 게시글 제목과 내용으로 기존 게시글을 업데이트
//        // 업데이트된 제목 설정
//        updatePost.setNoticeTitle(boardDTO.getNoticeTitle());
//        // 업데이트된 내용 설정
//        updatePost.setNoticeContent(boardDTO.getNoticeContent());
//        // 변경된 내용을 existingPost 데이터베이스에 저장
//        // 수정 성공 여부가 없음
//        Notice modifyNotice = noticeRepository.save(updatePost);
//
//        NoticeDTO modifyDTO = new NoticeDTO();
//        if(modifyNotice != null){
//            modifyDTO.setId(modifyNotice.getId());
//            modifyDTO.setNoticeContent(modifyNotice.getNoticeContent());
//            modifyDTO.setNoticeTitle(modifyNotice.getNoticeTitle());
//        }
//
//        return modifyDTO;
 //   }
@Transactional
public NoticeDTO updateNotice(int id, NoticeDTO noticeDTO) {
    Notice notice = noticeRepository.findById(id).orElse(null);
    if (notice == null) {
        return null;
    }
    notice.setNoticeTitle(noticeDTO.getNoticeTitle());
    notice.setNoticeContent(noticeDTO.getNoticeContent());
    Notice updatedNotice = noticeRepository.save(notice);
    NoticeDTO updatedDTO = new NoticeDTO();
    updatedDTO.setId(updatedNotice.getId());
    updatedDTO.setNoticeTitle(updatedNotice.getNoticeTitle());
    updatedDTO.setNoticeContent(updatedNotice.getNoticeContent());
    return updatedDTO;
}

}





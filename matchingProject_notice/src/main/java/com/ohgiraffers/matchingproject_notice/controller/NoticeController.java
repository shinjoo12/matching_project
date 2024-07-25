package com.ohgiraffers.matchingproject_notice.controller;

import com.ohgiraffers.matchingproject_notice.model.dto.NoticeDTO;
import com.ohgiraffers.matchingproject_notice.service.NoticeService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Controller
public class NoticeController {

    private final NoticeService noticeService;
    private NoticeDTO currentNotice;

    @Autowired
    public NoticeController(NoticeService noticeService){
        this.noticeService = noticeService;
    }

    //공지사항 목록 리스트 표시
    @GetMapping("/")
    public String Noticelist(){
        return "noticeList";
    }

    @GetMapping("/noticeRegister")
    public String RegistPost(){
        return "noticeRegister";
    }

    // 제목이나 내용이 비어있다면 등록 페이지로 다시 이동
    @PostMapping("/noticeRegister")
    public ModelAndView RegistPost(NoticeDTO noticeDTO, ModelAndView mv) {

        if (noticeDTO.getNoticeTitle() == null || noticeDTO.getNoticeTitle().equals("")) {
            mv.setViewName("redirect:/noticeRegister");
            return mv;
        }
        if (noticeDTO.getNoticeContent() == null || noticeDTO.getNoticeContent().equals("")) {
            mv.setViewName("redirect:/noticeRegister");
            return mv;
        }
        // 게시물을 저장하고 결과를 반환
        int result = noticeService.regist(noticeDTO);

        // 결과가 0 이하인 경우 오류 페이지로 이동
        if (result <= 0) {
            mv.setViewName("page");
        } else {
            // 성공적으로 저장된 경우 currentNotice 업데이트하고 "/noticeList/noticeDetail"로 리다이렉트
            currentNotice = noticeDTO;
            mv.setViewName("redirect:/noticeList/noticeDetail");
        }

        return mv;
    }



}

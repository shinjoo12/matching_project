package com.ohgiraffers.matchingproject_notice.controller;

import com.ohgiraffers.matchingproject_notice.model.dto.NoticeDTO;
import com.ohgiraffers.matchingproject_notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class NoticeController {

    private final NoticeService noticeService;

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
        int result = noticeService.regist(noticeDTO);
        return mv;
    }

    @GetMapping("/noticeDetail")
    public String noticeDetail(Model model){
        return "noticeDetail";
    }

}

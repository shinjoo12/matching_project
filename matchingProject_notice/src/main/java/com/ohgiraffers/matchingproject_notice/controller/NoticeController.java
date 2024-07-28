package com.ohgiraffers.matchingproject_notice.controller;

import com.ohgiraffers.matchingproject_notice.model.dto.NoticeDTO;
import com.ohgiraffers.matchingproject_notice.model.entitiy.Notice;
import com.ohgiraffers.matchingproject_notice.service.NoticeService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Controller
public class NoticeController {

    private final NoticeService noticeService;
    private NoticeDTO currentNotice;
    private List<NoticeDTO> noticeList = new ArrayList<>();

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    //공지사항 목록 리스트 표시
    @GetMapping("/")
    public String Noticelist() {
        return "noticeList";
    }

    @GetMapping("/noticeRegister")
    public String RegistPost() {
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
        // 게시물을 저장하고 목록에 추가
        int result = noticeService.regist(noticeDTO);
        if (result > 0) {
            noticeList.add(noticeDTO);
            mv.setViewName("redirect:/noticeList/noticeDetail/" + noticeDTO.getId());
        } else {
            mv.setViewName("page");
        }
        return mv;
    }


    @GetMapping("/noticeList/noticeDetail/{id}")
    public String noticeDetail(@PathVariable("id") int id, Model model) {
        for (NoticeDTO notice : noticeList) {
            if (notice.getId() == id) {
                model.addAttribute("notice", notice);
                return "noticeDetail";
            }
            model.addAttribute("page", "게시물을 찾을 수 없습니다");

        }
        return "noticeDetail";
    }


//    //[수정]
//    // 게시글 수정 페이지 요청 처리
//    @GetMapping("/noticeList1/noticeDetail/editnotice/{id}")
//    //요청 URL 경로의 일부로 전달된 id 값을 메서드의 파라미터 id에 바인딩 // Model model 컨트롤러에서 뷰로 데이터를 전달하는 데 사용
//    public String editnotice(@PathVariable("id") int id, Model model) {
//        // id에 해당하는 게시물을 BoardService를 통해 가져옴
//        //boardService 객체의 getPostById 메서드를 호출하여 id에 해당하는 게시물을 조회하고, 이를 BoardDTO 객체로 반환한다.
//        NoticeDTO edits = noticeService.getPostById(id);
//        // 존재하지 않는 게시글이면 게시판 목록으로 리다이렉트
//
//        if (edits == null) {
//            return "redirect:/noticeList1";
//        }
//
//        //boardDTO 뷰에서 참조할 키값
//        model.addAttribute("noticeDTO", edits); // boardDTO가 실제로는 Post 객체를 의미하는 것으로 가정한다.
//        return "editnotice"; // editpost.html로 반환하여 게시글 수정페이지를 표시
//    }
//
//    // 게시글 수정 요청 처리
//    @PostMapping("/noticeList1/noticeDetail/editnotice/{id}")
//    public String updateNotice(@PathVariable("id") int id, @ModelAttribute NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) {
//        NoticeDTO modifyNotice = noticeService.updateNotice(id, noticeDTO);
//        if (modifyNotice == null) {
//            redirectAttributes.addFlashAttribute("error", "게시글 수정 실패");
//        } else {
//            redirectAttributes.addFlashAttribute("success", "게시글 수정 완료");
//        }
//        return "redirect:/noticeList1/noticeDetail/" + id;
//    }

    // 수정 페이지 요청 처리
    @GetMapping("/noticeDetail/editnotice/{id}")
    public String editnotice(@PathVariable("id") int id, Model model) {
        NoticeDTO noticeDTO = noticeService.getPostById(id);
        if (noticeDTO == null) {
            return "redirect:/noticeList1";
        }
        model.addAttribute("noticeDTO", noticeDTO);
        return "editnotice";
    }

    // 수정 처리
    @PostMapping("/noticeDetail/editnotice/{id}")
    public String updateNotice(@PathVariable("id") int id, NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) {
        NoticeDTO updatedNotice = noticeService.updateNotice(id, noticeDTO);
        if (updatedNotice == null) {
            redirectAttributes.addFlashAttribute("error", "게시글 수정 실패");
            return "redirect:/editnotice/" + id;
        } else {
            redirectAttributes.addFlashAttribute("success", "게시글 수정 완료");
            return "redirect:/noticeList1";
        }
    }

    ////POSt요청 -> 서버에 데이터를 보내고 서버에 있는 리소스를 생성하거나 업데이트하는데 사용
////사용자가 수정 내용 입력하고 저장 버튼 누르면 경로 변수 id와 수정된 게시글 데이터가 함께 전달!!
//@PostMapping("/noticeList1/noticeDetail/editnotice/{id}")
////id는 경로변수, 수정할 게시글의 ID를 나타냄
////요청 전달된 데이터를 Post 객체로 바인딩하여 updatedPost 변수에 저장
//// 수정할 게시글(ID) 의 내용 넣기
////uRl에서 전달된 게시글 ID를 id 변수에 저장한다.  RedirectAttributes 는 일회성 메시지를 전달한느 데 사용
//public String updateNotice(@PathVariable("id") int id, @ModelAttribute NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) {
//    //  수정된 내용 게시물(id)을 업데이트
//    //id에 해당하는 게시글을  객체의 데이터로 업데이트한다.
//    NoticeDTO modifyNotice = noticeService.updateNotice(id, noticeDTO);
//
//    if (modifyNotice == null) {
//        redirectAttributes.addFlashAttribute("error", "게시글 수정 실패");
//    } else {
//        redirectAttributes.addFlashAttribute("success", "게시글 수정 완료");
//    }
//
//    // spring > 페이지의 이름으로 인식함.
//    //수정된 게시글의 ID를 가져와 URL에 추가한다
//    return "redirect:/noticeList1/" + modifyNotice.getId();
//}
    /* [전체 조회]*/
    @GetMapping("/noticeList1")
    public String getNoticeList(Model model) {
        // 게시물 목록을 BoardService를 통해 가져옴
        List<Notice> noticeList1 = noticeService.getAllPosts();

        // 게시물 목록이 비어있으면 noPost 속성을 true로 설정하여 처리
        if (noticeList1 == null || noticeList1.isEmpty()) {
            model.addAttribute("noPost", true);
        } else {
            //게시물 목록을 모델에 추가하여 postlist.html에 전달
            model.addAttribute("noticeList1", noticeList1);
        }
        // postlist.html을 반환하여 게시물 목록 페이지를 표시
        return "noticeList1";
    }
}




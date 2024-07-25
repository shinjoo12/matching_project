package com.ohgiraffers.matchingproject_notice.model.entitiy;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "noticepost")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "notice_no")
    private int no;

    @Column(name = "notice_title", unique = true, nullable = false)
    private String noticeTitle;

    @Column(name = "notice_content", nullable = false, length = 5000)
    private String noticeContent;

    @Column(name = "notice_createDate")
    private Date createDate;

    public Notice() {
    }

    public Notice(int id, int no, String noticeTitle, String noticeContent, Date createDate) {
        this.id = id;
        this.no = no;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", no=" + no +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", noticeContent='" + noticeContent + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}

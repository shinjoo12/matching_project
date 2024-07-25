package com.ohgiraffers.matchingproject_notice.repository;

import com.ohgiraffers.matchingproject_notice.model.entitiy.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {


}

package com.example.newspost.repository;

import com.example.newspost.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByTitle(String title);

    // 검색 기능 구현: 타이틀, 카테고리, 내용에 따른 필터링
    @Query("SELECT n FROM News n WHERE " +
            "( :title IS NULL OR n.title LIKE %:title%) AND " +
            "( :category IS NULL OR n.category = :category) AND " +
            "( :content IS NULL OR n.content LIKE %:content%)")
    List<News> searchNews(@Param("title") String title, @Param("category") String category, @Param("content") String content);



}

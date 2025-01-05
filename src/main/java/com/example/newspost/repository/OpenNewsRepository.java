package com.example.newspost.repository;

import com.example.newspost.entity.OpenNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenNewsRepository extends JpaRepository<OpenNews, Long> {
}

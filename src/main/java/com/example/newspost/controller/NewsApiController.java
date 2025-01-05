package com.example.newspost.controller;

import com.example.newspost.entity.News;
import com.example.newspost.repository.NewsRepository;
import com.example.newspost.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller  // 웹 페이지 렌더링을 처리하는 컨트롤러
@RequestMapping("/api/news")
public class NewsApiController {

    @Autowired
    private NewsRepository newsRepository;

    private final NewsService newsService;
    // 생성자
    public NewsApiController(NewsService newsService) {
        this.newsService = newsService;
    }


    // 뉴스 목록을 보여주는 페이지
    @GetMapping("/list")
    public String showNewsList(Model model) {
        List<News> newsList = newsService.getAllNews();
        model.addAttribute("newsList", newsList);
        return "newsList";
    }



}

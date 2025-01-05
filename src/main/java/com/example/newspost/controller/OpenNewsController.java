package com.example.newspost.controller;

import com.example.newspost.entity.OpenNews;
import com.example.newspost.service.OpenNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OpenNewsController {

    @Autowired
    private OpenNewsService openNewsService;

    @GetMapping("/news/open")
    public String fetchOpenNews(Model model) {
        List<OpenNews> newsList = openNewsService.fetchAndSaveOpenNews();
        model.addAttribute("newsList", newsList);
        return "openNewsList"; // HTML 파일 이름
    }
}

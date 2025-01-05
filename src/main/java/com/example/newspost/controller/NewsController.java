package com.example.newspost.controller;

import com.example.newspost.entity.News;
import com.example.newspost.repository.NewsRepository;
import com.example.newspost.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Transactional
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsService newsService;

    @GetMapping("/create")
    public String createNewsForm() {
        return "createNews"; // createNews.html 반환
    }


    @PostMapping("/create")
    public String createNews(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("category") String category,
            @RequestParam("file") MultipartFile file,
            Model model
    ) {
        try {
            // 파일 저장
            if (!file.isEmpty()) {
                String uploadDir = "C:/path/to/uploads";
                File destinationFile = new File(uploadDir, file.getOriginalFilename());
                file.transferTo(destinationFile); // 파일 저장
            }

            // 뉴스 저장  (파일 경로를 DB에 저장)
            newsService.saveNews(title, content, category, "/uploads/" + file.getOriginalFilename());
            model.addAttribute("message", "News created successfully");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "File upload failed");
        }
        return "redirect:/news";
    }




    // 기존 엔드포인트
    @GetMapping
    public ResponseEntity<String> getNews() {
        return ResponseEntity.ok("News service is up and running!");
    }


    // 추가: 모든 뉴스 조회 엔드포인트
    @GetMapping("/all")
    public ResponseEntity<List<News>> getAllNews() {
        List<News> allNews = newsRepository.findAll();
        return ResponseEntity.ok(allNews);
    }


    // 추가: 특정 id 조회
    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable("id") Long id) {
        return newsRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


//수정
    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(@PathVariable("id") Long id, @RequestBody News news) {
        return newsRepository.findById(id)
                .map(existingNews -> {
                    existingNews.setCategory(news.getCategory());
                    existingNews.setContent(news.getContent());
                    existingNews.setTitle(news.getTitle());
                    existingNews.setCreatedAt(news.getCreatedAt());  // (필요 시 업데이트할 다른 필드 추가)
                    newsRepository.save(existingNews);
                    return ResponseEntity.ok(existingNews);
                })
                .orElse(ResponseEntity.notFound().build());  // 뉴스가 없으면 404 반환
    }
//삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable("id") Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            newsRepository.delete(news.get());  // 뉴스 삭제
            return ResponseEntity.noContent().build();  // 204 No Content 응답
        } else {
            return ResponseEntity.notFound().build();  // 뉴스가 없으면 404 반환
        }
    }
     // 동적 검색 엔드포인트
    @GetMapping("/search")
    public ResponseEntity<List<News>> searchNews( @RequestParam(name = "title", required = false) String title,
                                                  @RequestParam(name = "category", required = false) String category,
                                                  @RequestParam(name = "content", required = false) String content) {
        List<News> newsList = newsService.searchNews(title, category, content);

        return newsList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(newsList);
    }








}

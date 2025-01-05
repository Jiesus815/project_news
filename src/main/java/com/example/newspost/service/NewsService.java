package com.example.newspost.service;

import com.example.newspost.entity.News;
import com.example.newspost.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

   // private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }


    public List<News> getAllNews() {
        return newsRepository.findAll(); // 모든 뉴스를 데이터베이스에서 가져옴
    }


    // 검색 기능을 위한 서비스 메서드
    public List<News> searchNews(String title, String category, String content) {
        // 레포지토리에서 정의된 searchNews 메서드를 호출
        return newsRepository.searchNews(title, category, content);
    }

    // 파일 저장 처리
    public String saveFile(MultipartFile file) {
        String uploadDir = "src/main/resources/static/uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리 생성
        }

        try {
            String originalFileName = file.getOriginalFilename();
            String cleanFileName = UUID.randomUUID().toString() + "_" + originalFileName;

            File destinationFile = new File(uploadDir + cleanFileName);
            file.transferTo(destinationFile); // 파일 저장

            System.out.println("File saved to: " + destinationFile.getAbsolutePath());
            return cleanFileName; // 브라우저에서 접근 가능한 경로 반환
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed.", e);
        }
    }


    public void saveNews(String title, String content, String category, String filePath) {
        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setCategory(category);
        news.setFilePath(filePath);
        newsRepository.save(news);
    }
}




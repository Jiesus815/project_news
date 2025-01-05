package com.example.newspost.service;

import com.example.newspost.entity.OpenNews;
import com.example.newspost.repository.OpenNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OpenNewsService {

    @Autowired
    private OpenNewsRepository openNewsRepository;

    private final String API_KEY = "f46fa26c47f94f57a7fbef41b10a5b36";
    private final String API_URL = "https://newsapi.org/v2/top-headlines";

    public List<OpenNews> fetchAndSaveOpenNews() {
        RestTemplate restTemplate = new RestTemplate();

        // API URL 생성
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("country", "us")
                .queryParam("apiKey", API_KEY)
                .toUriString();

        // API 호출
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        List<OpenNews> newsList = new ArrayList<>();
        if (response != null && response.containsKey("articles")) {
            List<Map<String, Object>> articles = (List<Map<String, Object>>) response.get("articles");

            for (Map<String, Object> article : articles) {
                OpenNews news = new OpenNews();
                news.setTitle((String) article.get("title"));
                news.setDescription((String) article.get("description"));
                news.setAuthor((String) article.get("author"));
                news.setPublishedDate(java.time.LocalDate.now()); // Publish date parsing 필요
                news.setSourceUrl((String) ((Map<String, Object>) article.get("source")).get("url"));
                newsList.add(news);
            }

            openNewsRepository.saveAll(newsList); // 데이터베이스 저장
        }
        return newsList;
    }
}

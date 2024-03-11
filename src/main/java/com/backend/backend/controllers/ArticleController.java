package com.backend.backend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.payloads.ArticleDTO;
import com.backend.backend.services.ArticleService;
import com.backend.backend.utils.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createArticle(@Valid @RequestBody ArticleDTO articleDto) {
        ApiResponse response = this.articleService.createArticle(articleDto);
        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putArticle(@PathVariable Integer id, @Valid @RequestBody ArticleDTO articleDto) {
        ApiResponse response = this.articleService.updateArticle(articleDto, id);
        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteArticle(@Valid @PathVariable("id") Integer id) {
        ApiResponse response = this.articleService.deleteArticle(id);
        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @GetMapping("/getByID/{id}")
    public ResponseEntity<ApiResponse> getArticleByID(@Valid @PathVariable("id") Integer id) {
        ApiResponse response = this.articleService.getArticleByID(id);
        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllArticle(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size) {
        ApiResponse response = this.articleService.getAllArticlesPaginated(page, size);
        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

}

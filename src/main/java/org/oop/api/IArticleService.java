package org.oop.api;

import org.oop.model.Article;

import java.util.List;

public interface IArticleService {
    Article createArticle(String title, String content);
    Article getArticleById(long id);
    List<Article> getArticlesByTitle(String title);
    List<Article> getAllArticles();
    boolean updateArticle(long id, String title, String content);
    boolean deleteArticle(long id);
}


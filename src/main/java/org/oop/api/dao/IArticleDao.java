package org.oop.api.dao;

import org.oop.model.Article;

import java.util.List;

public interface IArticleDao {
    Article createArticle(Article article);
    Article getArticleById(int id);
    List<Article> getArticlesByTitle(String title);
    List<Article> getAllArticles();
    boolean updateArticle(Article article);
    boolean deleteArticle(int id);
}
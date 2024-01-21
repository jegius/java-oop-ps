package org.oop.service;

import org.oop.api.IArticleService;
import org.oop.api.dao.IArticleDao;
import org.oop.di.Injector;
import org.oop.model.Article;

import java.util.List;

public class ArticleService implements IArticleService {
    private final IArticleDao articleDao;

    public ArticleService() {
        this.articleDao = Injector.getInstance().getService(IArticleDao.class);
    }

    @Override
    public Article createArticle(String title, String content) {
        Article newArticle = new Article(0, title, content);
        return articleDao.createArticle(newArticle);
    }

    @Override
    public Article getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    @Override
    public List<Article> getArticlesByTitle(String title) {
        return articleDao.getArticlesByTitle(title);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleDao.getAllArticles();
    }

    @Override
    public boolean updateArticle(int id, String title, String content) {
        Article article = articleDao.getArticleById(id);
        if (article == null) {
            return false;
        }
        article = new Article(id, title, content);
        return articleDao.updateArticle(article);
    }

    @Override
    public boolean deleteArticle(int id) {
        return articleDao.deleteArticle(id);
    }
}

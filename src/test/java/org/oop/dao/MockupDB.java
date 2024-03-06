package org.oop.dao;

import org.oop.api.*;
import org.oop.api.dao.IArticleDao;
import org.oop.api.dao.ICommentDao;
import org.oop.di.Injector;
import org.oop.model.Article;
import org.oop.model.Comment;
import org.oop.model.Role;
import org.oop.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MockupDB extends Dao implements IDatabaseService {

    private final List<Long> articleIDs = new ArrayList<>();

    public MockupDB() {
    }

    @Override
    public void initializeDatabase() {

        IDatabaseService databaseService = Injector.getInstance().getService(IDatabaseService.class);
        databaseService.initializeDatabase();

        // Создать две статьи.
        IUserService userService = Injector.getInstance().getService(IUserService.class);
        IAuthService authService = Injector.getInstance().getService(IAuthService.class);
        userService.register("Envek", "$2aJgnkkN9e6hrYGO.", "Envek@example.com", Role.USER);
        userService.register("Outspector", "$2a$10$ELqr66Uv", "Outspector@example.com", Role.USER);
        authService.login("Envek", "$2aJgnkkN9e6hrYGO.");
        User author = userService.getUserByUsername("Envek");
        IArticleDao articleDao = Injector.getInstance().getService(IArticleDao.class);
        Article article1 = articleDao.createArticle(new Article(0L, "Pull request'ы на GitHub или Как мне внести изменения в чужой проект",
                "https://habr.com/ru/articles/125999/", (long) author.getId()));
        articleIDs.add(article1.getId());
        Article article2 = articleDao.createArticle(new Article(0L, "Молчание Ruby-эксепшенов: транзакционный Rails/PostgreSQL триллер",
                "https://habr.com/ru/articles/418147/", (long) author.getId()));
        articleIDs.add(article2.getId());
        authService.logout();
    }

    public void cleanDatabase() {

        String cleaningQueries = "DROP TABLE IF EXISTS users, articles, comments;";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(cleaningQueries);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Comment> getCommentsFromOutspector() {
        List<Comment> comments = new ArrayList<>();
        IUserService userService = Injector.getInstance().getService(IUserService.class);
        User author = userService.getUserByUsername("Outspector");
        // Добавить комментарий к первой статье.
        comments.add(new Comment(0L, articleIDs.get(0), (long) author.getId(),
                "Кстати, pull request — это не единственный способ обратить внимание разработчика на сделанные тобой изменения."));
        // Добавить комментарий ко второй статье.
        comments.add(new Comment(0L, articleIDs.get(1), (long) author.getId(),
                "Если делать rescue Exception, то можно словить ещё более интересные спецэффекты."));
        return comments;
    }
}

package org.oop.dao;

import org.oop.api.IArticleService;
import org.oop.api.IConfigService;
import org.oop.api.IDatabaseService;
import org.oop.api.IUserService;
import org.oop.api.dao.IArticleDao;
import org.oop.api.dao.ICommentDao;
import org.oop.di.Injector;
import org.oop.model.Article;
import org.oop.model.Comment;
import org.oop.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MockupDB extends Dao implements IDatabaseService {

    private List<Long> articleIDs = new ArrayList<>();

    public MockupDB() {
    }

    @Override
    public void initializeDatabase() {

        String[] initializationQueries = new String[]{
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id SERIAL PRIMARY KEY," +
                        "username VARCHAR(50) NOT NULL," +
                        "password VARCHAR(255) NOT NULL," +
                        "email VARCHAR(100) NOT NULL," +
                        "role VARCHAR(50) NOT NULL DEFAULT 'USER')",

                "CREATE TABLE IF NOT EXISTS articles (" +
                        "id SERIAL PRIMARY KEY," +
                        "title VARCHAR(255) NOT NULL," +
                        "content TEXT NOT NULL," +
                        "author_id INTEGER," +
                        "FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE);",

                "CREATE TABLE IF NOT EXISTS comments (" +
                        "id SERIAL PRIMARY KEY," +
                        "article_id INTEGER," +
                        "user_id INTEGER," +
                        "comment_text TEXT NOT NULL," +
                        "FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE," +
                        "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE);" +

                        "INSERT INTO users (username, password, email, role) " +
                        "SELECT 'admin', '$2a$10$ELqr66UvJgnkkN9e6hrYGO.brljJ//Y2MTpMpVfhdmgEUB0wmS2cC', 'admin@example.com', 'ADMIN' " +
                        "WHERE NOT EXISTS (" +
                        "SELECT * FROM users WHERE username = 'admin'" +
                        ");",

                "INSERT INTO users (username, password, email, role) " +
                        "SELECT 'Envek', '$2a$10$ELqr66Uv', 'Envek@example.com', 'USER' " +
                        "WHERE NOT EXISTS (" +
                        "SELECT * FROM users WHERE username = 'Envek'" +
                        ");",

                "INSERT INTO users (username, password, email, role) " +
                        "SELECT 'Outspector', '$2aJgnkkN9e6hrYGO.', 'Outspector@example.com', 'USER' " +
                        "WHERE NOT EXISTS (" +
                        "SELECT * FROM users WHERE username = 'Outspector'" +
                        ");",
        };

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            for (String sql : initializationQueries) {
                stmt.execute(sql);
            }

            // Создать две статьи.
            IUserService userService = Injector.getInstance().getService(IUserService.class);
            User author = userService.getUserByUsername("Envek");
            IArticleDao articleDao = Injector.getInstance().getService(IArticleDao.class);
            Article article1 = articleDao.createArticle(new Article(0L, "Pull request'ы на GitHub или Как мне внести изменения в чужой проект",
                    "https://habr.com/ru/articles/125999/", (long) author.getId()));
            articleIDs.add(article1.getId());
            Article article2 = articleDao.createArticle(new Article(0L, "Молчание Ruby-эксепшенов: транзакционный Rails/PostgreSQL триллер",
                    "https://habr.com/ru/articles/418147/", (long) author.getId()));
            articleIDs.add(article2.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cleanDatabase() {

        String cleaningQueries = "DROP TABLE users, articles, comments;";

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

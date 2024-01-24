package org.oop.service;

import org.oop.api.IConfigService;
import org.oop.api.IDatabaseService;
import org.oop.di.Injector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService implements IDatabaseService {
    private final IConfigService configService;

    public DatabaseService() {
        this.configService = Injector.getInstance().getService(IConfigService.class);
    }
    @Override
    public void initializeDatabase() {
        {
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
                            ");"
            };
            String url = configService.getProperty("database.url");
            String user = configService.getProperty("database.user");
            String password = configService.getProperty("database.password");

            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement()) {
                for (String sql : initializationQueries) {
                    stmt.execute(sql);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

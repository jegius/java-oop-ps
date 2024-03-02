package org.oop.dao;

import org.oop.api.dao.ICommentDao;
import org.oop.model.Article;
import org.oop.model.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao extends Dao implements ICommentDao {
    @Override
    public Comment createComment(Comment comment) {
        String query = "INSERT INTO comments (article_id, user_id, comment_text) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, comment.getArticleId());
            preparedStatement.setLong(2, comment.getAuthorId());
            preparedStatement.setString(3, comment.getContent());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating article failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    comment.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating article failed, no ID obtained.");
                }
            }

            return comment;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Comment> getAllComment() {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT id, article_id, user_id, comment_text FROM comments";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                comments.add(new Comment(
                        resultSet.getLong("id"),
                        resultSet.getLong("article_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getString("comment_text")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public Comment getCommentById(long id) {
        String query = "SELECT id, article_id, user_id, comment_text FROM comments WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Comment(
                            resultSet.getLong("id"),
                            resultSet.getLong("article_id"),
                            resultSet.getLong("user_id"),
                            resultSet.getString("comment_text")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Comment> getAllCommentsToArticle(Long articleId) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT id, article_id, user_id, comment_text FROM comments WHERE article_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, articleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    comments.add(new Comment(
                            resultSet.getLong("id"),
                            resultSet.getLong("article_id"),
                            resultSet.getLong("user_id"),
                            resultSet.getString("comment_text")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public List<Comment> getAllCommentsByAuthor(Long userId) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT id, article_id, user_id, comment_text FROM comments WHERE user_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    comments.add(new Comment(
                            resultSet.getLong("id"),
                            resultSet.getLong("article_id"),
                            resultSet.getLong("user_id"),
                            resultSet.getString("comment_text")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public boolean updateComment(Comment comment) {
        String query = "UPDATE comments SET article_id = ?, user_id = ?, comment_text = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, comment.getArticleId());
            preparedStatement.setLong(2, comment.getAuthorId());
            preparedStatement.setString(3, comment.getContent());
            preparedStatement.setLong(4, comment.getId());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteComment(long id) {
        String query = "DELETE FROM comments WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

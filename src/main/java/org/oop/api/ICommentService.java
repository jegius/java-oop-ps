package org.oop.api;

import org.oop.model.Comment;

import java.util.List;

public interface ICommentService {

    Comment createComment(Long articleId, String content);
    List<Comment> getAllComments();
    Comment getCommentById(long id);
    List<Comment> getAllCommentsByAuthor(Long userId);
    List<Comment> getAllCommentsToArticle(Long articleId);
    boolean updateComment(long id, String content);
    boolean deleteComment(long id);
}

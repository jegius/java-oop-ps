package org.oop.service;

import org.oop.api.IAuthService;
import org.oop.api.ICommentService;
import org.oop.api.dao.ICommentDao;
import org.oop.di.Injector;
import org.oop.model.Comment;

import java.util.List;

public class CommentService implements ICommentService {

    private final ICommentDao commentDao;
    private final IAuthService authService;

    public CommentService() {
        this.commentDao = Injector.getInstance().getService(ICommentDao.class);
        this.authService = Injector.getInstance().getService(IAuthService.class);
    }

    @Override
    public Comment createComment(Long articleId, String content) {
        long authorId = authService.getCurrentUserId();
        Comment newComment = new Comment(0L, articleId, authorId == -1 ? 0L : authorId, content);
        return commentDao.createComment(newComment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentDao.getAllComment();
    }

    @Override
    public Comment getCommentById(long id) {
        return commentDao.getCommentById(id);
    }

    @Override
    public List<Comment> getAllCommentsByAuthor(Long userId) {
        return commentDao.getAllCommentsByAuthor(userId);
    }

    @Override
    public List<Comment> getAllCommentsToArticle(Long articleId) {
        return commentDao.getAllCommentsToArticle(articleId);
    }

    @Override
    public boolean updateComment(long id, String content) {
        return commentDao.updateComment(commentDao.getCommentById(id));
    }

    @Override
    public boolean deleteComment(long id) {
        return commentDao.deleteComment(id);
    }
}

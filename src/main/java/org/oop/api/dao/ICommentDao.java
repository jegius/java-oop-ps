package org.oop.api.dao;

import org.oop.model.Comment;

import java.util.List;

public interface ICommentDao {

    /**
     * Добавляет в базу данных новый комментарий
     * @param comment добавляемый комментарий
     * @return добавленный в базу данных комментарий
     */
    Comment createComment(Comment comment);

    /**
     * Получить список всех комментариев
     * @return список комментариев
     */
    List<Comment> getAllComment();

    /**
     * Получить комментарий
     * @param id идентификатор
     * @return найденный комментарий или null, если комментария с таким идентификатором нет
     */
    Comment getCommentById(long id);

    /**
     * Найти все комментарии к статье
     * @param articleId идентификатор статьи
     * @return список комментариев к указанной статье
     */
    List<Comment> getAllCommentsToArticle(Long articleId);

    /**
     * Найти все комментарии автора
     * @param userId идентификатор
     * @return список комментариев указанного автора
     */
    List<Comment> getAllCommentsByAuthor(Long userId);

    /**
     * Обновить комментарий
     * @param comment обновляемый комментарий
     * @return истину, если операция выполнена успешно
     */
    boolean updateComment(Comment comment);

    /**
     * Удалить комментарий
     * @param id идентификатор
     * @return истину, если операция выполнена успешно
     */
    boolean deleteComment(long id);
}

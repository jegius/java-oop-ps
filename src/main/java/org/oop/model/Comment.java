package org.oop.model;

import java.util.Objects;

public class Comment {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Текст
     */
    private String content;

    /**
     * Автор.
     */
    private Long authorId;

    /**
     * Статья.
     */
    private Long articleId;

    public Comment() {
    }

    public Comment(Long id, Long articleId, Long authorId, String content) {
        this.id = id;
        this.content = content;
        this.authorId = authorId;
        this.articleId = articleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return Objects.equals(getId(), comment.getId()) && Objects.equals(getContent(), comment.getContent()) && Objects.equals(getAuthorId(), comment.getAuthorId()) && Objects.equals(getArticleId(), comment.getArticleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getAuthorId(), getArticleId());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                ", articleId=" + articleId +
                '}';
    }
}

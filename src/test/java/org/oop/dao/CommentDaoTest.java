package org.oop.dao;

import junit.framework.TestCase;
import org.oop.model.Comment;
import org.testng.annotations.Test;

import java.util.List;

public class CommentDaoTest extends TestCase {

    private final MockupDB mdb = new MockupDB();

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mdb.cleanDatabase();
        mdb.initializeDatabase();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        mdb.cleanDatabase();
    }

    @Test
    public void testCommentDao() {
        CommentDao commentDao = new CommentDao();
        mdb.getCommentsFromOutspector().stream().map(commentDao::createComment).forEach(TestCase::assertNotNull);
        System.out.println("Comments added");
        List<Comment> comments = commentDao.getAllComment();
        assertFalse(comments.isEmpty());
        System.out.println(comments);
        comments.clear();
        System.out.println("Comments received");

        Comment comment = commentDao.getCommentById(2);
        assertNotNull(comment);
        System.out.println("Comment with id " + comment.getId() + " received");
        comments.addAll(commentDao.getAllCommentsToArticle(1L));
        assertEquals(1, comments.size());
        comments.clear();
        System.out.println("Comments to the article with id 1 received");

        comments.addAll(commentDao.getAllCommentsByAuthor(3L));
        assertEquals(2, comments.size());
        comments.clear();
        System.out.println("Comments by author with ID 3 received");

        comments.addAll(commentDao.getAllCommentsByAuthor(1L));
        assertEquals(0, comments.size());
        comments.clear();
        System.out.println("Author with ID 1 did not give comments");

        comment = commentDao.getCommentById(2);
        comment.setContent("Comment with id 2 author with id 3 changed");
        assertTrue(commentDao.updateComment(comment));
        System.out.println("Comment with id 2 author with id 3 changed");

        comment = commentDao.getCommentById(2);
        comment.setAuthorId(2L);
        assertTrue(commentDao.updateComment(comment));
        System.out.println("The author of the comment with ID 2 has been changed");

        assertTrue(commentDao.deleteComment(2));
        System.out.println("Comment with id 2 deleted");
    }
}
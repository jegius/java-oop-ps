package org.oop.service;

import junit.framework.TestCase;
import org.oop.App;
import org.oop.api.ICommentService;
import org.oop.api.IConfigService;
import org.oop.api.dao.ICommentDao;
import org.oop.dao.CommentDao;
import org.oop.dao.MockupDB;
import org.oop.di.Injector;
import org.testng.annotations.Test;

import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CommentServiceTest extends TestCase {

    private final InputStream systemIn = System.in;
    private final MockupDB mockupDB = new MockupDB();

    @Override
    public void setUp() throws Exception {
        mockupDB.cleanDatabase();
        mockupDB.initializeDatabase();
        Injector.getInstance().registerService(ICommentService.class, CommentService::new);
        Injector.getInstance().registerService(ICommentDao.class, CommentDao::new);
    }

    @Override
    public void tearDown() throws Exception {
        System.setIn(systemIn);
        mockupDB.cleanDatabase();
    }

    private void providerInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    public void testCommentService() {
        providerInput("1\n" + //Авторизоваться
                "Outspector\n" + //Имя
                "$2a$10$ELqr66Uv\n" + //Пароль
                "3\n" + //Посмотреть все статьи
                "1\n" + //Статья
                "2\n" + //Прокмментировать статью
                "1\n" + //Статья
                "Кстати, pull request — это не единственный способ обратить внимание разработчика на сделанные тобой изменения.\n" +
                "1\n" + //Посмотреть все комментарии
                "3\n" + //Удалить существующий комментарий
                "1\n" + //Введите ID комментария для удаления
                "4\n5");
        App.getInstance().run();

//        CommentService service = new CommentService();
//        mdb.getCommentsFromOutspector().forEach(comment -> service.createComment(comment.getArticleId(), comment.getContent()));
        System.out.println("Comments added");
    }
}
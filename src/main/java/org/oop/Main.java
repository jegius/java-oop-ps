package org.oop;

import org.oop.api.ICommentService;
import org.oop.api.dao.ICommentDao;
import org.oop.dao.CommentDao;
import org.oop.di.Injector;
import org.oop.service.CommentService;

public class Main {
    public static void main(String[] args) {
        Injector.getInstance().registerService(ICommentService.class, CommentService::new);
        Injector.getInstance().registerService(ICommentDao.class, CommentDao::new);
        App.getInstance().run();
    }
}
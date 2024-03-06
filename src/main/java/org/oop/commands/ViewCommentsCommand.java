package org.oop.commands;

import org.oop.api.IAuthService;
import org.oop.api.ICommand;
import org.oop.api.ICommentService;
import org.oop.commands.menu.BaseCommand;
import org.oop.commands.menu.CommentMenu;
import org.oop.di.Injector;
import org.oop.model.Comment;

import java.util.List;

public class ViewCommentsCommand extends BaseCommand {

    private final ICommentService commentService;

    public ViewCommentsCommand() {
        this.commentService = Injector.getInstance().getService(ICommentService.class);
    }

    @Override
    public ICommand execute() {
        IAuthService service = Injector.getInstance().getService(IAuthService.class);
        List<Comment> comments = commentService.getAllCommentsByAuthor(service.getCurrentUserId());

        if (comments.isEmpty()) {
            ioService.printLine("Нет комментариев, доступных для просмотра.");
        } else {
            ioService.printComments(comments, Injector.getInstance().getService(ICommentService.class));
        }

        return new CommentMenu();
    }

    @Override
    public String getDescription() {
        return "Посмотреть все комментарии";
    }
}

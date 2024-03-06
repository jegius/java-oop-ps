package org.oop.commands;

import org.oop.api.ICommand;
import org.oop.api.ICommentService;
import org.oop.commands.menu.CommentMenu;
import org.oop.commands.menu.BaseCommand;
import org.oop.di.Injector;

public class DeleteCommentCommand extends BaseCommand {
    private final ICommentService commentService;

    public DeleteCommentCommand() {
        this.commentService = Injector.getInstance().getService(ICommentService.class);
    }

    @Override
    public ICommand execute() {
        long commentId = Long.parseLong(ioService.prompt("Введите ID комментария для удаления: "));

        boolean isDeleted = commentService.deleteComment(commentId);
        if (isDeleted) {
            ioService.printLine("Комментарий с ID " + commentId + " был удален.");
        } else {
            ioService.printLine("Комментарий с ID " + commentId + " не удалось найти или удалить.");
        }

        return new CommentMenu();
    }

    @Override
    public String getDescription() {
        return "Удалить существующий комментарий";
    }
}

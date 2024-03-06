package org.oop.commands;

import org.oop.api.ICommand;
import org.oop.api.ICommentService;
import org.oop.commands.menu.BaseCommand;
import org.oop.commands.menu.CommentMenu;
import org.oop.di.Injector;

public class CreateCommentCommand  extends BaseCommand {
    private final ICommentService commentService;

    public CreateCommentCommand() {
        this.commentService = Injector.getInstance().getService(ICommentService.class);
    }

    @Override
    public ICommand execute() {
        long articleId = Long.parseLong(ioService.prompt("Введите ID статьи для комментирования: "));
        String content = ioService.prompt("Введите комментарий: ");

        commentService.createComment(articleId, content);

        ioService.printLine("Комментарий успешно создан.");

        return new CommentMenu();
    }

    @Override
    public String getDescription() {
        return "Прокмментировать статью";
    }
}

package org.oop.commands;

import org.oop.api.IArticleService;
import org.oop.api.ICommand;
import org.oop.commands.menu.ArticleMenu;
import org.oop.commands.menu.BaseCommand;
import org.oop.di.Injector;

public class CreateArticleCommand extends BaseCommand {
    private final IArticleService articleService;

    public CreateArticleCommand() {
        this.articleService = Injector.getInstance().getService(IArticleService.class);
    }

    @Override
    public ICommand execute() {
        String title = ioService.prompt("Введите заголовок статьи: ");
        String content = ioService.prompt("Введите содержимое статьи: ");

        articleService.createArticle(title, content);

        ioService.printLine("Статья '" + title + "' была успешно создана.");

        return new ArticleMenu();
    }

    @Override
    public String getDescription() {
        return "Создать новую статью";
    }
}
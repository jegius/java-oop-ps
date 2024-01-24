package org.oop.commands;

import org.oop.api.IArticleService;
import org.oop.api.ICommand;
import org.oop.commands.menu.BaseCommand;
import org.oop.commands.menu.MainMenu;
import org.oop.di.Injector;
import org.oop.model.Article;

import java.util.List;

public class ViewAllArticlesCommand extends BaseCommand {

    private final IArticleService articleService;

    public ViewAllArticlesCommand() {
        this.articleService = Injector.getInstance().getService(IArticleService.class);
    }

    @Override
    public ICommand execute() {
        List<Article> articles = articleService.getAllArticles();

        if (articles.isEmpty()) {
            ioService.printLine("Нет доступных статей для просмотра.");
        } else {
            ioService.printArticles(articles, Injector.getInstance().getService(IArticleService.class));
        }

        return new MainMenu();
    }

    @Override
    public String getDescription() {
        return "Посмотреть все статьи";
    }
}

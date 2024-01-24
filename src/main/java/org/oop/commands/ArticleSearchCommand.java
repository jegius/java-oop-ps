package org.oop.commands;

import org.oop.api.IArticleService;
import org.oop.api.ICommand;
import org.oop.commands.menu.BaseCommand;
import org.oop.commands.menu.MainMenu;
import org.oop.di.Injector;
import org.oop.model.Article;

import java.util.List;

public class ArticleSearchCommand extends BaseCommand {
    private final IArticleService articleService;

    public ArticleSearchCommand() {
        this.articleService = Injector.getInstance().getService(IArticleService.class);
    }

    @Override
    public ICommand execute() {
        String searchQuery = ioService.prompt("Введите название статьи для поиска: ");

        List<Article> articles = articleService.getArticlesByTitle(searchQuery);

        if (articles.isEmpty()) {
            ioService.printLine("Статьи с указанным названием не найдены.");
        } else {
            ioService.printLine("Найденные статьи:");
            ioService.printArticles(articles, Injector.getInstance().getService(IArticleService.class));
        }

        return new MainMenu();
    }

    @Override
    public String getDescription() {
        return "Найти статью";
    }

}
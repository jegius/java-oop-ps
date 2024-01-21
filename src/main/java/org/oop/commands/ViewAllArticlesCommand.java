package org.oop.commands;

import org.oop.api.IArticleService;
import org.oop.api.ICommand;
import org.oop.api.IOService;
import org.oop.commands.menu.MainMenu;
import org.oop.di.Injector;
import org.oop.model.Article;

import java.util.List;

public class ViewAllArticlesCommand implements ICommand {

    private final IArticleService articleService;
    private final IOService ioService;

    public ViewAllArticlesCommand() {
        this.articleService = Injector.getInstance().getService(IArticleService.class);
        this.ioService = Injector.getInstance().getService(IOService.class);
    }

    @Override
    public ICommand execute() {
        List<Article> articles = articleService.getAllArticles();

        if (articles.isEmpty()) {
            ioService.printLine("Нет доступных статей для просмотра.");
        } else {
            for (Article article : articles) {
                ioService.printLine(article.getId() + ": " + article.getTitle());
            }
        }

        return new MainMenu();
    }

    @Override
    public String getDescription() {
        return "Посмотреть все статьи";
    }
}

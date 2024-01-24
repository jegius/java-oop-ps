package org.oop.commands.menu;

import org.oop.commands.*;
import org.oop.api.ICommand;


public class ArticleMenu extends BaseCommand {

    public ArticleMenu() {
        initializeMenu();
    }

    private void initializeMenu() {
        commandSuppliers.put(1, CreateArticleCommand::new);
        commandSuppliers.put(2, DeleteArticleCommand::new);
        commandSuppliers.put(3, MainMenu::new);
    }

    @Override
    public ICommand execute() {
        return selectMenu();
    }


    @Override
    public String getDescription() {
        return "Управление статьями";
    }
}

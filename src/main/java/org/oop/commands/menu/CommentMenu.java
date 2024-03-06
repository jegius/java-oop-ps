package org.oop.commands.menu;

import org.oop.api.ICommand;
import org.oop.commands.CreateCommentCommand;
import org.oop.commands.DeleteCommentCommand;
import org.oop.commands.ViewCommentsCommand;

public class CommentMenu extends BaseCommand {

    public CommentMenu() {
        initializeMenu();
    }

    private void initializeMenu() {
        commandSuppliers.put(1, ViewCommentsCommand::new);
        commandSuppliers.put(2, CreateCommentCommand::new);
        commandSuppliers.put(3, DeleteCommentCommand::new);
        commandSuppliers.put(4, MainMenu::new);
    }

    @Override
    public ICommand execute() {
        return selectMenu();
    }


    @Override
    public String getDescription() {
        return "Управление комментариями";
    }
}

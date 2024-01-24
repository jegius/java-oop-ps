package org.oop.commands.menu;

import org.oop.commands.*;
import org.oop.api.ICommand;



public class UserMenu extends BaseCommand {

    public UserMenu() {
        initializeMenu();
    }

    private void initializeMenu() {
        commandSuppliers.put(1, AddUserCommand::new);
        commandSuppliers.put(2, ChangePasswordCommand::new);
        commandSuppliers.put(3, DeleteUserCommand::new);
        commandSuppliers.put(4, ShowAllUsersCommand::new);
        commandSuppliers.put(5, UpdateUserCommand::new);
        commandSuppliers.put(6, MainMenu::new);
    }

    @Override
    public ICommand execute() {
        return selectMenu();
    }

    @Override
    public String getDescription() {
        return "Меню управления пользователями";
    }
}
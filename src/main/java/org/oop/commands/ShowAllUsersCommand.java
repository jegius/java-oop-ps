package org.oop.commands;

import org.oop.api.IUserService;
import org.oop.api.ICommand;
import org.oop.commands.menu.BaseCommand;
import org.oop.commands.menu.UserMenu;
import org.oop.di.Injector;
import org.oop.model.User;

import java.util.List;

public class ShowAllUsersCommand extends BaseCommand {
    private final IUserService userService;

    public ShowAllUsersCommand() {
        this.userService = Injector.getInstance().getService(IUserService.class);
    }

    @Override
    public ICommand execute() {
        ioService.printLine("Список пользователей:");
        ioService.printUserTableHeader();
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            ioService.printLine("| Список пользователей пуст.                                         |");
        } else {
            users.forEach(user -> ioService.printLine(user.toString()));
        }
        ioService.printLine("+------------+----------------------+--------------------------------+------------+");

        return new UserMenu();
    }

    @Override
    public String getDescription() {
        return "Показать всех пользователей";
    }
}
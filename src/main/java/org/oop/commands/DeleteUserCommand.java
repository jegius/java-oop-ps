package org.oop.commands;

import org.oop.api.IAuthService;
import org.oop.api.IOService;
import org.oop.api.IUserService;
import org.oop.api.ICommand;
import org.oop.commands.menu.UserMenu;
import org.oop.di.Injector;
import org.oop.model.User;

import java.util.Optional;

public class DeleteUserCommand extends BaseCommand {
    private final IUserService userService;
    private final IOService ioService;
    private final IAuthService authService;

    public DeleteUserCommand() {
        this.userService = Injector.getInstance().getService(IUserService.class);
        this.ioService = Injector.getInstance().getService(IOService.class);
        this.authService = Injector.getInstance().getService(IAuthService.class);
    }

    @Override
    public ICommand execute() {
        ioService.printLine("Удаление пользователя:");

        Optional<String> username = promptOrReturn("Введите имя пользователя для удаления:");
        if (username.isEmpty()) return new UserMenu();

        User user = userService.getUserByUsername(username.get());

        if (authService.isCurrentUser(user)) {
            ioService.printLine("Нельзя удалить текущего пользователя.");
            return new UserMenu();
        }

        if (user == null) {
            ioService.printLine("Пользователь с таким именем не найден.");
        } else {
            boolean isDeleted = userService.deleteUserById(user.getId());
            if (isDeleted) {
                ioService.printLine("Пользователь успешно удален.");
            } else {
                ioService.printLine("Не удалось удалить пользователя.");
            }
        }

        return new UserMenu();
    }

    private Optional<String> promptOrReturn(String message) {
        String input = ioService.prompt(message);
        if ("back".equalsIgnoreCase(input)) {
            return Optional.empty();
        }
        return Optional.of(input);
    }

    @Override
    public String getDescription() {
        return "Удалить пользователя";
    }
}

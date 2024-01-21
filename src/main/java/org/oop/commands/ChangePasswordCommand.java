package org.oop.commands;

import org.oop.api.IOService;
import org.oop.api.IUserService;
import org.oop.commands.BaseCommand;
import org.oop.api.ICommand;
import org.oop.commands.menu.UserMenu;
import org.oop.di.Injector;
import org.oop.model.User;

import java.util.Optional;

public class ChangePasswordCommand extends BaseCommand {
    private final IUserService userService;
    private final IOService ioService;

    public ChangePasswordCommand() {
        this.userService = Injector.getInstance().getService(IUserService.class);
        this.ioService = Injector.getInstance().getService(IOService.class);
    }

    @Override
    public ICommand execute() {
        ioService.printLine("Смена пароля пользователя:");

        Optional<String> username = promptOrReturn("Введите имя пользователя:");
        if (username.isEmpty()) return new UserMenu();

        User user = userService.getUserByUsername(username.get());
        if (user == null) {
            ioService.printLine("Пользователь не найден.");
            return new UserMenu();
        }

        Optional<String> oldPassword = promptOrReturn("Введите старый пароль:");
        if (oldPassword.isEmpty()) return new UserMenu();

        Optional<String> newPassword = promptOrReturn("Введите новый пароль:");
        if (newPassword.isEmpty()) return new UserMenu();

        boolean passwordChanged = userService.changePassword(user, oldPassword.get(), newPassword.get());
        if (passwordChanged) {
            ioService.printLine("Пароль успешно изменен.");
        } else {
            ioService.printLine("Смена пароля не удалась. Возможно, неверно указан старый пароль.");
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
        return "Смена пароля пользователя";
    }
}
package org.oop.commands;

import org.oop.api.IOService;
import org.oop.api.IUserService;
import org.oop.api.ICommand;
import org.oop.commands.menu.UserMenu;
import org.oop.di.Injector;
import org.oop.model.Role;

import java.util.Optional;

public class AddUserCommand extends BaseCommand {
    private final IUserService userService;
    public AddUserCommand() {
        this.userService = Injector.getInstance().getService(IUserService.class);
    }

    @Override
    public ICommand execute() {
        ioService.printLine("Создание нового пользователя:");

        Optional<String> username = promptOrReturn("Введите имя пользователя:");
        if (username.isEmpty()) return new UserMenu();

        Optional<String> password = promptOrReturn("Введите пароль:");
        if (password.isEmpty()) return new UserMenu();

        Optional<String> email = promptOrReturn("Введите адрес электронной почты:");
        if (email.isEmpty()) return new UserMenu();

        Optional<Role> role = choiceRoleOrReturn("Выберите роль (ADMIN/USER):");
        if (role.isEmpty()) return new UserMenu();

        boolean isRegistered = userService.register(username.get(), password.get(), email.get(), role.get());

        if (isRegistered) {
            ioService.printLine("Пользователь успешно зарегистрирован.");
        } else {
            ioService.printLine("Не удалось зарегистрировать пользователя. Возможно, имя пользователя или адрес электронной почты уже используется.");
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

    private Optional<Role> choiceRoleOrReturn(String message) {
        String input = ioService.prompt(message);
        if ("back".equalsIgnoreCase(input)) {
            return Optional.empty();
        }
        try {
            Role role = Role.valueOf(input.toUpperCase());
            return Optional.of(role);
        } catch (IllegalArgumentException e) {
            ioService.printLine("Неверно указана роль. Повторите попытку.");
            return choiceRoleOrReturn(message);
        }
    }

    @Override
    public String getDescription() {
        return "Добавить нового пользователя";
    }
}
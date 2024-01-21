package org.oop.commands;

import org.oop.api.IOService;
import org.oop.api.IUserService;
import org.oop.api.ICommand;
import org.oop.commands.menu.MainMenu;
import org.oop.di.Injector;
import org.oop.model.Role;

public class RegisterCommand extends BaseCommand {
    private final IUserService userService;
    private final IOService consoleIOService;

    public RegisterCommand() {
        this.userService = Injector.getInstance().getService(IUserService.class);
        this.consoleIOService = Injector.getInstance().getService(IOService.class);
    }

    @Override
    public ICommand execute() {
        consoleIOService.printLine("Регистрация нового пользователя");

        String username = consoleIOService.prompt("Введите имя пользователя:");
        String password = consoleIOService.prompt("Введите пароль:");
        String email = consoleIOService.prompt("Введите email адрес:");

        try {
            boolean isRegistered = userService.register(username, password, email, Role.ADMIN);
            if (isRegistered) {
                consoleIOService.printLine("Регистрация прошла успешно. Теперь вы можете войти в систему.");
            } else {
                consoleIOService.printLine("Не удалось зарегистрироваться. Пользователь с таким именем уже существует.");
            }
        } catch (Exception e) {
            consoleIOService.printLine("Ошибка при регистрации: " + e.getMessage());
        }

        return new MainMenu();
    }

    @Override
    public String getDescription() {
        return "Регистрация пользователя";
    }
}

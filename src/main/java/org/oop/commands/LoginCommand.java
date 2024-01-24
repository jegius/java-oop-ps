package org.oop.commands;

import org.oop.api.IAuthService;
import org.oop.api.ICommand;
import org.oop.commands.menu.BaseCommand;
import org.oop.commands.menu.MainMenu;
import org.oop.di.Injector;

public class LoginCommand extends BaseCommand {
    private final IAuthService authService;

    public LoginCommand() {
        this.authService = Injector.getInstance().getService(IAuthService.class);
    }

    @Override
    public ICommand execute() {
        ioService.printLine(getDescription());

        String username = ioService.prompt("Введите имя пользователя: ");
        String password = ioService.prompt("Введите пароль: ");

        try {
            if (authService.login(username, password)) {
                ioService.printLine("Авторизация выполнена успешно.");
            } else {
                ioService.printLine("Логин или пароль неверны. Попробуйте снова.");
            }
            return new MainMenu();
        } catch (Exception e) {
            ioService.printLine("Ошибка при авторизации: " + e.getMessage());
            return new MainMenu();
        }
    }

    @Override
    public String getDescription() {
        return "Авторизоваться";
    }
}

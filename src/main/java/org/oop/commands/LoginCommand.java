package org.oop.commands;

import org.oop.api.IAuthService;
import org.oop.api.IOService;
import org.oop.api.ICommand;
import org.oop.commands.menu.MainMenu;
import org.oop.di.Injector;

public class LoginCommand extends BaseCommand {
    private final IAuthService authService;
    private final IOService consoleIOService;

    public LoginCommand() {
        this.authService = Injector.getInstance().getService(IAuthService.class);
        this.consoleIOService = Injector.getInstance().getService(IOService.class);
    }

    @Override
    public ICommand execute() {
        consoleIOService.printLine(getDescription());

        String username = consoleIOService.prompt("Введите имя пользователя: ");
        String password = consoleIOService.prompt("Введите пароль: ");

        try {
            if (authService.login(username, password)) {
                consoleIOService.printLine("Авторизация выполнена успешно.");
            } else {
                consoleIOService.printLine("Логин или пароль неверны. Попробуйте снова.");
            }
            return new MainMenu();
        } catch (Exception e) {
            consoleIOService.printLine("Ошибка при авторизации: " + e.getMessage());
            return new MainMenu();
        }
    }

    @Override
    public String getDescription() {
        return "Авторизоваться";
    }
}

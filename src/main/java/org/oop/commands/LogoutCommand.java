package org.oop.commands;

import org.oop.api.IAuthService;
import org.oop.api.ICommand;
import org.oop.commands.menu.BaseCommand;
import org.oop.commands.menu.MainMenu;
import org.oop.di.Injector;

public class LogoutCommand extends BaseCommand {
    private final IAuthService authService;

    public LogoutCommand() {
        this.authService = Injector.getInstance().getService(IAuthService.class);
    }

    @Override
    public ICommand execute() {
        ioService.printLine(getDescription());

        String choice = ioService.prompt("Уверены, что хотите выйти? (y/n):");

        try {
            if (choice.equalsIgnoreCase("y")) {
                authService.logout();
                ioService.printLine("Вы вышли из аккаунта.");
            }

            return new MainMenu();
        } catch (Exception e) {
            ioService.printLine("Ошибка при авторизации: " + e.getMessage());
            return new MainMenu();
        }
    }

    @Override
    public String getDescription() {
        return "Разлогиниться";
    }
}

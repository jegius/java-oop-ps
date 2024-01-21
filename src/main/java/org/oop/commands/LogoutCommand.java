package org.oop.commands;

import org.oop.api.IAuthService;
import org.oop.api.ICommand;
import org.oop.api.IOService;
import org.oop.commands.menu.MainMenu;
import org.oop.di.Injector;

public class LogoutCommand extends BaseCommand {
    private final IAuthService authService;
    private final IOService consoleIOService;

    public LogoutCommand() {
        this.authService = Injector.getInstance().getService(IAuthService.class);
        this.consoleIOService = Injector.getInstance().getService(IOService.class);
    }

    @Override
    public ICommand execute() {
        consoleIOService.printLine(getDescription());

        String choice = consoleIOService.prompt("Уверены, что хотите выйти? (y/n):");

        try {
            if (choice.equalsIgnoreCase("y")) {
                authService.logout();
                consoleIOService.printLine("Вы вышли из аккаунта.");
            }

            return new MainMenu();
        } catch (Exception e) {
            consoleIOService.printLine("Ошибка при авторизации: " + e.getMessage());
            return new MainMenu();
        }
    }

    @Override
    public String getDescription() {
        return "Разлогиниться";
    }
}

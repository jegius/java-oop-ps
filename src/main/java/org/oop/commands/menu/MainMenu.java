package org.oop.commands.menu;

import org.oop.api.IAuthService;
import org.oop.api.ICommand;
import org.oop.commands.*;
import org.oop.di.Injector;

import java.util.concurrent.atomic.AtomicInteger;

public class MainMenu extends BaseCommand {
    private final IAuthService authService;

    public MainMenu() {
        this.authService = Injector.getInstance().getService(IAuthService.class);
        initializeMenu();
    }

    private void initializeMenu() {
        final AtomicInteger menuItemNumber = new AtomicInteger(1);

        if (authService.isUserLoggedIn()) {
            commandSuppliers.put(menuItemNumber.getAndIncrement(), LogoutCommand::new);
            commandSuppliers.put(menuItemNumber.getAndIncrement(), ArticleMenu::new);
        } else {
            commandSuppliers.put(menuItemNumber.getAndIncrement(), LoginCommand::new);
            commandSuppliers.put(menuItemNumber.getAndIncrement(), RegisterCommand::new);
        }

        if (authService.isAdministrator()) {
            commandSuppliers.put(menuItemNumber.getAndIncrement(), UserMenu::new);
        }

        commandSuppliers.put(menuItemNumber.getAndIncrement(), ViewAllArticlesCommand::new);
        commandSuppliers.put(menuItemNumber.getAndIncrement(), ArticleSearchCommand::new);
        commandSuppliers.put(menuItemNumber.getAndIncrement(), () -> null);
    }

    @Override
    public ICommand execute() {
        return selectMenu();
    }

    @Override
    public String getDescription() {
        return "Главное меню";
    }
}



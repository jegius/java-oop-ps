package org.oop.commands.menu;

import org.oop.api.IOService;
import org.oop.commands.*;
import org.oop.api.ICommand;
import org.oop.di.Injector;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;


public class UserMenu extends BaseCommand {
    private final Map<Integer, Supplier<ICommand>> commandSuppliers = new LinkedHashMap<>();
    private final IOService ioService;

    public UserMenu() {
        this.ioService = Injector.getInstance().getService(IOService.class);
        initializeMenu();
    }

    private void initializeMenu() {
        commandSuppliers.put(1, AddUserCommand::new);
        commandSuppliers.put(2, ChangePasswordCommand::new);
        commandSuppliers.put(3, DeleteUserCommand::new);
        commandSuppliers.put(4, ShowAllUsersCommand::new);
        commandSuppliers.put(5, UpdateUserCommand::new);
        commandSuppliers.put(6, MainMenu::new);
    }

    @Override
    public ICommand execute() {
        Map<Integer, String> menuItems = new LinkedHashMap<>();
        commandSuppliers.forEach((key, value) -> menuItems.put(key, value.get().getDescription()));

        ioService.printMenu(getDescription(), menuItems);
        int selectedMenuItem = ioService.promptForMenuSelection(menuItems, "Выберите опцию и нажмите Enter:");

        if (commandSuppliers.containsKey(selectedMenuItem)) {
            return commandSuppliers.get(selectedMenuItem).get().execute();
        } else {
            ioService.printLine("Неверный номер опции. Пожалуйста, попробуйте снова.");
            return this;
        }
    }

    @Override
    public String getDescription() {
        return "Меню управления пользователями";
    }
}
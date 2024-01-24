package org.oop.commands.menu;

import org.oop.api.ICommand;
import org.oop.api.IOService;
import org.oop.di.Injector;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class BaseCommand implements ICommand {
    protected final Map<Integer, Supplier<ICommand>> commandSuppliers = new LinkedHashMap<>();
    protected IOService ioService;
    protected BaseCommand() {
        this.ioService = Injector.getInstance().getService(IOService.class);
    }

    protected Map<Integer, String> getMenuItems() {
        return commandSuppliers.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Optional.ofNullable(e.getValue().get())
                                .map(ICommand::getDescription)
                                .orElse("Выйти из приложения"),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    protected ICommand selectMenu() {
        Map<Integer, String> menuItems = getMenuItems();
        ioService.printMenu(getDescription(), menuItems);
        int selectedMenuItem = ioService.promptForMenuSelection(menuItems, "Выберите опцию и нажмите Enter:");

        return commandSuppliers.getOrDefault(selectedMenuItem, () -> {
            ioService.printLine("Неверный номер опции");
            return this;
        }).get();
    }
}

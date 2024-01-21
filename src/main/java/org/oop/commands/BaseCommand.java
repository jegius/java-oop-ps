package org.oop.commands;

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
    protected IOService consoleIOService;
    protected BaseCommand() {
        this.consoleIOService = Injector.getInstance().getService(IOService.class);
    }

    protected Map<Integer, String> getMainMenuItems() {
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
}

package org.oop.commands;

import org.oop.api.IUserService;
import org.oop.api.ICommand;
import org.oop.commands.menu.BaseCommand;
import org.oop.commands.menu.UserMenu;
import org.oop.di.Injector;
import org.oop.model.Role;
import org.oop.model.User;

import java.util.List;

public class UpdateUserCommand extends BaseCommand {
    private final IUserService userService;

    public UpdateUserCommand() {
        this.userService = Injector.getInstance().getService(IUserService.class);
    }

    @Override
    public ICommand execute() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            ioService.printLine("Список пользователей пуст.");
            return new UserMenu();
        }

        ioService.printUserTableHeader();
        users.forEach(user -> ioService.printLine(user.toString()));
        ioService.printLine("+------------+----------------------+--------------------------------+------------+");

        try {
            int userId = Integer.parseInt(ioService.prompt("Введите ID пользователя для изменения: "));

            User userToUpdate = users.stream()
                    .filter(u -> u.getId() == userId)
                    .findFirst()
                    .orElse(null);

            if (userToUpdate == null) {
                ioService.printLine("Пользователь с таким ID не найден.");
                return this;
            }

            String newUsername = ioService.prompt("Введите новое имя пользователя: (" + userToUpdate.getUsername() + ")");
            String newEmail = ioService.prompt("Введите новый email: (" + userToUpdate.getEmail() + ")");
            Role newRole = Role.valueOf(ioService.prompt("Введите новую роль (ADMIN/USER): (" + userToUpdate.getRole() + ")"));

            userToUpdate.setUsername(newUsername);
            userToUpdate.setEmail(newEmail);
            userToUpdate.setRole(newRole);

            boolean success = userService.updateUser(userToUpdate);
            if (success) {
                ioService.printLine("Пользователь успешно обновлен.");
            } else {
                ioService.printLine("Ошибка при обновлении пользователя.");
            }
        } catch (NumberFormatException e) {
            ioService.printLine("Неверный формат ID пользователя.");
        }


        return new UserMenu();
    }

    @Override
    public String getDescription() {
        return "Обновить информацию о пользователе";
    }
}

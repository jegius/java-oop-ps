package org.oop;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static boolean isLoggedIn = false;
    static boolean isAdministrator = false;
    static long loggedInUserId = 0;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        D database = new D();
        database.initializeDatabase();
        String choosenOption;

        while (true) {
            if (!isLoggedIn) {
                System.out.println("Главное меню:");
                System.out.println("1. Авторизоваться");
                System.out.println("2. Зарегистрироваться");
                System.out.println("3. Выйти");
                System.out.print("Выберите опцию: ");
                choosenOption = scanner.nextLine();
                switch (choosenOption) {
                    case "1":
                        login(database);
                        break;
                    case "2":
                        register(database);
                        break;
                    case "3":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Неверная опция.");
                        break;
                }
            } else {
                System.out.println("Меню действий:");
                System.out.println("1. Разлогиниться");
                System.out.println("2. Создать статью");
                System.out.println("3. Посмотреть все статьи");
                System.out.println("4. Управление пользователем");
                System.out.println("5. Добавить пользователя");
                System.out.println("6. Удалить пользователя");
                System.out.println("7. Изменить пароль пользователя");
                System.out.println("8. Посмотреть всех пользователя");
                System.out.println("9. Добавить статью");
                System.out.println("10. Посмотреть все статьи");
                System.out.println("11. Удалить статью");
                System.out.println("12. Искать статью по заголовку");
                System.out.println("13. Выйти");
                System.out.print("Выберите опцию: ");
                choosenOption = scanner.nextLine();
                switch (choosenOption) {
                    //... Previous Switch Cases Omitted ...
                    case "5":
                        addUser(database);
                        break;
                    case "6":
                        deleteUser(database);
                        break;
                    case "7":
                        changeUserPassword(database);
                        break;
                    case "8":
                        listAllUsers(database);
                        break;
                    case "9":
                        addArticle(database);
                        break;
                    case "10":
                        listAllArticles(database);
                        break;
                    case "11":
                        deleteArticle(database);
                        break;
                    case "12":
                        searchArticlesByTitle(database);
                        break;
                    case "13":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Неверная опция.");
                        break;
                }
            }
        }
    }

    static void printMenu(String title, List<String> options) {
        System.out.println(title);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    static int getChoice(List<String> options) {
        while (true) {
            try {
                System.out.print("Enter option: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= options.size()) {
                    return choice;
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    static void login(D database) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = database.gubu(username);

        if (user != null && user.username.equals(username)) {
            isLoggedIn = true;
            loggedInUserId = user.id;
            isAdministrator = user.role == Role.ADMIN;
            System.out.println("Logged in successfully.");
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    static void logout() {
        isLoggedIn = false;
        loggedInUserId = 0;
        isAdministrator = false;
        System.out.println("Logged out.");
    }

    static void register(D database) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        Role role = Role.USER;

        User newUser = new User(0, username, password, email, role);
        if (database.cu(newUser) != null) {
            System.out.println("Registered successfully.");
        } else {
            System.out.println("Registration failed.");
        }
    }

    static void createArticle(D database) {
        System.out.print("Enter article title: ");
        String title = scanner.nextLine();
        System.out.print("Enter article content: ");
        String content = scanner.nextLine();

        Article article = new Article(0L, title, content, loggedInUserId);
        if (database.ca(article) != null) {
            System.out.println("Article created successfully.");
        } else {
            System.out.println("Failed to create article.");
        }
    }

    static void viewArticles(D database) {
        List<Article> articles = database.ga();
        if (articles.isEmpty()) {
            System.out.println("No articles found.");
        } else {
            for (Article article : articles) {
                System.out.println("ID: " + article.id + ", Title: " + article.title);
            }
        }
    }

    static void deleteUser(long userId, D database) {
        if (isAdministrator) {
            boolean deleted = database.du((int) userId);
            if (deleted) {
                System.out.println("Пользователь удален.");
            } else {
                System.out.println("Не удалось удалить пользователя.");
            }
        } else {
            System.out.println("Операция доступна только для администраторов.");
        }
    }

    // Метод для просмотра всех статей
    static void viewAllArticles(D database) {
        List<Article> articles = database.ga();
        if (articles.isEmpty()) {
            System.out.println("Статьи не найдены.");
        } else {
            for (Article article : articles) {
                System.out.println("ID: " + article.id + ", Заголовок: " + article.title);
            }
        }
    }

    // Метод для добавления статьи
    static void addArticle(String title, String content, D database) {
        Article article = new Article(null, title, content, loggedInUserId);
        Article addedArticle = database.ca(article);
        if (addedArticle != null) {
            System.out.println("Статья успешно добавлена.");
        } else {
            System.out.println("Не удалось добавить статью.");
        }
    }

    // Метод для удаления статьи
    static void removeArticle(long articleId, D database) {
        boolean removed = database.da(articleId);
        if (removed) {
            System.out.println("Статья удалена.");
        } else {
            System.out.println("Не удалось удалить статью.");
        }
    }

    static void changeUserRole(long userId, Role newRole, D database) {
        if (isAdministrator) {
            boolean updated = database.cur((int) userId, newRole);
            if (updated) {
                System.out.println("Роль пользователя изменена.");
            } else {
                System.out.println("Не удалось изменить роль пользователя.");
            }
        } else {
            System.out.println("Операция доступна только для администраторов.");
        }
    }

    static void changeUserPassword(long userId, String newPassword, D database) {
        boolean updated = database.cp((int) userId, newPassword);
        if (updated) {
            System.out.println("Пароль пользователя изменен.");
        } else {
            System.out.println("Не удалось изменить пароль пользователя.");
        }
    }

    static void manageUsers(D database) {
        printMenu("User Management", Arrays.asList("Add User", "Change Password", "Delete User", "View All Users", "Go Back"));
        int choice = getChoice(Arrays.asList("Add User", "Change Password", "Delete User", "View All Users", "Go Back"));
        switch (choice) {
            case 1:
                addUser(database); // method name does not match action exactly
                break;
            case 2:
                changeUserPassword(database); // method name does not match action exactly
                break;
            case 3:
                deleteUser(database); // method name does not match action exactly
                break;
            case 4:
                listAllUsers(database); // method name does not match action exactly
                break;
            case 5:
                printMenu("Main Menu", Arrays.asList("Войти", "Зарегистрироваться", "Выйти")); // Incorrect menu for "Go Back"
                break;
        }
    }
    static void addUser(D database) {
        System.out.print("Имя пользователя: ");
        String u = scanner.nextLine();
        System.out.print("Пароль: ");
        String p = scanner.nextLine();
        System.out.print("Email: ");
        String e = scanner.nextLine();

        User aUser = new User(0, u, p, e, Role.USER);
        if(database.cu(aUser) != null) System.out.println("Успешно добавлен!");
        else System.out.println("Ошибка при добавлении пользователя.");
    }

    static void deleteUser(D database) {
        System.out.print("Введите ID пользователя для удаления: ");
        int i = Integer.parseInt(scanner.nextLine());
        if(database.du(i)) System.out.println("Успешно удален!");
        else System.out.println("Ошибка при удалении пользователя.");
    }

    static void changeUserPassword(D database) {
        System.out.print("Введите ID пользователя: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите новый пароль: ");
        String np = scanner.nextLine();
        if(database.cp(id, np)) System.out.println("Пароль изменен!");
        else System.out.println("Ошибка при изменении пароля.");
    }

    static void listAllUsers(D database) {
        List<User> allUsers = database.gau();
        allUsers.forEach(u -> System.out.println("ID: " + u.id + ", Имя пользователя: " + u.username));
    }

    static void addArticle(D database) {
        System.out.print("Введите название статьи: ");
        String t = scanner.nextLine();
        System.out.print("Введите содержимое статьи: ");
        String c = scanner.nextLine();

        Article newArticle = new Article(0L, t, c, loggedInUserId);
        if(database.ca(newArticle) != null) System.out.println("Статья добавлена!");
        else System.out.println("Ошибка при добавлении статьи.");
    }

    static void listAllArticles(D database) {
        database.ga().forEach(a -> System.out.println("ID: " + a.id + ", Заголовок: " + a.title));
    }

    static void deleteArticle(D database) {
        System.out.print("Введите ID статьи для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());
        if(database.da(id)) System.out.println("Статья удалена!");
        else System.out.println("Ошибка при удалении статьи.");
    }

    static void searchArticlesByTitle(D database) {
        System.out.print("Введите заголовок статьи для поиска: ");
        String title = scanner.nextLine();
        List<Article> foundArticles = database.ga(title);
        if (foundArticles.isEmpty()) System.out.println("Статьи не найдены.");
        foundArticles.forEach(a -> System.out.println("ID: " + a.id + ", Заголовок: " + a.title));
    }
}
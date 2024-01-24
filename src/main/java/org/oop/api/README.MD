# Руководство по интерфейсам API

Данный файл содержит описание набора интерфейсов API, используемых в Java-пакете `org.oop.api`. Каждый интерфейс предоставляет основные функции в системе, такие как работы с статьями и пользователями, аутентификация и авторизация, ввод-вывод данных и управление конфигурацией.

## IArticleService

### Методы
- `Article createArticle(String title, String content)` - метод создает и возвращает новую статью с заданными заголовком и содержанием.
- `Article getArticleById(long id)` - метод возвращает статью по её уникальному идентификатору.
- `List<Article> getArticlesByTitle(String title)` - метод возвращает список статей, которые в заголовке содержат заданную строку.
- `List<Article> getAllArticles()` - метод возвращает список всех статей.
- `boolean updateArticle(long id, String title, String content)` - метод обновляет заголовок и содержание статьи по идентификатору.
- `boolean deleteArticle(long id)` - метод удаляет статью по её уникальному идентификатору.

## IAuthService

### Методы
- `boolean register(String username, String password, String email)` - метод регистрирует нового пользователя в системе.
- `boolean login(String username, String password)` - метод авторизует пользователя в системе.
- `long getCurrentUserId()` - метод возвращает идентификатор текущего авторизованного пользователя.
- `boolean logout()` - метод выполняет выход пользователя из системы.
- `boolean isUserLoggedIn()` - метод проверяет, авторизован ли пользователь в системе.
- `boolean isCurrentUser(User user)` - метод проверяет, является ли переданный пользователь текущим авторизованным пользователем.
- `boolean isAdministrator()` - метод проверяет, имеет ли текущий авторизованный пользователь права администратора.

## ICommand

### Методы
- `ICommand execute()` - метод, исполняющий команду.
- `String getDescription()` - метод, возвращающий описание команды.

## IConfigService

### Методы
- `String getProperty(String propertyName)` - метод возвращает значение свойства конфигурации по его имени.

## IDatabaseService

### Методы
- `void initializeDatabase()` - метод инициализирует базу данных.

## IOService

### Методы
- `String readLine()` - метод считывает строку с консоли.
- `void printLine(String line)` - метод печатает строку в консоль.
- `void close()` - метод закрывает поток ввода/вывода.
- `void printUserTableHeader()` - метод печатает заголовок таблицы пользователей.
- `void printArticles(List<Article> articles, IArticleService articleService)` - метод выводит список статей в консоль.
- `void printMenu(String title, Map<Integer, String> items)` - метод печатает меню с заданным заголовком и пунктами.
- `void printPromt(String promptMessage)` - метод печатает приглашение ввода.
- `String prompt(String message)` - метод запрашивает у пользователя ввод и возвращает введённую строку.
- `int promptForMenuSelection(Map<Integer, String> menuItems, String promptMessage)` - метод запрашивает у пользователя выбор пункта меню и возвращает номер выбранного пункта.

## IUserService

### Методы
- `boolean changePassword(User user, String oldPassword, String newPassword)` - метод изменяет пароль пользователя.
- `boolean register(String username, String password, String email, Role role)` - метод регистрирует пользователя с указанием роли.
- `User getUserByUsername(String username)` - метод возвращает пользователя по его логину.
- `List<User> getAllUsers()` - метод возвращает список всех пользователей.
- `boolean deleteUserById(int userId)` - метод удаляет пользователя по идентификатору.
- `boolean updateUser(User user)` - метод обновляет данные пользователя.
- `boolean changeUserRole(int userId, Role newRole)` - метод изменяет роль пользователя.

## Инструкции по применению
Для использования интерфейсов необходимо создать их реализации в соответствии с бизнес-логикой приложения. Реализация должна учитывать всех требования к хранению, обработке и безопасности данных.
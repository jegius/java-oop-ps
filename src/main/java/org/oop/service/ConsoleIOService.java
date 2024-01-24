package org.oop.service;

import org.oop.api.IArticleService;
import org.oop.api.IAuthService;
import org.oop.api.IOService;
import org.oop.di.Injector;
import org.oop.model.Article;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleIOService implements IOService {
    private final Scanner scanner;

    public ConsoleIOService() {

        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void printLine(String line) {
        System.out.println(line);
    }

    @Override
    public void close() {
        scanner.close();
    }

    public void printMenu(String title, Map<Integer, String> items) {
        printLine(getTableBorder());
        printLine("| " + title);
        printLine(getTableBorder());
        for (Map.Entry<Integer, String> item : items.entrySet()) {
            printLine("| " + item.getKey() + ". " + item.getValue());
        }
        printLine(getTableBorder());
    }

    public String prompt(String message) {
        printLine(message);
        return readLine();
    }

    public void printUserTableHeader() {
        printLine("+------------+----------------------+--------------------------------+------------+");
        printLine("| ID         | Username             | Email                          | Role       |");
        printLine("+------------+----------------------+--------------------------------+------------+");
    }

    @Override
    public void printArticles(List<Article> articles, IArticleService articleService) {
        for (Article article : articles) {
            printLine(article.getId() + ": " + article.getTitle());
        }

        try {
            long articleId = Long.parseLong(prompt("Введите ID статьи, которую вы хотите прочитать: "));

            Article selectedArticle = articleService.getArticleById(articleId);

            if (selectedArticle != null) {
                printLine(selectedArticle.toString());
            } else {
                printLine("Статьи с ID " + articleId + " не существует.");
            }
        } catch (NumberFormatException e) {
            printLine("Некорректный ввод ID статьи. Пожалуйста, введите числовой ID.");
        }
    }

    public int promptForMenuSelection(Map<Integer, String> menuItems, String promptMessage) {
        int selection = -1;
        while (!menuItems.containsKey(selection)) {
            printLine(promptMessage);
            try {
                selection = Integer.parseInt(readLine());
            } catch (NumberFormatException e) {
                printLine("Неверный ввод, попробуйте еще раз.");
            }
        }
        return selection;
    }

    @Override
    public void printPromt(String promptMessage) {
        printLine(promptMessage != null ? promptMessage : "Введите команду:");
    }

    private String getTableBorder() {
        return "+------------------------------------------------+";
    }

}

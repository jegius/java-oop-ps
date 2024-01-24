package org.oop.api;

import org.oop.model.Article;

import java.util.List;
import java.util.Map;

public interface IOService {
    String readLine();
    void printLine(String line);
    void close();
    void printUserTableHeader();

    void printArticles(List<Article> articles,  IArticleService articleService);

    void printMenu(String title, Map<Integer, String> items);
    void printPromt(String promptMessage);

    String prompt(String message);

    int promptForMenuSelection(Map<Integer, String> menuItems, String promptMessage);
}

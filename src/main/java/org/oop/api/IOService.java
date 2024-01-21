package org.oop.api;

import java.util.Map;

public interface IOService {
    String readLine();
    void printLine(String line);
    void close();
    void printUserTableHeader();

    void printMenu(String title, Map<Integer, String> items);
    void printPromt(String promptMessage);

    String prompt(String message);

    int promptForMenuSelection(Map<Integer, String> menuItems, String promptMessage);
}

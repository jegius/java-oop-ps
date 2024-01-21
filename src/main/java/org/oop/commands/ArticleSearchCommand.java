package org.oop.commands;

import org.oop.api.ICommand;

public class ArticleSearchCommand extends BaseCommand {
    @Override
    public ICommand execute() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Найти статью";
    }
}

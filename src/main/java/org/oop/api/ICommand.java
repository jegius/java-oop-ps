package org.oop.api;

public interface ICommand {
    ICommand execute();

    String getDescription();
}

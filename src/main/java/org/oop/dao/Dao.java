package org.oop.dao;

import org.oop.api.IConfigService;
import org.oop.di.Injector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Dao {
    protected final IConfigService configService;
    Dao() {
        this.configService = Injector.getInstance().getService(IConfigService.class);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                configService.getProperty("database.url"),
                configService.getProperty("database.user"),
                configService.getProperty("database.password")
        );
    }


}

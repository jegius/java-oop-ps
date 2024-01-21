package org.oop.di;

import org.oop.api.*;
import org.oop.api.dao.IArticleDao;
import org.oop.api.dao.IUserDao;
import org.oop.dao.*;
import org.oop.service.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Injector {
    private static volatile Injector instance;

    public static Injector getInstance() {
        if (instance == null) {
            synchronized (Injector.class) {
                if (instance == null) {
                    instance = new Injector();
                }
            }
        }
        return instance;
    }

    private final Map<Class<?>, Supplier<?>> serviceFactories = new HashMap<>();
    private final Map<Class<?>, Object> serviceCache = new HashMap<>();

    private Injector() {
        registerService(IAuthService.class, AuthService::new);
        registerService(IUserService.class, UserService::new);
        registerService(IOService.class, ConsoleIOService::new);
        registerService(IConfigService.class, ConfigService::new);
        registerService(IDatabaseService.class, DatabaseService::new);
        registerService(IArticleService.class, ArticleService::new);

        registerService(IUserDao.class, UserDao::new);
        registerService(IArticleDao.class, ArticleDao::new);
    }

    public <T> void registerService(Class<T> serviceType, Supplier<? extends T> factory) {
        serviceFactories.put(serviceType, factory);
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceType) {
        if (serviceCache.containsKey(serviceType)) {
            return (T) serviceCache.get(serviceType);
        } else {
            Supplier<?> factory = serviceFactories.get(serviceType);
            if (factory == null) {
                throw new RuntimeException("No factory registered for service: " + serviceType.getName());
            }

            T service = (T) factory.get();
            serviceCache.put(serviceType, service);

            return service;
        }


    }
}
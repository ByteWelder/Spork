package io.github.sporklibrary.logging;

import io.github.sporklibrary.logging.implementations.DefaultLoggerFactory;

public final class LoggerFactory {

    private LoggerFactory() {
    }

    public interface Interface {
        Logger createLogger(String name);
    }

    private static LoggerFactory.Interface loggerFactoryInterface = new DefaultLoggerFactory();

    public static Logger getLogger(Class<?> classObject) {
        return loggerFactoryInterface.createLogger(classObject.getName());
    }

    public static Logger getLogger(String name) {
        return loggerFactoryInterface.createLogger(name);
    }
}

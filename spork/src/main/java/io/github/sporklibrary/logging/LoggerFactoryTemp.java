package io.github.sporklibrary.logging;

import io.github.sporklibrary.logging.implementations.DefaultLoggerFactory;

public class LoggerFactoryTemp {

    public interface Interface {
        Logger createLogger(String name);
    }

    private static LoggerFactoryTemp.Interface loggerFactoryInterface = new DefaultLoggerFactory();

    public static Logger getLogger(Class<?> classObject) {
        return loggerFactoryInterface.createLogger(classObject.getName());
    }

    public static Logger getLogger(String name) {
        return loggerFactoryInterface.createLogger(name);
    }
}

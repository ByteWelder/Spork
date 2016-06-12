package io.github.sporklibrary.logging.implementations;

import io.github.sporklibrary.logging.Logger;
import io.github.sporklibrary.logging.LoggerFactory;

public class DefaultLoggerFactory implements LoggerFactory.Interface {

    @Override
    public Logger createLogger(String name) {
        return new DefaultLogger(name);
    }
}

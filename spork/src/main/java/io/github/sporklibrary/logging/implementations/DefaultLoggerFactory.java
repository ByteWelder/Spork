package io.github.sporklibrary.logging.implementations;

import io.github.sporklibrary.logging.Logger;
import io.github.sporklibrary.logging.LoggerFactoryTemp;

public class DefaultLoggerFactory implements LoggerFactoryTemp.Interface {

    @Override
    public Logger createLogger(String name) {
        return new DefaultLogger(name);
    }
}

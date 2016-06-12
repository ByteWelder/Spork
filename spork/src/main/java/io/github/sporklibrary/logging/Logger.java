package io.github.sporklibrary.logging;

public interface Logger {

    String getName();

    void trace(String format);
    void trace(String format, Throwable caught);
    void trace(String format, Object... arguments);
    boolean isTraceEnabled();

    void debug(String format);
    void debug(String format, Throwable caught);
    void debug(String format, Object... arguments);
    boolean isDebugEnabled();

    void info(String format);
    void info(String format, Throwable caught);
    void info(String format, Object... arguments);
    boolean isInfoEnabled();

    void warn(String format);
    void warn(String format, Throwable caught);
    void warn(String format, Object... arguments);
    boolean isWarnEnabled();

    void error(String format);
    void error(String format, Throwable caught);
    void error(String format, Object... arguments);
    boolean isErrorEnabled();
}

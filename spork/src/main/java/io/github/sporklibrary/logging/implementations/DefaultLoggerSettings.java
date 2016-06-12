package io.github.sporklibrary.logging.implementations;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class DefaultLoggerSettings {
    private static int logLevel = DefaultLogger.Level.INFO;
    private static PrintStream regularPrintStream = System.out;
    private static PrintStream errorPrintStream = System.err;
    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

    private DefaultLoggerSettings() {
    }

    public static int getLogLevel() {
        return logLevel;
    }

    public static void setLogLevel(int logLevel) {
        if (logLevel < DefaultLogger.Level.TRACE || logLevel > DefaultLogger.Level.ERROR) {
            throw new RuntimeException("invalid log level");
        }

        DefaultLoggerSettings.logLevel = logLevel;
    }

    public static void setRegularPrintStream(PrintStream printStream) {
        DefaultLoggerSettings.regularPrintStream = printStream;
    }

    public static void setErrorPrintStream(PrintStream printStream) {
        DefaultLoggerSettings.errorPrintStream = printStream;
    }

    public static DateFormat getDateFormat() {
        return dateFormat;
    }

    public static void setDateFormat(DateFormat dateFormat) {
        DefaultLoggerSettings.dateFormat = dateFormat;
    }

    public static PrintStream getPrintStream(int logLevel) {
        if (logLevel == DefaultLogger.Level.ERROR) {
            return errorPrintStream;
        } else {
            return regularPrintStream;
        }
    }
}

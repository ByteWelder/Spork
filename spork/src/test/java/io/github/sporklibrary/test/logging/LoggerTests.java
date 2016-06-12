package io.github.sporklibrary.test.logging;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.github.sporklibrary.logging.Logger;
import io.github.sporklibrary.logging.LoggerFactory;
import io.github.sporklibrary.logging.implementations.DefaultLogger;
import io.github.sporklibrary.logging.implementations.DefaultLoggerSettings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoggerTests {
    private LogOutputStream regularOutputStream;
    private LogOutputStream errorOutputStream;

    // Custom OutputStream to be used as a debugging tool when wrapped within a PrintStream for DefaultLoggerSettings
    private static class LogOutputStream extends OutputStream {
        private List<String> lines = new ArrayList<>();
        private StringBuilder lineBuilder = new StringBuilder();

        @Override
        public void write(int b) throws IOException {
            if (b != '\n') {
                lineBuilder.append((char)b);
            } else {
                lines.add(lineBuilder.toString());
                lineBuilder = new StringBuilder();
            }
        }

        List<String> getLines() {
            return lines;
        }

        String getLastLine() {
            return lines.get(lines.size() - 1);
        }

        void reset() {
            lines.clear();
            lineBuilder = new StringBuilder();
        }
    }

    @Before
    public void setup() {
        this.regularOutputStream = new LogOutputStream();
        this.errorOutputStream = new LogOutputStream();

        DefaultLoggerSettings.setRegularPrintStream(new PrintStream(regularOutputStream));
        DefaultLoggerSettings.setErrorPrintStream(new PrintStream(errorOutputStream));
    }

    @After
    public void tearDown() {
        DefaultLoggerSettings.setDefault();
    }

    @Test
    public void testLevels() {

        Logger testLogger = LoggerFactory.getLogger("testLevels");

        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.DEBUG);
        assertFalse(testLogger.isTraceEnabled());
        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.TRACE);
        assertTrue(testLogger.isTraceEnabled());

        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.INFO);
        assertFalse(testLogger.isDebugEnabled());
        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.DEBUG);
        assertTrue(testLogger.isDebugEnabled());

        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.WARN);
        assertFalse(testLogger.isInfoEnabled());
        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.INFO);
        assertTrue(testLogger.isInfoEnabled());

        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.ERROR);
        assertFalse(testLogger.isWarnEnabled());
        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.WARN);
        assertTrue(testLogger.isWarnEnabled());

        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.WARN);
        assertTrue(testLogger.isErrorEnabled());
        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.ERROR);
        assertTrue(testLogger.isErrorEnabled());
    }

    @Test
    public void testLevelFiltering() {
        resetOutputStreams();

        Logger testLogger = LoggerFactory.getLogger("testLevelFiltering");

        // save level to revert later
        int logLevel = DefaultLoggerSettings.getLogLevel();

        // minimal level set to INFO and output DEBUG level text
        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.INFO);
        testLogger.debug("test");

        assertEquals(0, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());

        // revert log level
        DefaultLoggerSettings.setLogLevel(logLevel);
    }

    @Test
    public void testDebug() {
        resetOutputStreams();
        DefaultLoggerSettings.setDateFormat(new SimpleDateFormat(""));
        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.DEBUG);

        Logger testLogger = LoggerFactory.getLogger("testDebug");

        testLogger.debug("test");
        assertEquals(1, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("DEBUG testDebug - test", regularOutputStream.getLastLine());

        testLogger.debug("test {}", 1);
        assertEquals(2, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("DEBUG testDebug - test 1", regularOutputStream.getLastLine());

        testLogger.debug("test {}{}{}", 1, 2, String.class);
        assertEquals(3, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("DEBUG testDebug - test 12java.lang.String", regularOutputStream.getLastLine());

        Throwable throwable = new Throwable("This exception is part of a test (ignore it)");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        throwable.printStackTrace(printStream);
        String[] lines = outputStream.toString().split("\\n");

        throwable.printStackTrace();
        testLogger.debug("test", throwable);
        assertEquals(5 + lines.length, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
    }

    @Test
    public void testInfo() {
        resetOutputStreams();
        DefaultLoggerSettings.setDateFormat(new SimpleDateFormat(""));
        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.INFO);

        Logger testLogger = LoggerFactory.getLogger("testInfo");

        testLogger.info("test");
        assertEquals(1, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("INFO testInfo - test", regularOutputStream.getLastLine());

        testLogger.info("test {}", 1);
        assertEquals(2, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("INFO testInfo - test 1", regularOutputStream.getLastLine());

        testLogger.info("test {}{}{}", 1, 2, String.class);
        assertEquals(3, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("INFO testInfo - test 12java.lang.String", regularOutputStream.getLastLine());

        Throwable throwable = new Throwable("This exception is part of a test (ignore it)");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        throwable.printStackTrace(printStream);
        String[] lines = outputStream.toString().split("\\n");

        throwable.printStackTrace();
        testLogger.info("test", throwable);
        assertEquals(5 + lines.length, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
    }

    @Test
    public void testTrace() {
        resetOutputStreams();
        DefaultLoggerSettings.setDateFormat(new SimpleDateFormat(""));
        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.TRACE);

        Logger testLogger = LoggerFactory.getLogger("testTrace");

        testLogger.trace("test");
        assertEquals(1, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("TRACE testTrace - test", regularOutputStream.getLastLine());

        testLogger.trace("test {}", 1);
        assertEquals(2, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("TRACE testTrace - test 1", regularOutputStream.getLastLine());

        testLogger.trace("test {}{}{}", 1, 2, String.class);
        assertEquals(3, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("TRACE testTrace - test 12java.lang.String", regularOutputStream.getLastLine());

        Throwable throwable = new Throwable("This exception is part of a test (ignore it)");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        throwable.printStackTrace(printStream);
        String[] lines = outputStream.toString().split("\\n");

        throwable.printStackTrace();
        testLogger.trace("test", throwable);
        assertEquals(5 + lines.length, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
    }

    @Test
    public void testWarning() {
        resetOutputStreams();
        DefaultLoggerSettings.setDateFormat(new SimpleDateFormat(""));
        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.WARN);

        Logger testLogger = LoggerFactory.getLogger("testWarning");

        testLogger.warn("test");
        assertEquals(1, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("WARN testWarning - test", regularOutputStream.getLastLine());

        testLogger.warn("test {}", 1);
        assertEquals(2, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("WARN testWarning - test 1", regularOutputStream.getLastLine());

        testLogger.warn("test {}{}{}", 1, 2, String.class);
        assertEquals(3, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
        assertEquals("WARN testWarning - test 12java.lang.String", regularOutputStream.getLastLine());

        Throwable throwable = new Throwable("This exception is part of a test (ignore it)");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        throwable.printStackTrace(printStream);
        String[] lines = outputStream.toString().split("\\n");

        throwable.printStackTrace();
        testLogger.warn("test", throwable);
        assertEquals(5 + lines.length, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
    }

    @Test
    public void testError() {
        resetOutputStreams();
        DefaultLoggerSettings.setDateFormat(new SimpleDateFormat(""));
        DefaultLoggerSettings.setLogLevel(DefaultLogger.Level.ERROR);

        Logger testLogger = LoggerFactory.getLogger("testError");

        testLogger.error("errortest");
        assertEquals(0, regularOutputStream.getLines().size());
        assertEquals(1, errorOutputStream.getLines().size());
        assertEquals("ERROR testError - errortest", errorOutputStream.getLastLine());

        testLogger.error("test {}", 1);
        assertEquals(0, regularOutputStream.getLines().size());
        assertEquals(2, errorOutputStream.getLines().size());
        assertEquals("ERROR testError - test 1", errorOutputStream.getLastLine());

        testLogger.error("test {}{}{}", 1, 2, String.class);
        assertEquals(0, regularOutputStream.getLines().size());
        assertEquals(3, errorOutputStream.getLines().size());
        assertEquals("ERROR testError - test 12java.lang.String", errorOutputStream.getLastLine());

        Throwable throwable = new Throwable("This exception is part of a test (ignore it)");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        throwable.printStackTrace(printStream);
        String[] lines = outputStream.toString().split("\\n");

        throwable.printStackTrace();
        testLogger.error("test", throwable);
        assertEquals(0, regularOutputStream.getLines().size());
        assertEquals(5 + lines.length, errorOutputStream.getLines().size());
    }

    private void resetOutputStreams() {
        regularOutputStream.reset();
        errorOutputStream.reset();

        assertEquals(0, regularOutputStream.getLines().size());
        assertEquals(0, errorOutputStream.getLines().size());
    }
}

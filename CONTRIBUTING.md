# Contributing

First off, thank you for taking the time to contribute!

This page is a set of guidelines for contributing to this project and its packages.
You are welcome to suggest changes to these guidelines in a pull request.

# How can I contribute?

## Bug Reports

When submitting a bug report, please:

- Mention which dependencies and versions of Spork you are using.
- Use a clear and descriptive title.
- Describes the steps to reproduce the problem.
- If possible, provide a piece of example code that can be used to reproduce the problem.
- Explain the behavior that you observe and the behavior that you expect. 

## Suggesting Enhancements

When submitting an enhancement request, please:

- Use a clear and descriptive title.
- Provide a step-by-step description of the enhancement that you want to request.
- Explain why the enhancement would be useful.

## Pull Requests

### Code quality checks

Please check the following items before creating a pull request:

- Ensure decent test coverage for the code you add: `./gradlew jacocoTestReport` 
- You can run all quality checks as follows: `./gradlew check`
- When adding Android code: run all Android tests on at least one device: `./gradlew connectedAndroidTest`

The `support/` directory has sub-projects that might help you to verify your code quality.

### Language

#### Java 7

Only use Java 7 features that are fully supported on all supported Android versions.

Supported:
- Diamond operator
- String switch
- Multiple-catch
- Underscores in number literals
- Binary literals

NOT supported:
- `try-with-resources`
- `java.lang.SafeVarargs`

See http://stackoverflow.com/a/13550632/3848666 for more information.

#### Nullability

All variables are non-null by default. Any nullable variable must be annotated with `@Nullable`.

### Code Style

- See `checkstyle.xml`.
- Use tabs for indentation.
- The rest is mostly based on IntelliJ defaults.
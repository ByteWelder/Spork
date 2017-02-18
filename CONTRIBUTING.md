# Contributing

## Checklist

Please check the following items before creating a pull request:

- add tests for new features
- check code coverage for your tests: `./gradlew jacocoTestReport`
- run all checks: `./gradlew clean check`
- run all Android tests: `./gradlew connectedAndroidTest`

## Language

### Java 7

Only use Java 7 features that are fully supported on Android.

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

### Nullability

All variables are non-null by default. Any nullable variable must be annotated with `@Nonnull`.
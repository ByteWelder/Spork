# Spork

Spork is an extensible Java library for dependency injection.

## Installation

Edit your `build.gradle` file and add the repository:

```groovy
repositories {
    mavenCentral()
    maven {
        url "https://dl.bintray.com/sporklibrary/spork"
    }
}
```

The next step is to add dependencies:

```groovy
compile 'io.github.sporklibrary:spork:1.3.0'
```

## Usage

### Regular

```java
public class Parent
{
    @Inject
    private Child child;
 
    public Parent()
    {
        Spork.inject(this);
    }
}
 
@Component
public class Child
{
}
```

### Scoped

```java
public class Parent
{
    @Inject
    private Child child;
 
    public Parent()
    {
        Spork.inject(this);
    }
}
 
@Component(scope = Component.Scope.SINGLETON)
public class Child
{
}
```

## License

Spork is available through the [Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

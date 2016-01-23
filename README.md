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

### Components

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

The component can also be defined as a Singleton: `@Component(scope = Component.Scope.SINGLETON)`

## License

Spork is available through the [Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

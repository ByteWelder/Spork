# Spork

Spork is a dependency injection framework.

Aims:
- easy to use
- lightweight
- fast

Features:
- regular and singleton scope
- component auto-registration (as opposed to building a graph with Modules in Dagger)
- no code generation (no special build step required)

## Known limitations

The very first injection on a specific classpath might take some time because that's when the classpath is scanned for components.
A solution to implement in the future is to create a pre-caching method.

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

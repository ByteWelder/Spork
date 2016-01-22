# Spork

Spork is a dependency injection framework.

Features:
- easy to use
- lightweight
- auto-registration of components
- no code generation (no special build step required)

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

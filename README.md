# Spork for Android
Spork is a dependency injection framework designed to make developing apps easier.

The Android part of this project is currently under development.

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
compile 'io.github.sporklibrary:spork-android:1.0.0'
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


### Views

View injection works with classes derived from `Activity`, `Fragment` and `View`.

```java
public class MyActivity extends Activity
{
    // Inject by ID
	@InjectView(R.id.button)
	private Button mButton;

    // Inject by field name
	@InjectView
	private Button button;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_test);
		
		// Spork does its magic
		Spork.inject(this);
	}
}
```

## License

Spork is available through the [Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

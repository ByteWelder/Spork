# Spork for Android
Spork is a field and method binding library designed to make developing apps easier.

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
dependencies {
    compile 'io.github.sporklibrary:spork-android:1.3.0'
}
```

The last step is to initialize at least once. You can do this anywhere before calling `Spork.bind()`:

```java
SporkAndroid.initialize();
```

## Usage

### Components

Any Java object can become a component. Components can be easily bound anywhere.

For example:

```java
public class Parent
{
    @BindComponent
    private Child child;
 
    public Parent()
    {
        Spork.bind(this);
    }
}
 
@Component
public class Child
{
}
```

The component scope can be customized: `@Component(scope = Component.Scope.SINGLETON)`

The default scope creates a new instance for each binding.

`BindComponent` can also be used inject for a base class or interface as long as you specify `@BindComponent(implementation = ...)`

### Views

View binding works with classes derived from `Activity`, `Fragment` and `View`.

```java
public class MyActivity extends Activity
{
	// Bind by ID
	@BindView(R.id.my_button)
	private Button mButton;

	// Bind by field name
	@BindView
	private Button button;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.my_activity);
		
		// Spork does its magic
		Spork.bind(this);
	}
}
```

### Fragments

Fragment binding works with classes derived from `Fragment`. The v4 support library Fragments are not yet supported.

```java
public class MyActivity extends Activity
{
	@BindFragment(R.id.my_fragment)
	private Fragment mFragment;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.my_activity);
		
		// Spork does its magic
		Spork.bind(this);
	}
}
```

### Clicks

Click binding works within `Activity`, `Fragment` and `View`. It works with `View` as a target.

The method can be either without arguments or with exactly 1 argument that accepts any class or subclass that is compatible with the bound view.

```java
public class MyActivity extends Activity
{
	// Bind by ID
	@BindClick(R.id.first_button)
	private void onClickMyButton()
	{
	}

	// Bind by method name and accept Button as parameter
	@BindClick
	private void second_button(Button button)
	{
	}

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.my_activity);
		
		// Spork does its magic
		Spork.bind(this);
	}
}
```

## License

Spork is available through the [Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

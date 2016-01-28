# Spork
[![Build Status][build-status-svg]][build-status-link]
[![Coverage Status][coverage-svg]][coverage-link]
[![Download][download-svg]][download-link]
[![License][license-svg]][license-link]

Spork is an extensible Java library for field and method binding.

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
compile 'io.github.sporklibrary:spork:1.8.0'
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

## License

Spork is available through the [Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

[build-status-svg]: http://img.shields.io/travis/SporkLibrary/Spork/master.svg?style=flat
[build-status-link]: https://travis-ci.org/SporkLibrary/Spork
[download-svg]: https://api.bintray.com/packages/sporklibrary/spork/spork/images/download.svg
[download-link]: https://bintray.com/sporklibrary/spork/spork/_latestVersion
[license-svg]: https://img.shields.io/badge/license-Apache%202.0-lightgrey.svg?style=flat
[license-link]: https://github.com/SporkLibrary/Spork/blob/master/LICENSE
[coverage-svg]: https://coveralls.io/repos/github/SporkLibrary/Spork/badge.svg?branch=master
[coverage-link]: https://coveralls.io/github/SporkLibrary/Spork?branch=master

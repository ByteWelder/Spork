# Spork Example

This is project:

- showcases how `spork`, `spork-android` and `spork-inject` can be used
- facilitates an integration test for the mentioned libraries
- facilitates an integration test for ProGuard

ProGuard settings from `spork-android` ensure that the `spork.*` symbols and annotations are kept.
That is why this project's namespace is not `spork.example` but `example.spork`.
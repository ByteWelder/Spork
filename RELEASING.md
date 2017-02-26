# Publishing

## Verify

1. ensure changes are merged to `master`
2. run all tests: `./gradlew clean check`
3. check code coverage: `./gradlew jacocoTestReport`
4. check if `gradle.properties` has `VERSION_NAME` set correctly

## Upload

Build and publish artifact to Maven repository:

./gradlew spork-inject:dependencies -PsporkReleaseTarget=spork-inject | grep spork

1. run `./gradlew spork:install spork:uploadArchives`
2. run `./gradlew -PsporkReleaseTarget=spork-inject spork-inject:uploadArchives spork-inject:install`
3. run `./gradlew -PsporkReleaseTarget=spork-android spork-android:uploadArchives spork-android:install`
4. run `./gradlew -PsporkReleaseTarget=spork-android-support spork-android-support:uploadArchives spork-android-support:install`
5. Stage libraries on [Sonatype]
6. create and push tag with version to git
7. bump the version in `gradle.properties`
8. update the website:
    - getting started guide library version
    - release notes

Snapshots are available through `maven { url 'https://oss.sonatype.org/content/repositories/snapshots/' }`

[Sonatype]: https://oss.sonatype.org "sonatype.org"
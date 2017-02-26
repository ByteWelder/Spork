# Publishing

## Verify

1. ensure changes are merged to `master`
2. run all tests: `./gradlew clean check`
3. check code coverage: `./gradlew jacocoTestReport`
4. check if the root `build.gradle` has `sporkVersion` set correctly

## Upload

Build and publish artifact to Maven repository:

./gradlew spork-inject:dependencies -PsporkReleaseTarget=spork-inject | grep spork

1. run `./gradlew spork:clean spork:jar spork:bintrayUpload -PdryRun=false`
2. run `./gradlew -PsporkReleaseTarget=spork-inject spork-inject:clean spork-inject:jar spork-inject:bintrayUpload spork-inject:install -PdryRun=false`
3. run `./gradlew -PsporkReleaseTarget=spork-android spork-android:clean spork-android:assembleRelease spork-android:assembleDebug spork-android:bintrayUpload spork-android:publishToMavenLocal -PdryRun=false`
4. run `./gradlew -PsporkReleaseTarget=spork-android-support spork-android-support:clean spork-android-support:assembleRelease spork-android-support:assembleDebug spork-android-support:bintrayUpload spork-android-support:publishToMavenLocal -PdryRun=false`
5. create and push tag with version to git
6. bump the version in `gradle.properties`
7. update the website:
    - getting started guide library version
    - release notes

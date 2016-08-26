# Publishing

- updated the version in `gradle.properties`
- ensure changes are merged to `master`
- run all tests: `./gradlew clean connectedCheck`
- build and publish artifact to Maven repository:
    - `./gradlew uploadArchives`
    - Staging is available at [oss.sonatype.org](https://oss.sonatype.org)
    - Snapshots are available through `maven { url 'https://oss.sonatype.org/content/repositories/snapshots/' }`
- create and push tag with version to git
- update the website:
    - getting started guide library version
    - release notes
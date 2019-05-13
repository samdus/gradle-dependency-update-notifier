# Release Process

- check for breaking open issues and PRs
- create release issue
- make sure CI status is green
- check and update [`CHANGELOG.md`](https://github.com/muhlba91/gradle-dependency-update-notifier/blob/master/docs/CHANGELOG.md) for new version
- create and push tag:
```
git tag -a x.y.z -m x.y.z
git push --tags
```
- check CI and [Gradle Plugin Repository](https://plugins.gradle.org/plugin/org.muehlbachler.gradle.plugin.dependency-update-notifier) for new release
- update [`VERSION`](https://github.com/muhlba91/gradle-dependency-update-notifier/blob/master/VERSION) to new development (`SNAPSHOT`) version
- push new development version
- close release issue
- close milestone, create new one and move all open issues/PRs to this milestone

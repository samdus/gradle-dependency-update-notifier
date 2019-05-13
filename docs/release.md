# Release Process

- check for breaking open issues and PRs
- create release issue
- make sure CI status is green
- check [`CHANGELOG.md`](https://github.com/muhlba91/gradle-dependency-update-notifier/blob/master/docs/CHANGELOG.md) for completeness
- update files to remove `SNAPSHOT`
  - [`VERSION`](https://github.com/muhlba91/gradle-dependency-update-notifier/blob/master/VERSION)
  - [`CHANGELOG.md`](https://github.com/muhlba91/gradle-dependency-update-notifier/blob/master/docs/CHANGELOG.md)
- push changes, create and push new release tag:
```
git commit -am "fix #<issue_number> - releasing x.y.z"
git push
git tag -a x.y.z -m "x.y.z"
git push --tags
```
- check CI and [Gradle Plugin Repository](https://plugins.gradle.org/plugin/org.muehlbachler.gradle.plugin.dependency-update-notifier) for new release
- update files for new development (`SNAPSHOT`) version:
  - [`VERSION`](https://github.com/muhlba91/gradle-dependency-update-notifier/blob/master/VERSION)
  - [`CHANGELOG.md`](https://github.com/muhlba91/gradle-dependency-update-notifier/blob/master/docs/CHANGELOG.md)
- push new development version
```
git commit -am "fix #<issue> - bump version"
git push
```
- milestone
  - close old milestone
  - open new milestone
  - move all open issues/pull requests assigned to the previous milestone to the new one

*Hint*: release issue should be closed automatically.

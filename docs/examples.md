# Examples

## GitLab Notifier

A basic `build.gradle` file could look like this:

```groovy
plugins {
  id 'java-library'
  id 'org.muehlbachler.gradle.plugin.dependency-update-notifier' version "$version"
  id 'com.github.ben-manes.versions' version '0.21.0'
}

repositories {
  mavenCentral()
  jcenter()
}

dependencyUpdates {
  outputFormatter=json
}

dependencyUpdateNotifier {
  // json = 'build/dependencyUpdates/report.json'
  
  gitlab {
    url = 'http://gitlab.test'
    projectId = 1
    token = 'token'
    label = 'label'
    title = 'Dependency Updates (%count)'
  }
}
dependencyUpdateNotifier.dependsOn 'dependencyUpdates'
```

Replace `$version` with the latest plugin version and to run:

```
gradle gitlabDependencyUpdateNotifier
```

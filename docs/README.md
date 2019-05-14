# Get Started

This Gradle plugin creates GitLab issues for outdated project dependencies found by the [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin).

Link to the [Gradle Plugin Registry](https://plugins.gradle.org/plugin/org.muehlbachler.gradle.plugin.dependency-update-notifier).


## Usage

### `plugins` block:

Build script snippet for plugins DSL for Gradle 2.1 and later:

```groovy
plugins {
  id "org.muehlbachler.gradle.plugin.dependency-update-notifier" version "$version"
}
```

### `buildscript` block:

Build script snippet for use in older Gradle versions or where dynamic configuration is required:

```groovy
apply plugin: "org.muehlbachler.gradle.plugin.dependency-update-notifier"

buildscript {
  repositories {
  maven { url "https://plugins.gradle.org/m2/"}
  }

  dependencies {
  classpath "org.muehlbachler.gradle.plugin.dependency-update-notifier:$version"
  }
}
```


## Configuration

The following configuration block is **required**.

```groovy
dependencyUpdateNotifier {
  json =  'build/dependencyUpdates/report.json'
  
  gitlab {
    url = 'GitLab URL'
    projectId = 0
    token = 'GitLab Private Token'
    label = 'label1,label2'
    title = 'Dependency Updates (%count)'
  }
}
```


## Tasks

The plugin comes with a task which publishes the issue to GitLab:

```
gradle gitlabDependencyUpdateNotifier
``` 

**Attention!** Make sure to *execute the versions plugin task before*: `gradle dependencyUpdates`.

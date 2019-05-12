# Dependency Update Notifier (Gradle Plugin)

This Gradle plugin creates GitLab issues for outdated project dependencies found by the [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin).

Link to the Gradle Plugin Registry: [https://plugins.gradle.org/plugin/org.muehlbachler.gradle.plugin.dependency-update-notifier](https://plugins.gradle.org/plugin/org.muehlbachler.gradle.plugin.dependency-update-notifier)


## Project Status

[![Build Status](https://travis-ci.org/muhlba91/gradle-dependency-update-notifier.svg?branch=master)](https://travis-ci.org/muhlba91/gradle-dependency-update-notifier)


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
  json =  "/path/to/report.json"
  
  gitlab {
    url = "GitLab URL"
    projectId = 0
    token = "GitLab Private Token"
    label = "label1,label2"
    title = "Dependency Updates (%count)"
  }
}
```

The value provided in `gitlab.label` corresponds to the labels set on the generated issue.

The value provided in `gitlab.title` is the generated issues's title and can interpolate the number of available updates through `%count`.


## Tasks

The plugin comes with a task which publishes the issue to GitLab:

```
./gradlew gitlabDependencyUpdateNotifier
``` 


## Contributions

Submit an issue describing the problem(s)/question(s) and proposed fixes/work-arounds.

To contribute, just fork the repository, develop and test your code changes and submit a pull request.

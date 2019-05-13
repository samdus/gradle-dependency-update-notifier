# Dependency Update Notifier (Gradle Plugin)

This Gradle plugin creates GitLab issues for outdated project dependencies found by the [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin).

Link to the [Gradle Plugin Registry](https://plugins.gradle.org/plugin/org.muehlbachler.gradle.plugin.dependency-update-notifier).


## Project Status

[![Build Status](https://travis-ci.org/muhlba91/gradle-dependency-update-notifier.svg?branch=master)](https://travis-ci.org/muhlba91/gradle-dependency-update-notifier)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/org/muehlbachler/gradle/plugin/dependency-update-notifier/org.muehlbachler.gradle.plugin.dependency-update-notifier.gradle.plugin/maven-metadata.xml.svg?label=Gradle%20Plugin)](https://plugins.gradle.org/plugin/org.muehlbachler.gradle.plugin.dependency-update-notifier)
[![Documentation](https://img.shields.io/badge/docs-latest-yellow.svg)](https://muhlba91.github.io/gradle-dependency-update-notifier)
[![CPAN](https://img.shields.io/cpan/l/Config-Augeas.svg)](https://github.com/muhlba91/gradle-dependency-update-notifier/blob/master/LICENSE)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=org.muehlbachler.gradle.plugin.dependency-update-notifier&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.muehlbachler.gradle.plugin.dependency-update-notifier)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=org.muehlbachler.gradle.plugin.dependency-update-notifier&metric=security_rating)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=org.muehlbachler.gradle.plugin.dependency-update-notifier)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=org.muehlbachler.gradle.plugin.dependency-update-notifier&metric=coverage)](https://sonarcloud.io/component_measures/metric/coverage/list?id=org.muehlbachler.gradle.plugin.dependency-update-notifier)
[![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=org.muehlbachler.gradle.plugin.dependency-update-notifier&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?id=org.muehlbachler.gradle.plugin.dependency-update-notifier)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=org.muehlbachler.gradle.plugin.dependency-update-notifier&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=org.muehlbachler.gradle.plugin.dependency-update-notifier)


## Documentation

The documentation can be found [here](https://muhlba91.github.io/gradle-dependency-update-notifier).


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


## Contributions

Submit an issue describing the problem(s)/question(s) and proposed fixes/work-arounds.

To contribute, just fork the repository, develop and test your code changes and submit a pull request.

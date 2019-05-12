package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.dependency;

import lombok.Data;

@Data
public class AvailableDependency {
    String release;
    String milestone;
    String integration;
}

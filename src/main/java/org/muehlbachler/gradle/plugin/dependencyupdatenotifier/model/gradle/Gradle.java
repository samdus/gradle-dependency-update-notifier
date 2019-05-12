package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gradle;

import lombok.Data;

@Data
public class Gradle {
    String version;
    String reason;
    boolean isUpdateAvailable;
    boolean isFailure;
}

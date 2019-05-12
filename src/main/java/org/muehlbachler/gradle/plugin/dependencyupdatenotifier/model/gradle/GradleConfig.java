package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gradle;

import lombok.Data;

import java.util.Objects;

@Data
public class GradleConfig {
    Gradle current;
    Gradle nightly;
    Gradle releaseCandidate;
    Gradle running;
    boolean enabled;

    public boolean isUpdateAvailable() {
        return (Objects.nonNull(current) && current.isUpdateAvailable()) || (Objects.nonNull(releaseCandidate) && releaseCandidate.isUpdateAvailable());
    }

    public boolean hasCurrentVersionUpdate() {
        return Objects.nonNull(current) && current.isUpdateAvailable() && running.getVersion().compareTo(current.getVersion()) < 0;
    }

    public boolean hasReleaseCandidateVersionUpdate() {
        return Objects.nonNull(releaseCandidate) && releaseCandidate.isUpdateAvailable() && running.getVersion().compareTo(releaseCandidate.getVersion()) < 0;
    }
}

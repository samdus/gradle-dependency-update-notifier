package org.muehlbachler.gradle.plugin.dependencyupdatenotifier;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import lombok.Data;
import lombok.EqualsAndHashCode;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.DependencyAnalysis;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.dependency.Dependency;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gradle.GradleConfig;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseTask extends DefaultTask {

    @Input
    String jsonPath = "build/dependencyUpdates/report.json";

    public void init() {
        setGroup("reporting");
    }

    @TaskAction
    public void action() {
        try {
            final File jsonFile = getProject().file(jsonPath);
            final DependencyAnalysis currentDependencies = getCurrentDependencies();
            final DependencyAnalysis dependencies = Optional.ofNullable(readDependenciesFromJson(jsonFile))
                    .map(newDependencies -> filterOutdatedIssues(newDependencies, currentDependencies))
                    .map(newDependencies -> filterGradleIssue(newDependencies, currentDependencies))
                    .orElse(null);
            if(Objects.nonNull(dependencies)) {
                handleDependencies(dependencies);
            }
        } catch(final IOException e) {
            getLogger().error("Failed executing task.", e);
            throw new GradleException("Failed due to " + e.getMessage(), e);
        }
    }

    protected abstract void handleDependencies(final DependencyAnalysis dependencies) throws IOException;

    protected DependencyAnalysis getCurrentDependencies() throws IOException {
        return new DependencyAnalysis();
    }

    private DependencyAnalysis filterOutdatedIssues(final DependencyAnalysis dependencies, final DependencyAnalysis currentDependencies) {
        final List<Dependency> currentOutdated = currentDependencies.getOutdated().getDependencies();
        final List<Dependency> outdatedDependencies = dependencies.getOutdated().getDependencies().stream()
                .map(dependency -> {
                    final Optional<Dependency> foundDependency = currentOutdated.stream()
                            .filter(currentDependency -> currentDependency.equals(dependency))
                            .findFirst();
                    return !foundDependency.isPresent() ? dependency : foundDependency
                            .map(currentDependency -> StringUtils.compare(dependency.getAvailable().getRelease(), currentDependency.getAvailable().getRelease()) > 0 ? dependency : null)
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Dependency::getRepresentation))
                .collect(Collectors.toList());
        dependencies.getOutdated().setDependencies(outdatedDependencies);
        dependencies.getOutdated().setCount(outdatedDependencies.size());
        return dependencies;
    }

    private DependencyAnalysis filterGradleIssue(final DependencyAnalysis dependencies, final DependencyAnalysis currentDependencies) {
        final GradleConfig gradle = dependencies.getGradle();
        final GradleConfig currentGradle = currentDependencies.getGradle();
        if(!gradle.isUpdateAvailable()) {
            return dependencies;
        }

        final boolean currentVersionUpdateAvailable = gradle.hasCurrentVersionUpdate() && (!currentGradle.hasCurrentVersionUpdate() || StringUtils.compare(gradle.getCurrent().getVersion(), currentGradle.getCurrent().getVersion()) > 0);
        final boolean releaseCandidateVersionUpdate = gradle.hasReleaseCandidateVersionUpdate() && (!currentGradle.hasReleaseCandidateVersionUpdate() || StringUtils.compare(gradle.getReleaseCandidate().getVersion(), currentGradle.getReleaseCandidate().getVersion()) > 0);
        if(!currentVersionUpdateAvailable) {
            gradle.getCurrent().setUpdateAvailable(false);
        }
        if(!releaseCandidateVersionUpdate) {
            gradle.getReleaseCandidate().setUpdateAvailable(false);
        }

        return dependencies;
    }

    private DependencyAnalysis readDependenciesFromJson(final File jsonFile) throws IOException {
        final JsonAdapter<DependencyAnalysis> jsonReader = new Moshi.Builder().build().adapter(DependencyAnalysis.class);
        final Source fileSource = Okio.source(jsonFile);
        final BufferedSource source = Okio.buffer(fileSource);
        return jsonReader.fromJson(source);
    }
}

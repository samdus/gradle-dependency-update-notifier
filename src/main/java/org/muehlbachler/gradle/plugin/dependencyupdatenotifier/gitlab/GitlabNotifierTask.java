package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.gitlab;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.tasks.Input;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.BaseTask;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.DependencyAnalysis;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.dependency.Dependency;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gitlab.GitlabNotifierConfig;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gitlab.issue.GitlabIssue;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gradle.GradleConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class GitlabNotifierTask extends BaseTask {
    public static final String NAME = "gitlabDependencyUpdateNotifier";

    @Input
    GitlabNotifierConfig config;
    GitlabClient client;

    public void init() {
        super.init();
        client = new GitlabClient(config, getLogger());

        setDescription("Creates a GitLab issue for outdated dependencies.");
    }

    @Override
    protected void handleDependencies(final DependencyAnalysis dependencies) throws IOException {
        final List<String> outdatedIssues = getOutdatedIssues(dependencies);
        final String gradleIssue = getGradleIssue(dependencies);
        if(!outdatedIssues.isEmpty() || StringUtils.isNotEmpty(gradleIssue)) {
            final GitlabIssue gitlabIssue = buildIssue(outdatedIssues, gradleIssue);
            client.createIssue(gitlabIssue);
        }
    }

    private List<String> getOutdatedIssues(final DependencyAnalysis dependencies) {
        final List<Dependency> outdatedDependencies = dependencies.getOutdated().getDependencies();
        outdatedDependencies.sort(Comparator.comparing(Dependency::getRepresentation));
        return outdatedDependencies.stream()
                .map(dependency -> {
                    String dependencyString = String.format("- [ ] `%s`", dependency.getIssueRepresentation());
                    if(dependency.hasProjectUrl()) {
                        dependencyString = String.format("%s - [%s](%s)", dependencyString, dependency.getProjectUrl(), dependency.getProjectUrl());
                    }
                    return dependencyString;
                })
                .collect(Collectors.toList());
    }

    private String getGradleIssue(final DependencyAnalysis dependencies) {
        final GradleConfig gradle = dependencies.getGradle();
        if(!gradle.isUpdateAvailable()) {
            return null;
        }

        String newVersion = "";
        if(gradle.hasCurrentVersionUpdate()) {
            newVersion = gradle.getCurrent().getVersion();
        } else if(gradle.hasReleaseCandidateVersionUpdate()) {
            newVersion = gradle.getReleaseCandidate().getVersion() + " (RC)";
        }
        return String.format("- [ ] Gradle `%s` -> `%s`", gradle.getRunning().getVersion(), newVersion);
    }

    private GitlabIssue buildIssue(final List<String> outdatedIssues, final String gradleIssue) {
        final long count = outdatedIssues.size() + (StringUtils.isNotEmpty(gradleIssue) ? 1L : 0L);
        final String title = config.getTitle()
                .replaceAll("%count", Long.toString(count));
        String body = String.join("\n", outdatedIssues);
        if(StringUtils.isNotEmpty(gradleIssue)) {
            body += (outdatedIssues.isEmpty() ? "" : "\n\n") + gradleIssue;
        }
        final List<String> labels = Arrays.asList(config.getLabel().split(","));
        return new GitlabIssue()
                .setTitle(title)
                .setDescription(body)
                .setLabels(labels);
    }
}

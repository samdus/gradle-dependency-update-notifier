package org.muehlbachler.gradle.plugin.dependencyupdatenotifier;

import groovy.lang.Closure;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gradle.api.Project;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gitlab.GitlabNotifierConfig;

@Data
@RequiredArgsConstructor
public class DependencyUpdateNotifierExtension {
    public static final String NAME = "dependencyUpdateNotifier";

    @NonNull Project project;
    GitlabNotifierConfig gitlab;
    String json = "build/dependencyUpdates/report.json";

    GitlabNotifierConfig gitlab(final Closure closure) {
        gitlab = new GitlabNotifierConfig();
        project.configure(gitlab, closure);
        return gitlab;
    }
}

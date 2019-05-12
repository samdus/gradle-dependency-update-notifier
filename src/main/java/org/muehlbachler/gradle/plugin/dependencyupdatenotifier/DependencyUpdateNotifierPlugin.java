package org.muehlbachler.gradle.plugin.dependencyupdatenotifier;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.gitlab.GitlabNotifierTask;

public class DependencyUpdateNotifierPlugin implements Plugin<Project> {
    @Override
    public void apply(final Project project) {
        final DependencyUpdateNotifierExtension extension = project.getExtensions().create(DependencyUpdateNotifierExtension.NAME, DependencyUpdateNotifierExtension.class, project);

        project.getTasks().register(GitlabNotifierTask.NAME, GitlabNotifierTask.class, gitlabNotifierTask -> {
            gitlabNotifierTask.setJsonPath(extension.getJson());
            gitlabNotifierTask.setConfig(extension.getGitlab());
            gitlabNotifierTask.init();
        });
    }
}

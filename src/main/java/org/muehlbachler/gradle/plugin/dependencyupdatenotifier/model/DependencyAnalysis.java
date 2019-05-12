package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model;

import lombok.Data;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.dependency.Dependencies;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gradle.GradleConfig;

@Data
public class DependencyAnalysis {
    GradleConfig gradle = new GradleConfig();
    Dependencies current = new Dependencies();
    Dependencies exceeded = new Dependencies();
    Dependencies outdated = new Dependencies();
    Dependencies unresolved = new Dependencies();
    int count;
}

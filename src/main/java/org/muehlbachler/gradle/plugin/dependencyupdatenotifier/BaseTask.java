package org.muehlbachler.gradle.plugin.dependencyupdatenotifier;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import lombok.Data;
import lombok.EqualsAndHashCode;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.DependencyAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
            final DependencyAnalysis dependencies = readDependenciesFromJson(jsonFile);
            if(Objects.nonNull(dependencies)) {
                handleDependencies(dependencies);
            }
        } catch(final IOException e) {
            getLogger().error("Failed executing task.", e);
            throw new GradleException("Failed due to " + e.getMessage(), e);
        }
    }

    protected abstract void handleDependencies(final DependencyAnalysis dependencies) throws IOException;

    private DependencyAnalysis readDependenciesFromJson(final File jsonFile) throws IOException {
        final JsonAdapter<DependencyAnalysis> jsonReader = new Moshi.Builder().build().adapter(DependencyAnalysis.class);
        final Source fileSource = Okio.source(jsonFile);
        final BufferedSource source = Okio.buffer(fileSource);
        return jsonReader.fromJson(source);
    }
}

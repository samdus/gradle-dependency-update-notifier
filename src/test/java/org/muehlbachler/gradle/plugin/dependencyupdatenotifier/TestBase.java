package org.muehlbachler.gradle.plugin.dependencyupdatenotifier;

import io.specto.hoverfly.junit5.HoverflyExtension;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.util.FileTestUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@ExtendWith(HoverflyExtension.class)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class TestBase {
    @TempDir
    public Path testProjectDir;
    File buildFile;
    File propertiesFile;
    File reportJson;

    String testName;

    protected void init() throws IOException {
        Assertions.assertThat(Files.isDirectory(testProjectDir)).isTrue();

        buildFile = Files.createFile(testProjectDir.resolve("build.gradle")).toFile();
        propertiesFile = Files.createFile(testProjectDir.resolve("gradle.properties")).toFile();
        reportJson = Files.createFile(testProjectDir.resolve("report.json")).toFile();
    }

    protected GradleRunner getRunner(final String task) {
        return GradleRunner.create()
                .withProjectDir(testProjectDir.toFile())
                .withArguments(task)
                .withDebug(true)
                .withPluginClasspath().forwardOutput();
    }

    protected BuildResult executeRunner(final String task) {
        return getRunner(task).build();
    }

    protected BuildResult executeFailedRunner(final String task) {
        return getRunner(task).buildAndFail();
    }

    protected void generateBuildFile(final int proxyPort, final String buildFileName, final Map<String, String> properties) throws IOException {
        FileTestUtil.generateBuildFile(proxyPort, testName, buildFileName, buildFile, propertiesFile, properties);
    }

    protected void generateBuildFile(final int proxyPort, final Map<String, String> properties) throws IOException {
        FileTestUtil.generateBuildFile(proxyPort, testName, "test.build.gradle", buildFile, propertiesFile, properties);
    }

    protected void copyReportJson(final String name) throws IOException {
        FileTestUtil.copyReportJson(testName, name, reportJson);
    }

    protected String readResource(final String name) throws IOException {
        return FileTestUtil.readResource(testName + "/" + name);
    }
}

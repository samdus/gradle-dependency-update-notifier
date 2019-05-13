package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.util;

import okio.Okio;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FileTestUtil {
    public static String readResource(final String path) throws IOException {
        final InputStream file = FileTestUtil.class
                .getClassLoader()
                .getResourceAsStream(path);
        return Okio.buffer(Okio.source(file)).readUtf8();
    }

    public static void writeFile(final File destination, final String content) throws IOException {
        try(final BufferedWriter output = new BufferedWriter(new FileWriter(destination))) {
            output.write(content);
        }
    }

    public static void generateBuildFile(final int proxyPort, final String test, final String resourceName, final File buildFile, final File propertiesFile, final Map<String, String> replacements) throws IOException {
        final String buildFileContent = readResource(test + "/" + resourceName);
        final String content = StringUtils.replaceEachRepeatedly(buildFileContent, replacements.keySet().toArray(new String[0]), replacements.values().toArray(new String[0]));
        writeFile(buildFile, content);

        if(proxyPort > 0) {
            final String propertiesFileContent = readResource("test.gradle.properties").replaceAll("%proxyPort", Integer.toString(proxyPort));
            writeFile(propertiesFile, propertiesFileContent);
        }
    }

    public static void copyReportJson(final String test, final String name, final File reportJson) throws IOException {
        final String reportJsonContent = readResource(test + "/dependencyUpdates/" + name + ".json");
        writeFile(reportJson, reportJsonContent);
    }
}

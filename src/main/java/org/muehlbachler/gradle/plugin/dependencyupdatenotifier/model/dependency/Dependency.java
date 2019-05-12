package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.dependency;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class Dependency {
    String group;
    String name;
    String version;
    String projectUrl;
    AvailableDependency available;

    public boolean hasProjectUrl() {
        return StringUtils.isNotEmpty(projectUrl);
    }

    public String getRepresentation() {
        return group + ":" + name;
    }

    public String getIssueRepresentation() {
        return String.format("%s:(%s -> %s)", getRepresentation(), version, available.getRelease());
    }
}

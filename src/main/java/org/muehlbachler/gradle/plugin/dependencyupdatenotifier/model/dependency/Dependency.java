package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.dependency;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Dependency {
    @EqualsAndHashCode.Include
    String group;
    @EqualsAndHashCode.Include
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

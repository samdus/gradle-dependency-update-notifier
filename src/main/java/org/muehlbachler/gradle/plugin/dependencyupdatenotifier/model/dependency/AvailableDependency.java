package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.dependency;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class AvailableDependency {
    String release;
    String milestone;
    String integration;

    public String getNewRelease() {
        return StringUtils.isNotEmpty(release) ? release : milestone;
    }
}

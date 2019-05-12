package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.dependency;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Dependencies {
    List<Dependency> dependencies = new ArrayList<>();
    int count;
}

package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gitlab;

import lombok.Data;

@Data
public class GitlabNotifierConfig {
    String url;
    long projectId;
    String token;
    String label;
    String title;
}

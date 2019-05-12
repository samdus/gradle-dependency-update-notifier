package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gitlab.issue;

import lombok.Data;

import java.util.List;

@Data
public class GitlabIssue {
    Long projectId;
    GitlabAuthor author;
    String description;
    List<GitlabAuthor> assignees;
    List<String> labels;
    Long id;
    String title;
    String webUrl;
}

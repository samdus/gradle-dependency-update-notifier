package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gitlab.issue;

import com.squareup.moshi.Json;
import lombok.Data;

import java.util.List;

@Data
public class GitlabIssue {
    @Json(name = "project_id")
    Long projectId;
    GitlabAuthor author;
    String description;
    List<GitlabAuthor> assignees;
    List<String> labels;
    Long id;
    String title;
    @Json(name = "web_url")
    String webUrl;
}

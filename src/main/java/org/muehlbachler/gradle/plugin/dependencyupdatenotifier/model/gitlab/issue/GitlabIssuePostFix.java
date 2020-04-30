package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gitlab.issue;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.Json;
import lombok.Data;
import org.w3c.dom.html.HTMLImageElement;

import java.util.Arrays;
import java.util.List;


/*
    This class is a FIX for GitLab Enterprise Edition 11.6.9-ee
    In the documentation, it should always be String, although in this version
    POST uses a String and GET uses a List
*/
@Data
public class GitlabIssuePostFix {
    @Json(name = "project_id")
    Long projectId;
    GitlabAuthor author;
    String description;
    List<GitlabAuthor> assignees;
    String labels;
    Long id;
    String title;
    @Json(name = "web_url")
    String webUrl;

    public GitlabIssuePostFix(GitlabIssue original){
        setProjectId(original.getProjectId());
        setAuthor(original.getAuthor());
        setDescription(original.getDescription());
        setAssignees(original.getAssignees());
        setLabels(String.join(",", original.getLabels()));
        setId(original.getId());
        setTitle(original.getTitle());
        setWebUrl(original.getWebUrl());
    }

    public GitlabIssue toGitlabIssue(){
        return new GitlabIssue()
            .setProjectId(projectId)
            .setAuthor(author)
            .setDescription(description)
            .setAssignees(assignees)
            .setLabels(Arrays.asList(labels.split(",")))
            .setId(id)
            .setTitle(title)
            .setWebUrl(webUrl);
    }
}

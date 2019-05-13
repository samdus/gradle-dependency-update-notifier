package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.gitlab;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.gradle.api.logging.Logger;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.BaseClient;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gitlab.GitlabNotifierConfig;
import org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gitlab.issue.GitlabIssue;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@FieldDefaults(makeFinal = true)
public class GitlabClient extends BaseClient {
    public static final String TOKEN = "private-token";

    Logger logger;
    GitlabNotifierConfig config;

    JsonAdapter<List<GitlabIssue>> issueListAdapter;
    JsonAdapter<GitlabIssue> issueAdapter;

    public GitlabClient(final GitlabNotifierConfig config, final Logger logger) {
        this.logger = logger;
        this.config = config;

        final ParameterizedType gitlabIssueListType = Types.newParameterizedType(List.class, GitlabIssue.class);
        final Moshi moshi = new Moshi.Builder().build();
        issueListAdapter = moshi.adapter(gitlabIssueListType);
        issueAdapter = moshi.adapter(GitlabIssue.class);

        getRequestBuilder()
                .addHeader(TOKEN, config.getToken());
    }

    public List<GitlabIssue> getIssues() throws IOException {
        final Request request = getRequestBuilder()
                .url(String.format("%s/projects/%s/issues?labels=%s&state=opened", config.getUrl(), config.getProjectId(), config.getLabel()))
                .build();
        final Response response = getClient().newCall(request).execute();
        final int statusCode = response.code();
        if(statusCode != HttpURLConnection.HTTP_OK || Objects.isNull(response.body())) {
            throw new IllegalArgumentException(String.format("Could not retrieve GitLab issues: %s, %d", config.getUrl(), statusCode));
        }
        final List<GitlabIssue> gitlabIssues = issueListAdapter.fromJson(response.body().source());
        response.body().close();
        return gitlabIssues;
    }

    public GitlabIssue createIssue(final GitlabIssue issue) throws IOException {
        final String json = issueAdapter.toJson(issue);
        final RequestBody requestBody = RequestBody.create(JSON, json);
        final Request request = getRequestBuilder()
                .url(String.format("%s/projects/%s/issues", config.getUrl(), config.getProjectId()))
                .post(requestBody)
                .build();
        final Response response = getClient().newCall(request).execute();
        final int statusCode = response.code();
        if(statusCode != HttpURLConnection.HTTP_OK || Objects.isNull(response.body())) {
            throw new IllegalArgumentException(String.format("Could not create GitLab issue: %s, %d", config.getUrl(), statusCode));
        }
        final GitlabIssue createdIssue = issueAdapter.fromJson(response.body().source());
        response.body().close();
        return createdIssue;
    }
}

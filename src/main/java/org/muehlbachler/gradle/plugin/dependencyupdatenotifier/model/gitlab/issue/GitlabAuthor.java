package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gitlab.issue;

import com.squareup.moshi.Json;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
public class GitlabAuthor {
    @Json(name = "web_url")
    String webUrl;
    String username;
    long id;
    String name;
}

package org.muehlbachler.gradle.plugin.dependencyupdatenotifier.model.gitlab.issue;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
public class GitlabAuthor {
    String webUrl;
    String username;
    long id;
    String name;
}

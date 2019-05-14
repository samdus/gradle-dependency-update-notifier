# Configuration

Configuration is done using the **required** `dependencyUpdateNotifier` block in your `build.gradle` file.

Main properties are:

| Property | Description | Default |
|----------|-------------|---------|
| json | path to the JSON report of the [Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin) | `build/dependencyUpdates/report.json` |


## GitLab

To configure the GitLab notifier, you **must** configure the `gitlab` sub-block as well.

| Property | Description |
|----------|-------------|
| url | GitLab API URL |
| projectId | GitLab's project ID to post issues to |
| token | private authentication token for GitLab |
| label | comma-delimited labels used to create and retrieve issues |
| title | the title of the created issue |

Moreover, it is possible to *interpolate* the following variables in the `title` property:

| Variable | Description |
|----------|-------------|
| `%count` | the number of updatable dependencies |

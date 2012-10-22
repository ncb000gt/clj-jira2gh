# clj-jira2gh

Copies issues from jira over to github.

## Installation

`lein run` with the appropriate options.

## Usage

    $ lein run [args]

    or

    $ java -jar clj-jira2gh-0.1.0-standalone.jar [args]

## Options

copy|close [jira_prefix] [jira_user] [jira_password] [jira_project] [number of issues] [github_username] [github_issue] [github_repo]

Copy will copy issues over, close will close all issues in the repository.

## License

Copyright © 2012 Nick Campbell 

MIT

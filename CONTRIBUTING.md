# How to contribute

Before we get started, thank you for considering contributing to this project. 🙇🏿

There are multiple ways you can contribute:

* Code
* Bug reports
* Translation

Independently of how you'd like to contribute, please make sure you read and comply with the [Code of Conduct](CODE_OF_CONDUCT.md).

## Code

If you are a first time contributor kindly check the [good first issue](https://github.com/addhen/kanalytics/issues?q=is%3Aissue+is%3Aopen+label%3A%22good+first+issue%22) label for issues that are easy to fix and to get you started.
If you can't find any issue that you can work on, please feel free to create a [new issue](https://github.com/addhen/kanalytics/issues/new) and will get back to you.


### Building the project

The project is built with Gradle. Given it is multiplatform, you can build it for the JVM and Native.

To build the project use

**Android**

`./gradlew assemble bundle`

**iOS**

Open the `sample/ios/iosApp` project in Xcode. Then [build and run](https://developer.apple.com/documentation/xcode/building-and-running-an-app) it from Xcode.

Run to see all supported tasks:
`./gradlew tasks --all`


#### Importing into Android Studio or IntelliJ IDEA

To import into Android Studio or IntelliJ IDEA, just open up the `root` of the project folder. Android Studio or IntelliJ IDEA should automatically detect
that it is a Gradle project and import it. It's important that you make sure that all building and test operations
are delegated to Gradle under [Gradle Settings](https://www.jetbrains.com/help/idea/gradle-settings.html).

**Note**: You may need to install the pre-commit hook to ensure that the code style is consistent. To do this, run `./install_hooks.sh`.

### Pull Requests

Contributions are made using Github [pull requests](https://help.github.com/en/articles/about-pull-requests):

1. [Fork](https://github.com/addhen/kanalytics/fork) the repository and work on your fork.
2. [Create](https://github.com/addhen/kanalytics/compare) a new PR with a request to merge to the **main** branch.
3. Fill the PR template with all the necessary required info.
4. Make sure any code contributed is covered by tests and no existing tests are broken.
5. Run `./gradlew spotlessCheck --stacktrace` to ensure the code style is consistent.

**Note**: All PRs should have an associated ticket.


### Commit messages

* Commit messages should be written in English
* They should be written in present tense using imperative mood ("Fix" instead of "Fixes", "Improve" instead of "Improved").

See [How to Write a Git Commit Message](https://chris.beams.io/posts/git-commit/)

## Bug Reports

Please use [Github Issues](https://github.com/addhen/kanalytics/issues/new/choose) to submit bug reports.
Before doing so however, please make sure to search for an existing issues to avoid reporting duplicates.


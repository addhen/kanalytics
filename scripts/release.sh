#!/usr/bin/env bash

set -exo pipefail

# This script is used to publish a new version of the library.
# It takes two arguments: the new version and the new snapshot version.
# If the snapshot version is not provided, it will use the current snapshot version.
# Example: ./scripts/release.sh 1.0.0 1.0.1-SNAPSHOT

# Gets the current version of the library
function getVersion() {
  ./gradlew -q --no-configuration-cache  printVersionName
}

NEW_VERSION=$1
NEW_SNAPSHOT_VERSION=$2
CUR_SNAPSHOT_VERSION=$(getVersion)

if [ -z "$NEW_SNAPSHOT_VERSION" ]; then
  # If no snapshot version was provided, use the current value
  NEW_SNAPSHOT_VERSION=$CUR_SNAPSHOT_VERSION
fi

echo "Publishing v$NEW_VERSION"

# Prepare release
sed -i.bak "s/${CUR_SNAPSHOT_VERSION}/${NEW_VERSION}/g" gradle/libs.versions.toml
git add gradle/libs.versions.toml
echo "Prepare for release v$NEW_VERSION"
git commit -m "Prepare for release v$NEW_VERSION"

# Sanity check
./gradlew spotlessCheck --no-configuration-cache && ./gradlew lint --stacktrace --no-configuration-cache

# Add git tag
echo "Add new version v$NEW_VERSION"
git tag "v$NEW_VERSION"
# Prepare next snapshot
echo "Setting next snapshot version $NEW_SNAPSHOT_VERSION"
sed -i.bak "s/${NEW_VERSION}/${NEW_SNAPSHOT_VERSION}/g" gradle/libs.versions.toml
git add gradle/libs.versions.toml
git commit -m "Prepare next development version"

# Remove the backup file from sed edits
rm gradle/libs.versions.toml.bak

# Push it all up
git push && git push --tags

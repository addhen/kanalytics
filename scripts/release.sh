#!/usr/bin/env bash

set -exo pipefail

# This script is used to publish a new version of the library.
# It takes two arguments: the new version and the new snapshot version.
# If the snapshot version is not provided, it will use the current snapshot version.
# Example: ./scripts/release.sh 1.0.0 1.0.1-SNAPSHOT

# Gets the current version of the library from the kanalytics entry in the toml file
function getKanalyticsVersion() {
  # Look for a line that starts with "kanalytics" followed by "=" and extract the version
  grep -E "^kanalytics[ ]*=[ ]*\"?[0-9]" gradle/libs.versions.toml | head -n 1 | sed -E 's/kanalytics[ ]*=[ ]*"?([^"]+)"?.*/\1/'
}

# Determines the format of the kanalytics line (with or without quotes, spaces)
function getKanalyticsFormat() {
  # Look for a line that starts with "kanalytics" followed by "=" and a version number
  grep -E "^kanalytics[ ]*=[ ]*\"?[0-9]" gradle/libs.versions.toml | head -n 1
}

NEW_VERSION=$1
NEW_SNAPSHOT_VERSION=$2
CUR_SNAPSHOT_VERSION=$(getKanalyticsVersion)
KANALYTICS_LINE=$(getKanalyticsFormat)

# Input validation: Ensure NEW_VERSION is provided
if [ -z "$NEW_VERSION" ]; then
  echo "Error: NEW_VERSION argument is required."
  echo "Usage: $0 <NEW_VERSION> [NEW_SNAPSHOT_VERSION]"
  exit 1
fi

if [ -z "$NEW_SNAPSHOT_VERSION" ]; then
  # If no snapshot version was provided, use the current value
  NEW_SNAPSHOT_VERSION=$CUR_SNAPSHOT_VERSION
fi

echo "Publishing v$NEW_VERSION"

# Check if the version is quoted in the file
if [[ "$KANALYTICS_LINE" == *"\""* ]]; then
  # Version is quoted
  sed -i.bak -E "s/(kanalytics[ ]*=[ ]*\"?)${CUR_SNAPSHOT_VERSION}(\"?)/\1${NEW_VERSION}\2/g" gradle/libs.versions.toml
else
  # Version is not quoted
  sed -i.bak -E "s/(kanalytics[ ]*=[ ]*)${CUR_SNAPSHOT_VERSION}/\1${NEW_VERSION}/g" gradle/libs.versions.toml
fi

git add gradle/libs.versions.toml
echo "Prepare for release v$NEW_VERSION"
git commit -m "Prepare for release v$NEW_VERSION"

# Sanity check
./gradlew spotlessCheck --no-configuration-cache && ./gradlew lint --stacktrace --no-configuration-cache

# Add git tag
echo "Add new version v$NEW_VERSION"
git tag "v$NEW_VERSION" -m "Release v$NEW_VERSION"
# Prepare next snapshot

echo "Setting next snapshot version $NEW_SNAPSHOT_VERSION"
# Check if the version is quoted in the file
if [[ "$KANALYTICS_LINE" == *"\""* ]]; then
  # Version is quoted
  sed -i.bak -E "s/(kanalytics[ ]*=[ ]*\"?)${NEW_VERSION}(\"?)/\1${NEW_SNAPSHOT_VERSION}\2/g" gradle/libs.versions.toml
else
  # Version is not quoted
  sed -i.bak -E "s/(kanalytics[ ]*=[ ]*)${NEW_VERSION}/\1${NEW_SNAPSHOT_VERSION}/g" gradle/libs.versions.toml
fi

git add gradle/libs.versions.toml
git commit -m "Prepare next development version"

# Remove the backup file from sed edits
rm gradle/libs.versions.toml.bak

# Push it all up
git push && git push --tags

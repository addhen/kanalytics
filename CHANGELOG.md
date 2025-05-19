## [Unreleased]

### Added
- Migrated from using OSSRH to Sonatype Central Portal(#64).

### Documentation
- Update documentation with Sonatype Central Portal URLs.

### Dependencies
- Bump sqldelight to `v2.1.0` (#62)
- Bump kotlinx collections immutable to `v0.4.0` (#61)
- Bump kotlin to `v2.1.21` (#60)
- Bump agp to `v8.10.0` (#58)
- Bump compose multiplatform to `v1.8.0` (#57)
- Bump gradle to `v8.14` (#56)
- Bump kotlinx coroutines to `v1.10.2` (#53)
- Bump kotlinx serialization json to `v1.8.1` (#51)
- Bump androidx benchmark marco junit4 to `v1.3.4` (#50)
- Bump jsontree androidx baseline profile to `v1.3.4`  (#49)
- Bump dev.mokkery to `v2.7.2` (#47)

## [1.2.0] - 2025-03-23

### Added

- Add ability to toggle sending of analytics events to tracking tools. Useful for GDPR compliance.
- Add support for sending user properties to analytics trackers.

### Fixed
- Fix the issue of custom fonts not loading on iOS devices and instead loading the default system font(#63).

### Documentation
- Add multi-version support to the project documentation based on the library version. Now you should be able to select the documentation for the library version.

### Dependencies
- Bump data-table-material3 to `v0.11.3`
- Bump activity-compose to `v1.10.1`
- Bump jsontree to `v2.5.0`
- Bump agp to `v8.9.0`
- Bump dev.mokkery to `v2.7.1`

## [1.1.0] - 2025-02-22

### Changed

- Made `KAnalyticsEvent#properties` a `Map` instead of a `MutableMap` -- Breaking change.
- Updated `KAnalyticsEvent#copy` function's `parameters` name to `properties` of type `Map` instead of `MutableMap` -- Breaking change.
- Renamed `KAnalyticsEvent#addParameter` to `KAnalyticsEvent#addProperty` -- Breaking change.
- Renamed `KAnalyticsEvent#addParameters` to `KAnalyticsEvent#addProperties` -- Breaking change.


## [1.0.0] - 2025-02-21

### Added
- Initial release of KAnalytics core library.
- Initial release of KAnalytics Viewer app.

See documentation for more details: https://addhen.github.io/kanalytics/

[unreleased]: https://github.com/addhen/kanalytics/compare/v1.1.0...HEAD
[1.0.0]: https://github.com/addhen/kanalytics/releases/tag/v1.0.0
[1.1.0]: https://github.com/addhen/kanalytics/releases/tag/v1.1.0

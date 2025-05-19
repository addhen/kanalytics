## [Unreleased]

### Added

- Add ability to toggle sending of analytics events to tracking tools. Useful for GDPR compliance.
- Add support for sending user properties to analytics trackers.

### Documentation
- Add multi-version support to the project documentation based on the library version. Now you should be able to select the documentation for the library version.
- Update documentation with Sonatype Central Portal URLs(#64).

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

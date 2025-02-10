# Copyright 2024, Addhen Ltd and the kanalytics project contributors
# SPDX-License-Identifier: Apache-2.0

# Makefile to contain all the most frequently used commands. For more tasks use the
# `./gradlew` command directly.

# Note: Makefiles must be indented using TABs and not spaces or make will fail.

.PHONY: buildDebug
buildDebug:	# Assemble the debug app (without linting)
	@./gradlew assembleDebug -x lint

.PHONY: spotlessCheck
spotlessCheck:	# Run spotless checks
	@./gradlew spotlessCheck --stacktrace

.PHONY: spotlessApply
spotlessApply:	# Run spotless apply to fix code style
	@./gradlew spotlessApply --stacktrace

.PHONY: clean
clean:	# Do a clean build
	@./gradlew clean

.PHONY: build
build:	# Assemble the project (without linting)
	@./gradlew assemble bundle -x lint

.PHONY: lint
lint:	# Run lint checks
	@./gradlew lint

.PHONY: test
tests:	# Run all unit tests without linting
	@./gradlew test -x lint

.PHONY: help
help:		# Display this help
	@grep -Eh "^[a-zA-Z]+:.+# " $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.+# "}; {printf "%-20s %s\n", $$1, $$2}'

.PHONY: housekeeping
housekeeping:			# Perform some git housekeeping
	git fsck
	git gc --aggressive
	git remote update --prune

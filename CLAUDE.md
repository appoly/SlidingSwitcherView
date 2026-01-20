# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

SlidingSwitcherView is an Android library providing an animated two-option sliding switch view. It uses MotionLayout for smooth transitions between states. Distributed via JitPack.

## Build Commands

```bash
# Build the library
./gradlew :SlidingSwitcherView:build

# Run unit tests
./gradlew :SlidingSwitcherView:test

# Run instrumented tests
./gradlew :SlidingSwitcherView:connectedAndroidTest

# Clean build
./gradlew clean

# Assemble release AAR
./gradlew :SlidingSwitcherView:assembleRelease
```

## Architecture

This is a single-module Android library (`SlidingSwitcherView/`).

### Core Components

- **SlidingSwitcherView** (`SlidingSwitcherView.kt`) - Main custom view extending `MotionLayout`. Handles state management, animations, and listener callbacks.
- **SwitchSate** (`SwitchSate.kt`) - Enum defining `Start` and `End` states.
- **Extensions** (`SlidingSwicherViewExtensions.kt`) - Lambda-based listener extension function.

### Layout System

The view uses MotionLayout with:
- `sliding_switcher_view_layout.xml` - Data binding layout with two `CheckedTextView` tabs and a sliding background `View`
- `sliding_switcher_scene.xml` - MotionScene defining start/end constraint sets and transitions

### Custom Attributes

Defined in `res/values/attrs.xml`:
- `startItemText` - Text for the start (left) option
- `endItemText` - Text for the end (right) option

## JitPack Distribution

The library is published via JitPack. The `jitpack.yml` configures JDK 17 for builds. Group ID: `com.github.appoly`
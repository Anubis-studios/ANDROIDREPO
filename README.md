# CREATOR SURGE STUDIOS MEGASYSTEM v2.0

> **System Spec & Operating Core for Creator Surge Studios AI Megasystem**

## Overview

**Creator Surge Studios Megasystem v2.0** acts as a root super-agent orchestrating tools, sub-agents, memories, and model routing across development, content creation, and deep research domains.

### Core Architecture

```
                      +----------------------------------+
                      |   CREATOR SURGE STUDIOS MEGASYS  |
                      |          MEGASYSTEM v2.0         |
                      +----------------------------------+
                                       |
       +-------------------------------+-------------------------------+
       |                               |                               |
       v                               v                               v
+--------------+               +---------------+               +---------------+
|   MODE_DEV   |               | MODE_CREATOR  |               | MODE_RESEARCH |
| Code, Infra, |               | Worlds, Sandbox|               | Synthesis,    |
| Architecture |               | & UX Design   |               | Analysis      |
+--------------+               +---------------+               +---------------+
       |                               |                               |
       +-------------------------------+-------------------------------+
                                       |
                                       v
                     +-----------------------------------+
                     |        MODE_ORCHESTRATOR          |
                     | Multi-Agent Routing & Execution  |
                     +-----------------------------------+
```

---

## Modes of Operation

1. **`MODE_DEV`**: Architecture design, Kotlin code implementation, infrastructure, API contracts, engine development, and tool wiring.
2. **`MODE_CREATOR`**: Interactive 2D Isometric Sandbox World view, Jetpack Compose UI/UX, and creative assets.
3. **`MODE_RESEARCH`**: Comparative evaluation, technical synthesis, deep reasoning, and virtual memory logs.
4. **`MODE_ORCHESTRATOR`**: Multi-agent task decomposition, workflow routing, and sub-agent status monitoring (`AGENT_ARCHITECT`, `AGENT_ENGINEER`, `AGENT_DESIGNER`, `AGENT_RESEARCHER`).

---

## Key App Features (Android Jetpack Compose)

- **System Mode Switcher**: Toggle operational modes (`DEV`, `CREATOR`, `RESEARCH`, `ORCHESTRATOR`).
- **Multi-Agent Status Monitor**: View real-time status and tasks for virtual agents.
- **Virtual Memory Log Viewer**: Inspect `SESSION_MEMORY`, `PROJECT_MEMORY`, and `KNOWLEDGE_MEMORY`.
- **2D Isometric Sandbox Canvas**: Interactive diamond grid map renderer in Compose with tile placement (Grass, Water, Stone, Sand, Structure), avatar markers, and pan/zoom viewport gestures.

---

## Build & Run Instructions

To build the Android application and execute test suites:

```bash
# Compile and build Debug APK and App Bundle
./gradlew test assembleDebug bundleDebug
```

Outputs:
- Debug APK: `app/build/outputs/apk/debug/app-debug.apk`
- Android App Bundle (AAB): `app/build/outputs/bundle/debug/app-debug.aab`

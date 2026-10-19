# AGENTS.md - Creator Surge Studios Megasystem v2.0 Guidelines

This document specifies operational standards, guidelines, and verification rules for AI agents working within the Creator Surge Studios repository.

---

## Identity & Core Behaviour (`[CREATOR_SURGE_STUDIOS_AI_MEGASYSTEM_V2_0]`)

- **Identity**: Operate as a constructive, expert teammate rather than a generic assistant.
- **Goal**: Produce concrete, deterministic artifacts (code, specifications, architecture diagrams, Jetpack Compose layouts, configs).
- **Core Directives**: Deep reasoning, clear structure, minimal redundancy, strict adherence to safety and non-harmful behavior.

---

## Operational Modes

1. **`MODE_DEV`**: Architecture, code implementation, infrastructure, API contracts, engine development, and MCP wiring.
2. **`MODE_CREATOR`**: Game mechanics, isometric 2D world states, Jetpack Compose UI/UX, and creative assets.
3. **`MODE_RESEARCH`**: Structured technical analysis, comparative evaluation, and knowledge synthesis.
4. **`MODE_ORCHESTRATOR`**: Multi-agent task decomposition, workflow routing, and sub-agent reconciliation (`AGENT_ARCHITECT`, `AGENT_ENGINEER`, `AGENT_DESIGNER`, `AGENT_RESEARCHER`).

---

## Task Execution & Virtual Memory Rules

1. **Autonomous Planning**:
   - Every non-trivial goal must be broken into 3–10 distinct tasks with defined purpose, required tools, and expected output.
2. **Proactive Verification**:
   - For any file creation or modification, verify the change using read-only tools (`read_file` or `list_files`) before marking the step as complete.
   - Do not edit build artifacts directly; modify source files and execute build scripts as needed.
3. **Virtual Memory Logging**:
   - Maintain `SESSION_MEMORY` (active goals/decisions), `PROJECT_MEMORY` (long-term specs), and `KNOWLEDGE_MEMORY` (reusable patterns).
   - At the completion of major feature iterations or architectural sessions, emit a `PROJECT_MEMORY_UPDATE` summary block:
     ```markdown
     - PROJECT_MEMORY_UPDATE:
       - PROJECT: Surge Open World Android
       - DECISIONS: Native Kotlin + Jetpack Compose; 2D Isometric Engine; WebSocket backend.
       - NEW: Interactive Sandbox Canvas, Mode Switcher Dashboard.
       - OPEN: Multi-user real-time tile sync, avatar animation pipeline.
     ```

---

## Android & Jetpack Compose Development Standards

- **Compose UI**: Use Jetpack Compose Material3 for all user interface components.
- **State Management**: Use `remember`, `mutableStateOf`, and `mutableStateMapOf` for UI and Canvas state management.
- **Performance**: Keep canvas rendering lightweight and leverage `DrawScope` extension functions for custom graphics (such as 2D isometric diamond tiles).
- **Build Verification**: Verify changes by executing `./gradlew test assembleDebug bundleDebug` in a bash session.

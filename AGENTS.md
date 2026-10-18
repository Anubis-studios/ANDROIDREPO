# AGENTS.md - Creator Surge Studios Megasystem Guidelines

This document specifies operational standards, guidelines, and verification rules for AI agents working within the Creator Surge Studios repository.

---

## Agent Operational Directives

### 1. Identity & Tone
- Operate as a constructive, expert teammate rather than a generic virtual assistant.
- Focus on producing concrete, deterministic artifacts (code, specifications, architecture diagrams, configurations).

### 2. Execution Modes
- **`MODE_DEV`**: Prioritize clean code, robust architecture, clear interfaces, and explicit configuration management.
- **`MODE_CREATOR`**: Focus on game mechanics, isometric world state definitions, Jetpack Compose layouts, and user experience workflows.
- **`MODE_RESEARCH`**: Provide structured technical comparisons, trade-off analysis, and synthesized takeaways.
- **`MODE_ORCHESTRATOR`**: Break complex goals down into sequential/parallel task lists and reconcile agent outputs.

---

## Task Execution & Verification Rules

1. **Autonomous Planning**:
   - Every non-trivial goal must be broken into 3–10 distinct tasks with defined purpose, required tools, and expected output.
2. **Proactive Verification**:
   - For any file creation or modification, verify the change using read-only tools (`read_file` or `list_files`) before marking the step as complete.
   - Do not edit build artifacts directly; modify source files and build as needed.
3. **Memory Logging**:
   - At the completion of major feature iterations or architectural sessions, emit a `PROJECT_MEMORY_UPDATE` summary block detailing decisions made, new artifacts generated, and open questions.

---

## Pre-commit Verification Checklist

Before finalizing any changes:
- Ensure all created or modified files exist and are verified.
- Confirm markdown files adhere to standard formatting conventions.
- Check that no stray temporary files or artifacts remain in the workspace.

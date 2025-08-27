# Project Guidelines

Purpose
- This project is a Java/Quarkus re-implementation inspired by the Python-based mylar3 project: https://github.com/mylar3/mylar3
- Goal: deliver a modern, type-safe, container-friendly service with feature parity for core workflows while following idiomatic Java/Quarkus practices.

Scope and Goals
- Core features we aim to mirror from mylar3 (progressively):
  - Metadata retrieval and library enrichment via ComicVine.
  - Series/volume/issue tracking and scheduled refresh jobs.
  - Archive handling (CBZ/ZIP, CBR/RAR) for basic extraction and processing.
- Non-goals (for now):
  - 1:1 UI parity or Python-specific plugin ecosystems.
  - Direct porting of code; we focus on equivalent behavior with a Java-first design.

Architecture Overview
- Framework: Quarkus (fast startup, low memory, native option).
- Key modules in this repo:
  - Entities: Plain DTOs for ComicVine models (e.g., ComicVineIssue, ComicVineVolume, etc.).
  - Integrations: REST clients (e.g., ComicVineClient) using the Quarkus REST Client.
  - Services: Domain services (e.g., ComicVineService) and scheduled tasks (ScheduledTaskService).
  - Utilities: Helpers for extraction (Zip, Rar) and image conversion (Webp).
- Persistence and Migrations:
  - Hibernate ORM with Flyway migrations (quarkus.flyway.migrate-at-start=true).
  - Initial schema under src/main/resources/db/migration.
- Configuration (examples from application.properties):
  - quarkus.rest-client.user-agent=Watcher
  - quarkus.rest-client.comicvine.url=https://comicvine.gamespot.com
  - quarkus.rest-client.comicvine.follow-redirects=true
  - quarkus.datasource.devservices.port=5234 (dev services)
- Scheduling: Use Quarkus scheduler for recurring sync and housekeeping tasks.

Coding Standards
- Language: Java (match toolchain configured in Gradle).
- Style: Keep DTOs simple (public fields for external API DTOs are acceptable), use constructors/builders for internal domain models when introduced.
- Null-safety: Prefer Optional where appropriate for internal APIs; validate external inputs.
- Logging: Use structured, leveled logging; avoid leaking secrets (API keys).
- Error handling: Prefer explicit exceptions with actionable messages; fail fast on misconfiguration.

Testing
- Unit tests for services/utilities; integration tests for REST client and scheduling.
- Use testcontainers/dev services for DB-backed tests where needed.
- Add lightweight tests as features are added to ensure parity with mylar3 behaviors.

Contribution Workflow
- Issues should reference the mylar3 feature or behavior being targeted when relevant.
- For new features:
  - Propose design briefly (link the mylar3 counterpart if available).
  - Add/adjust Flyway migrations when schema changes are needed.
  - Include tests that verify behavior against expected outcomes.
- Coding hygiene:
  - Small, focused PRs; meaningful commit messages.
  - Keep configuration in application.properties/application.yaml and document new keys.

Attribution and Licensing
- mylar3 is the inspiration; do not copy code verbatim. Recreate logic in Java idioms.
- If behavior mirrors mylar3 closely, reference the original component/issue/PR in comments or commit messages where appropriate.

Roadmap Hints
- Expand providers incrementally (e.g., additional metadata or search providers) while maintaining clear abstractions in the integrations layer.
- Introduce a minimal REST API and/or UI layer after stabilizing core services.
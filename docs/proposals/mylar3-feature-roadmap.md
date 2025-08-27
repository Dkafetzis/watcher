# Mylar3 Parity Roadmap and Feature Proposals

Purpose: propose incremental, Java/Quarkus-native features inspired by mylar3 to reach core parity while keeping this service lightweight and container-friendly.

References
- mylar3: https://github.com/mylar3/mylar3
- Note: We mirror behavior, not code. Designs below follow Java/Quarkus idioms.

## Milestone 0 — Baseline (current)
- File detection from watch directory, move to library. ✓
- RAR→CBZ conversion and WEBP image conversion for ZIPs. ✓
- ComicVine client + scheduled issues fetch with logging. ✓

## Milestone 1 — Library Scanner & Metadata Enrichment (mylar3: library scan + metadata update)
Goal: detect series/volumes/issues from filenames, enrich via ComicVine, and persist.

Features
1. Filename parsing & normalizer (mylar3: parseinfo)
   - Extract series title, issue number, year, and optional variant.
   - Handle common patterns: Title 2021 #05 (of 6), Title v2 003, Title 003A.
   - Config: library.scanner.strict=true|false (leniency for unknown patterns).

2. ComicVine enrichment pipeline
   - Search by series title and year, select best matching volume.
   - Fetch issue details (credits, cover, date, story arc).
   - Cache volume/issue lookups (in DB) to avoid rate limits.
   - Config: comicvine.rate-limit.per-minute, comicvine.cache.ttl.

3. Persistence model (initial)
   - Tables: series, volume, issue, issue_file (join to ComicFile).
   - Migration: Flyway V1.1 add core tables with indexes on (series_name, year), (volume_cvid), (issue_cvid).

4. Scheduling
   - Nightly rescan job to fill missing metadata; retry failed lookups.
   - Backoff and partial updates to respect ComicVine limits.

APIs (internal for now)
- LibraryScannerService.scan(ComicFile) → ParsedInfo
- EnrichmentService.enrich(ParsedInfo) → IssueMetadata
- MetadataRepository upserts Series/Volume/Issue and links ComicFile.

Testing
- Unit tests for parser edge cases.
- Integration tests using WireMock for ComicVine search/issues.

## Milestone 2 — Series Tracking & Wanted Queue (mylar3: series monitoring)
Goal: allow users to “follow” series/volumes and maintain a wanted list of upcoming/missing issues.

Features
1. Followed Series
   - Entity: followed_series (volume_cvid, status=ACTIVE/PAUSED, added_at).
   - Service: TrackingService to refresh followed volumes.
   - Scheduled: weekly volume refresh to discover new issues.

2. Wanted Queue
   - Compute missing issues for followed volumes and mark as WANTED.
   - Emit events/logs for discovery; optional REST endpoint to list.

3. Basic REST API (Quarkus RESTEasy Reactive)
   - GET /api/series/followed
   - POST /api/series/follow (by volume id or search)
   - GET /api/wanted

Testing
- Service unit tests for wanted calculation.
- API test with Quarkus test profile and H2.

## Milestone 3 — Post-Processing Enhancements (mylar3: file ops)
Goal: configurable post-processing to standardize library and images.

Features
1. Rename & organize
   - Template: {Series} ({Year}) #{IssueNumber}{Variant} — {Title}
   - Move under library root: /Series (Year)/Series v{vol}/Series #000X.cbz
   - Config: library.organize.enabled, library.organize.template.

2. Image pipeline options
   - Toggle WEBP quality, max width, grayscale for digital.
   - Config: webp.quality=80, webp.max-width, webp.enabled=true.

3. Validation & repair
   - Verify archive integrity after zip; retry on failure.

## Milestone 4 — Providers & Extensibility (mylar3: multiple sources)
Goal: keep a clean abstraction to add more metadata sources later (without implementing them now).

Features
1. Provider abstraction
   - interface MetadataProvider with searchVolumes, getIssue, getVolume.
   - ComicVineProvider implements it using current client.
   - Config: metadata.provider=comicvine.

2. Rate limiting & caching
   - Simple in-memory cache per provider; optional Caffeine later.

## Milestone 5 — Housekeeping & Health
- Orphan detection (files without metadata) → queue for rescan.
- Failed job registry with reasons and nextRetry.
- Quarkus health endpoints tied to provider availability and disk checks.

## Configuration Keys (proposed)
- library.directory (exists)
- work.directory (exists)
- watch.directory (exists)
- library.scanner.strict=true|false
- comicvine.rate-limit.per-minute=30
- comicvine.cache.ttl=PT6H
- library.organize.enabled=false
- library.organize.template="{Series} ({Year}) #{Issue}{Variant} — {Title}"
- webp.enabled=true
- webp.quality=80

## Minimal Schema Additions (planned via Flyway)
- V1.1
  - series(id, name, year, publisher, cvid UNIQUE)
  - volume(id, series_id FK, name, start_year, cvid UNIQUE)
  - issue(id, volume_id FK, number, title, cover_date, cvid UNIQUE)
  - issue_file(issue_id FK, comic_file_id FK UNIQUE)
  - followed_series(id, volume_id FK, status, added_at)

## Implementation Notes
- Keep DTOs simple; internal domain entities to use constructors/builders.
- Avoid code duplication: add LibraryScannerService and EnrichmentService modules.
- Use Quarkus Scheduler for rescans; ensure SKIP concurrent execution.
- Add detailed logging with context (volume/issue IDs) but no secrets.

## Next Steps (short-term)
1. Add provider abstraction + ComicVineProvider impl.
2. Implement filename parser with tests.
3. Migrate schema V1.1 for series/volume/issue.
4. Wire enrichment hook into ScheduledTaskService.detectFiles.
5. Add minimal REST endpoints for followed series and wanted list.

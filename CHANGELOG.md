# Changelog

All notable changes to this project are documented in this file.

## [1.0.0] - 2026-03-31
### Added
- Configurable frontend API timeout via `VITE_API_TIMEOUT_MS` with safe fallback.
- Render deployment guidance and troubleshooting notes.
- Public backend health endpoint (`/api/health`) for deployment verification.
- Default seeded demo users for citizen/doctor login in deployed environments.

### Changed
- Default CORS origin list expanded for deployed frontend compatibility.
- Startup data initialization now supports deterministic demo login behavior.

### Notes
- This tag/version is considered the stable baseline for portfolio/resume showcase.

# Operations

## Health Probes
- Liveness: `/actuator/health/liveness`
- Readiness: `/actuator/health/readiness`

The readiness group includes `db` and readiness state. Liveness includes
only liveness state.

## Logging Profiles
- `application-dev.properties`: verbose logging for local diagnostics.
- `application-prod.properties`: reduced logging noise in production.

## Tracing
Tracing sampling rate can be controlled via `management.tracing.sampling.probability`
in each profile.

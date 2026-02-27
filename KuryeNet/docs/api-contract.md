# API Contract

This document describes the standard response envelope for KuryeNet APIs.

## Success Response
All successful responses return a `Result` or `DataResult` object.

Example (Result):
```json
{
  "success": true,
  "message": "Operation completed"
}
```

Example (DataResult):
```json
{
  "success": true,
  "message": "Items listed",
  "data": []
}
```

## Error Response
Validation and runtime errors are wrapped in `ErrorResult` or `ErrorDataResult`
with an `ApiError` payload.

Example:
```json
{
  "success": false,
  "message": "Failed",
  "data": {
    "timestamp": "25-02-2026 10:15:30",
    "status": "BAD_REQUEST",
    "message": "invalid input(s)",
    "errors": {
      "customerEmail": "must be a well-formed email address"
    },
    "details": null
  }
}
```

## Validation Rules
- `@Valid` is applied on request DTOs and parameters where needed.
- Validation errors are returned with `errors` map (field -> message).

## Versioning
All API endpoints support both `/api/*` and `/api/v1/*`.
The `/api/v1` prefix is a versioned alias for backward-compatible evolution.

## OpenAPI / Swagger
- Swagger UI: `/swagger-ui/index.html`
- OpenAPI JSON: `/v3/api-docs`

Security scheme:
- `bearerAuth` (HTTP Bearer JWT)

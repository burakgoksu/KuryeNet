# Data & Performance Notes

## Pagination Rules
- `pageNo` is 0-based and must be >= 0.
- `pageSize` must be between 1 and 200.

## Indexing Strategy
Indexes target the most common lookup/filter columns used by repository methods:
- emails, identities, order numbers
- foreign keys used in joins
- city/type/name filters

These are defined in `db/migration/V4__add_indexes.sql`.

# Configuration

## Environment Variables
- `DB_URL` (default: `jdbc:postgresql://localhost:5432/KuryeNet`)
- `DB_USERNAME` (default: `postgres`)
- `DB_PASSWORD` (default: `admin`)

- `JWT_SECRET`
- `JWT_ISSUER` (default: `kurye-net`)
- `JWT_AUDIENCE` (default: `kurye-net-api`)
- `JWT_ACCESS_MINUTES` (default: `1440`)
- `JWT_REFRESH_DAYS` (default: `7`)
- `JWT_CLOCK_SKEW_SECONDS` (default: `30`)

- `SECURITY_LOGIN_MAX_ATTEMPTS` (default: `5`)
- `SECURITY_LOGIN_LOCK_MINUTES` (default: `15`)
- `SECURITY_CORS_ALLOWED_ORIGINS` (default: `http://localhost:8081`)

- `API_BASE_PATH` (default: `/api`)
- `API_VERSIONED_BASE_PATH` (default: `/api/v1`)

- `APP_MESSAGING_ENABLED` (default: `false`)

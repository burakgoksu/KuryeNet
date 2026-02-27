# Test Strategy

## Profiles
- Tests run with the `test` profile by default.
- `src/test/resources/application-test.properties` configures H2 and disables Flyway.

## Local Commands
```bash
mvn -DskipTests=false test
```

## CI
GitHub Actions workflow runs `mvn test` on every push and pull request.

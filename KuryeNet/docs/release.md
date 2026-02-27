# Release Process

## Checklist
1. Run tests: `mvn -DskipTests=false test`
2. Review Flyway migrations under `src/main/resources/db/migration`
3. Build artifact: `mvn -DskipTests=false package`
4. Update version in `pom.xml` if needed
5. Verify `/actuator/health` and `/swagger-ui/index.html` in staging
6. Deploy to production

## CD Pipeline
Tagging a release triggers GitHub Actions to build and upload the JAR artifact.

Example:
```bash
git tag v1.0.0
git push --tags
```

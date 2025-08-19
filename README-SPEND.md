# Spend Analysis Module

Professional spend analytics for procurement and finance teams.

## Testing credentials

- Username: `analyst` Password: `password` (role ANALYST)
- Username: `approver` Password: `password` (role APPROVER)

## Run locally

```bash
mvn spring-boot:run
```

App: `http://localhost:8080`

## Web UI

- Spend Dashboard: `/spend`
- Analytics (cube): `/spend/analytics`
- Savings Tracking: `/spend/savings`
- Reporting (export): `/spend/reporting`

## REST API

Auth: HTTP Basic

- Compute analytics
  - GET `/spend?start=YYYY-MM-DD&end=YYYY-MM-DD`
- Query cube rows
  - GET `/spend/analytics?key=YEAR|CATEGORY|SUPPLIER`
- Create savings item
  - POST `/spend/savings` (form from UI)
- Export report
  - POST `/spend/reporting` JSON
  - Body example:
    ```json
    {"title":"Spend Report","format":"PDF","data":{"headers":["Supplier","Amount"],"rows":[{"Supplier":"SUP-1","Amount":1000},{"Supplier":"SUP-2","Amount":500}]}}
    ```

## cURL examples

```bash
curl -u analyst:password "http://localhost:8080/spend?start=2025-01-01&end=2025-12-31"

curl -u analyst:password "http://localhost:8080/spend/analytics?key=YEAR|SUPPLIER"

curl -u analyst:password -H "Content-Type: application/json" -d '{"title":"Spend Report","format":"XLSX","data":{"headers":["Supplier","Amount"],"rows":[{"Supplier":"SUP-1","Amount":1000}]}}' -o report.xlsx http://localhost:8080/spend/reporting
```

## Deployment

- Build: `mvn -DskipTests package`
- Jar: `target/erp-procurement-0.1.0-SNAPSHOT.jar`
- ENV Vars: `SERVER_PORT` (optional)

## Tests

- Unit tests in `src/test/java` (extend with real datasets)


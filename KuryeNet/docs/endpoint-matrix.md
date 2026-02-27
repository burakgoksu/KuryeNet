# Endpoint Matrix

This matrix documents the effective access rules enforced by
`com.gp.KuryeNet.core.config.WebSecurityConfiguration` and method-level
`@PreAuthorize` annotations.

Base paths (both are supported):
- `/api` (default)
- `/api/v1` (versioned alias)

Roles accepted by policy (both forms are supported):
- ADMIN / ROLE_ADMIN
- COURIER / ROLE_COURIER
- CUSTOMER / ROLE_CUSTOMER

## Public (permitAll)
| Method | Path pattern | Notes |
| --- | --- | --- |
| * | /auth/** | Registration endpoints |
| * | /authentication/** | Login/refresh endpoints |
| POST | /api/customers/add | Customer self-registration |
| * | /actuator/health | Actuator health |
| * | /actuator/info | Actuator info |
| * | /actuator/prometheus | Prometheus scrape |
| * | /actuator/metrics | Metrics list |
| * | /error | Framework error path |

## Customer or Admin
| Method | Path pattern | Policy |
| --- | --- | --- |
| * | /api/customers/** | `permissionPolicy.isCustomerOrAdmin` |
| * | /api/customersbaskets/** | `permissionPolicy.isCustomerOrAdmin` |
| * | /api/customersbaskets/add | `permissionPolicy.isCustomerOrAdmin` |

## Customer, Courier, or Admin
| Method | Path pattern | Policy |
| --- | --- | --- |
| * | /api/couriers/getCourierWithOrderDetails | `permissionPolicy.isCustomerCourierOrAdmin` |
| * | /api/orders/getByOrderNumber | `permissionPolicy.isCustomerCourierOrAdmin` |

## Courier or Admin (default for /api/**)
| Method | Path pattern | Policy |
| --- | --- | --- |
| * | /api/** | `permissionPolicy.isCourierOrAdmin` |

## Admin-only (method-level)
The following endpoints are further restricted by `@PreAuthorize` on the
controller methods. These are in addition to the path rules above.

| Method | Path | Notes |
| --- | --- | --- |
| DELETE | /api/addresses/delete | Soft delete |
| POST | /api/addresses/restore | Restore |
| GET | /api/addresses/deleted | List deleted |
| DELETE | /api/couriers/delete | Soft delete |
| POST | /api/couriers/restore | Restore |
| GET | /api/couriers/deleted | List deleted |
| DELETE | /api/customers/delete | Soft delete |
| POST | /api/customers/restore | Restore |
| GET | /api/customers/deleted | List deleted |
| POST | /api/customersbaskets/restore | Restore |
| GET | /api/customersbaskets/deleted | List deleted |
| DELETE | /api/orders/delete | Soft delete |
| POST | /api/orders/restore | Restore |
| GET | /api/orders/deleted | List deleted |
| DELETE | /api/providers/delete | Soft delete |
| POST | /api/providers/restore | Restore |
| GET | /api/providers/deleted | List deleted |
| DELETE | /api/vehicles/delete | Soft delete |
| POST | /api/vehicles/restore | Restore |
| GET | /api/vehicles/deleted | List deleted |
| * | /api/users/** | All user endpoints (class-level) |
| * | /api/roles/** | All role endpoints (class-level) |
| * | /api/usersroles/** | All user-role endpoints (class-level) |

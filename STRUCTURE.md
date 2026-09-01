[Magyar](./STRUCTURE.hu.md) | **English**

# idopontfoglalo – Target Folder Structure

> This is a **target picture** of what the project will look like once every module is done.
> As of 2026-08-30, the project is being migrated from a layer-based structure
> (`naptar/entity`, `naptar/repository`, ...) to a **feature-based** structure, one
> sub-package per entity/aggregate, with `Entity` suffix on entity classes.
> Only `naptar/szolgaltatas` is in progress; everything else is still a plan.

```
saas-idopontfoglalo-java/
│
├── pom.xml
├── README.md
├── .gitignore
│
├── src/
│   ├── main/
│   │   ├── java/com/jantsee/idopontfoglalo/
│   │   │   ├── IdopontfoglaloApplication.java
│   │   │   │
│   │   │   ├── common/                              ← shared code across modules
│   │   │   │   ├── exception/
│   │   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   │   └── BusinessRuleException.java
│   │   │   │   ├── config/
│   │   │   │   │   ├── CorsConfig.java
│   │   │   │   │   ├── OpenApiConfig.java
│   │   │   │   │   └── SecurityConfig.java          ← once we re-add Security
│   │   │   │   └── dto/
│   │   │   │       └── ApiErrorResponse.java
│   │   │   │
│   │   │   ├── naptar/                              ← CALENDAR MODULE (feature-based)
│   │   │   │   ├── szolgaltatas/
│   │   │   │   │   ├── SzolgaltatasEntity.java
│   │   │   │   │   ├── SzolgaltatasRepository.java
│   │   │   │   │   ├── SzolgaltatasService.java
│   │   │   │   │   ├── SzolgaltatasController.java
│   │   │   │   │   ├── SzolgaltatasDto.java
│   │   │   │   │   └── SzolgaltatasMapper.java
│   │   │   │   │
│   │   │   │   ├── munkavallalo/
│   │   │   │   │   ├── MunkavallaloEntity.java
│   │   │   │   │   ├── MunkavallaloRepository.java
│   │   │   │   │   ├── MunkavallaloService.java
│   │   │   │   │   ├── MunkavallaloController.java
│   │   │   │   │   ├── MunkavallaloDto.java
│   │   │   │   │   └── MunkavallaloMapper.java
│   │   │   │   │
│   │   │   │   └── foglalas/
│   │   │   │       ├── FoglalasEntity.java
│   │   │   │       ├── FoglalasStatusz.java
│   │   │   │       ├── FoglalasRepository.java
│   │   │   │       ├── FoglalasService.java
│   │   │   │       ├── FoglalasController.java
│   │   │   │       ├── FoglalasLetrehozasDto.java
│   │   │   │       ├── FoglalasValaszDto.java
│   │   │   │       └── FoglalasMapper.java
│   │   │   │
│   │   │   ├── ugyfel/                              ← CUSTOMER DATA MODULE (feature-based)
│   │   │   │   ├── ugyfel/
│   │   │   │   │   ├── UgyfelEntity.java
│   │   │   │   │   ├── UgyfelRepository.java
│   │   │   │   │   ├── UgyfelService.java
│   │   │   │   │   ├── UgyfelController.java
│   │   │   │   │   ├── UgyfelDto.java
│   │   │   │   │   ├── UgyfelLetrehozasDto.java
│   │   │   │   │   └── UgyfelMapper.java
│   │   │   │   └── megjegyzes/
│   │   │   │       └── MegjegyzesEntity.java
│   │   │   │
│   │   │   ├── ertesites/                           ← NOTIFICATION MODULE
│   │   │   │   ├── service/
│   │   │   │   │   ├── NotificationService.java     ← interface
│   │   │   │   │   └── EmailNotificationService.java
│   │   │   │   ├── config/
│   │   │   │   │   └── MailConfig.java
│   │   │   │   └── template/
│   │   │   │       ├── foglalas-visszaigazolas.html
│   │   │   │       └── foglalas-emlekezteto.html
│   │   │   │
│   │   │   └── tranzakcio/                          ← TRANSACTION MODULE (feature-based)
│   │   │       ├── tranzakcio/
│   │   │       │   ├── TranzakcioEntity.java
│   │   │       │   ├── TranzakcioStatusz.java
│   │   │       │   ├── TranzakcioRepository.java
│   │   │       │   └── TranzakcioService.java        ← interface impl detail may move
│   │   │       ├── PaymentService.java                ← interface
│   │   │       ├── BarionPaymentService.java
│   │   │       └── config/
│   │   │           └── BarionConfig.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties               ← shared/base settings
│   │       ├── application-dev.properties            ← local development
│   │       ├── application-prod.properties            ← production
│   │       └── db/migration/                          ← Flyway migrations
│   │           ├── V1__create_szolgaltatasok_table.sql
│   │           ├── V2__create_munkavallalok_table.sql
│   │           ├── V3__create_ugyfelek_table.sql
│   │           ├── V4__create_foglalasok_table.sql
│   │           └── V5__create_tranzakciok_table.sql
│   │
│   └── test/
│       └── java/com/jantsee/idopontfoglalo/
│           ├── naptar/
│           │   ├── szolgaltatas/
│           │   │   ├── SzolgaltatasServiceTest.java
│           │   │   └── SzolgaltatasControllerTest.java
│           │   └── foglalas/
│           │       └── FoglalasServiceTest.java
│           ├── ugyfel/
│           │   └── ugyfel/
│           │       └── UgyfelServiceTest.java
│           └── IdopontfoglaloApplicationTests.java   ← basic Spring Boot smoke test
│
└── (optional, once we get there)
    ├── Dockerfile
    └── docker-compose.yml                             ← app + Postgres started together
```

## Package layout: feature-based, not layer-based

Inside each **module** (`naptar`, `ugyfel`, `ertesites`, `tranzakcio`), the code is grouped
**by feature/entity**, not by technical layer. So instead of one `service/` folder holding
every service in the module, each feature gets its own sub-package containing its entity,
repository, service, controller, DTOs, and mapper together.

Naming convention:
- Entity classes get an explicit `Entity` suffix (e.g. `SzolgaltatasEntity`, `MunkavallaloEntity`),
  to make it unmistakable at a glance which class is the JPA-mapped one, since it now sits
  in the same package as its DTOs, repository, etc.
- Repository, Service, Controller, DTO, Mapper keep their existing suffix convention
  (`SzolgaltatasRepository`, `SzolgaltatasService`, ...).

## Module boundary rule

A module may **only** call another module's `service` layer — never its `repository` or
entity directly. Example: `naptar.foglalas.FoglalasService` sends a confirmation through
`ertesites.NotificationService`, but never reaches into `ertesites`'s internal data structures.
This rule is unchanged by the feature-based repackaging — it applies **between modules**, not
between feature sub-packages of the same module.

## What this structure adds compared to the earlier (layer-based) map

1. **Feature-based packages within each module** – `szolgaltatas/`, `munkavallalo/`,
   `foglalas/`, etc., each self-contained, instead of module-wide `entity/`, `repository/`,
   `service/`, `controller/` folders.
2. **`Entity` suffix on entity classes** – needed now that the entity lives in the same
   package as its DTOs and mapper, to avoid ambiguity.
3. **Profile-based configuration** (`application-dev.properties`, `application-prod.properties`) –
   alongside the shared `application.properties`, environment-specific overrides.
4. **`src/test/java`** – the package structure mirrors `main`, feature by feature.
5. **A full DTO + Mapper pair for every entity** – once an entity has a relationship to
   another entity, the raw entity can no longer "leak" through the Controller.
6. **`ertesites/template/`** – email templates (e.g. with Thymeleaf) that
   `EmailNotificationService` fills in with concrete data.
7. **Docker (optional, at the end)** – app + Postgres started together with
   `docker-compose`, only relevant if we get that far.

## Current state (2026-08-30)

Files that actually exist (mid-migration to feature-based structure):
- `naptar/entity/Szolgaltatas.java` *(being migrated to `naptar/szolgaltatas/SzolgaltatasEntity.java`)*
- `naptar/repository/SzolgaltatasRepository.java` *(moving to `naptar/szolgaltatas/`)*
- `naptar/service/SzolgaltatasService.java` *(moving to `naptar/szolgaltatas/`)*
- `naptar/controller/SzolgaltatasController.java` *(moving to `naptar/szolgaltatas/`)*
- `common/exception/GlobalExceptionHandler.java`

Everything else in the structure above is still a plan.
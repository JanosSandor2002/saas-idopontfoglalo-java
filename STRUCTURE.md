[Magyar](./STRUCTURE.hu.md) | **English**

# idopontfoglalo – Target Folder Structure

> This is a **target picture** of what the project will look like once every module is done.
> As of 2026-08-30, only `naptar/entity`, `naptar/repository`, `naptar/service`,
> `naptar/controller` (for `SzolgaltatasEntity` only) and `common/exception` actually exist.
> Everything else will be filled in gradually as we go through the learning process.

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
│   │   │   ├── naptar/                              ← CALENDAR MODULE
│   │   │   │   ├── entity/
│   │   │   │   │   ├── Szolgaltatas.java
│   │   │   │   │   ├── Munkavallalo.java
│   │   │   │   │   ├── Foglalas.java
│   │   │   │   │   └── FoglalasStatusz.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── SzolgaltatasRepository.java
│   │   │   │   │   ├── MunkavallaloRepository.java
│   │   │   │   │   └── FoglalasRepository.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── SzolgaltatasDto.java
│   │   │   │   │   ├── MunkavallaloDto.java
│   │   │   │   │   ├── FoglalasLetrehozasDto.java
│   │   │   │   │   └── FoglalasValaszDto.java
│   │   │   │   ├── mapper/
│   │   │   │   │   ├── SzolgaltatasMapper.java
│   │   │   │   │   ├── MunkavallaloMapper.java
│   │   │   │   │   └── FoglalasMapper.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── SzolgaltatasService.java
│   │   │   │   │   ├── MunkavallaloService.java
│   │   │   │   │   └── FoglalasService.java
│   │   │   │   └── controller/
│   │   │   │       ├── SzolgaltatasController.java
│   │   │   │       ├── MunkavallaloController.java
│   │   │   │       └── FoglalasController.java
│   │   │   │
│   │   │   ├── ugyfel/                              ← CUSTOMER DATA MODULE
│   │   │   │   ├── entity/
│   │   │   │   │   ├── Ugyfel.java
│   │   │   │   │   └── Megjegyzes.java
│   │   │   │   ├── repository/
│   │   │   │   │   └── UgyfelRepository.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── UgyfelDto.java
│   │   │   │   │   └── UgyfelLetrehozasDto.java
│   │   │   │   ├── mapper/
│   │   │   │   │   └── UgyfelMapper.java
│   │   │   │   ├── service/
│   │   │   │   │   └── UgyfelService.java
│   │   │   │   └── controller/
│   │   │   │       └── UgyfelController.java
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
│   │   │   └── tranzakcio/                          ← TRANSACTION MODULE
│   │   │       ├── entity/
│   │   │       │   ├── Tranzakcio.java
│   │   │       │   └── TranzakcioStatusz.java
│   │   │       ├── repository/
│   │   │       │   └── TranzakcioRepository.java
│   │   │       ├── service/
│   │   │       │   ├── PaymentService.java          ← interface
│   │   │       │   └── BarionPaymentService.java
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
│           │   ├── service/
│           │   │   ├── SzolgaltatasServiceTest.java
│           │   │   └── FoglalasServiceTest.java
│           │   └── controller/
│           │       └── SzolgaltatasControllerTest.java
│           ├── ugyfel/
│           │   └── service/
│           │       └── UgyfelServiceTest.java
│           └── IdopontfoglaloApplicationTests.java   ← basic Spring Boot smoke test
│
└── (optional, once we get there)
    ├── Dockerfile
    └── docker-compose.yml                             ← app + Postgres started together
```

## Module boundary rule

A module may **only** call another module's `service` layer — never its `repository` or
entity directly. Example: `naptar.FoglalasService` sends a confirmation through
`ertesites.NotificationService`, but never reaches into `ertesites`'s internal data structures.

## What this structure adds compared to the earlier (naptar-only) map

1. **Profile-based configuration** (`application-dev.properties`, `application-prod.properties`) –
   alongside the shared `application.properties`, environment-specific overrides.
2. **`src/test/java`** – the package structure mirrors `main`, module by module.
3. **A full DTO + Mapper pair for every entity** – once an entity has a relationship to
   another entity, the raw entity can no longer "leak" through the Controller.
4. **`ertesites/template/`** – email templates (e.g. with Thymeleaf) that
   `EmailNotificationService` fills in with concrete data.
5. **Docker (optional, at the end)** – app + Postgres started together with
   `docker-compose`, only relevant if we get that far.

## Current state (2026-08-30)

Files that actually exist:
- `naptar/entity/Szolgaltatas.java`
- `naptar/repository/SzolgaltatasRepository.java`
- `naptar/service/SzolgaltatasService.java`
- `naptar/controller/SzolgaltatasController.java`
- `common/exception/GlobalExceptionHandler.java`

Everything else in the structure above is still a plan.
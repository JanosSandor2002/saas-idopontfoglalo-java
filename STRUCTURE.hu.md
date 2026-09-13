**Magyar** | [English](./STRUCTURE.md)

# idopontfoglalo – Cél-mappastruktúra

> Ez egy **célkép** arra, hogyan nézzen ki a projekt, amikor minden modul készen van.
> 2026.08.30 óta a projekt egy réteg-alapú struktúráról (`naptar/entity`, `naptar/repository`, ...)
> egy **feature-alapú** struktúrára migrál, entitásonként/aggregátumonként egy-egy almappával,
> `Entity` végződéssel az entitás-osztályokon.
> Jelenleg (2026.09.13-i állapot) a `naptar/szolgaltatas`, `naptar/munkavallalo`, `naptar/foglalas`
> és az `ugyfel/ugyfel` feature-ök készek el, a `common/exception` alapja megvan.

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
│   │   │   ├── common/                              ← modulokon átívelő, megosztott kód
│   │   │   │   ├── exception/
│   │   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   │   └── BusinessRuleException.java
│   │   │   │   ├── config/
│   │   │   │   │   ├── CorsConfig.java
│   │   │   │   │   ├── OpenApiConfig.java
│   │   │   │   │   └── SecurityConfig.java          ← ha visszatesszük
│   │   │   │   └── dto/
│   │   │   │       └── ApiErrorResponse.java
│   │   │   │
│   │   │   ├── naptar/                              ← NAPTÁR MODUL (feature-alapú)
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
│   │   │   ├── ugyfel/                              ← ÜGYFÉLADATOK MODUL (feature-alapú)
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
│   │   │   ├── ertesites/                           ← ÉRTESÍTÉS MODUL
│   │   │   │   ├── service/
│   │   │   │   │   ├── NotificationService.java     ← interfész
│   │   │   │   │   └── EmailNotificationService.java
│   │   │   │   ├── config/
│   │   │   │   │   └── MailConfig.java
│   │   │   │   └── template/
│   │   │   │       ├── foglalas-visszaigazolas.html
│   │   │   │       └── foglalas-emlekezteto.html
│   │   │   │
│   │   │   └── tranzakcio/                          ← TRANZAKCIÓ MODUL (feature-alapú)
│   │   │       ├── tranzakcio/
│   │   │       │   ├── TranzakcioEntity.java
│   │   │       │   ├── TranzakcioStatusz.java
│   │   │       │   ├── TranzakcioRepository.java
│   │   │       │   └── TranzakcioService.java        ← interfész-implementáció, később mozoghat
│   │   │       ├── PaymentService.java                ← interfész
│   │   │       ├── BarionPaymentService.java
│   │   │       └── config/
│   │   │           └── BarionConfig.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties               ← közös/alap beállítások (PostgreSQL, .env-ből)
│   │       ├── application-dev.properties            ← lokális fejlesztéshez
│   │       ├── application-prod.properties            ← élesítéshez
│   │       └── db/migration/                          ← Flyway migrációk
│   │           ├── V1__create_szolgaltatasok_table.sql
│   │           ├── V2__create_munkavallalok_table.sql
│   │           ├── V3__create_ugyfelek_table.sql
│   │           ├── V4__create_foglalasok_table.sql
│   │           └── V5__create_tranzakciok_table.sql
│   │
│   └── test/
│       ├── java/com/jantsee/idopontfoglalo/
│       │   ├── naptar/
│       │   │   ├── szolgaltatas/
│       │   │   │   ├── SzolgaltatasServiceTest.java
│       │   │   │   └── SzolgaltatasControllerTest.java
│       │   │   ├── munkavallalo/
│       │   │   │   └── MunkavallaloServiceTest.java
│       │   │   └── foglalas/
│       │   │       └── FoglalasServiceTest.java
│       │   ├── ugyfel/
│       │   │   └── ugyfel/
│       │   │       └── UgyfelServiceTest.java
│       │   └── IdopontfoglaloApplicationTests.java   ← Spring Boot alap smoke test
│       └── resources/
│           └── application.properties                ← teszt-felülírás: H2 in-memory adatbázis
│
└── (opcionális, ha idáig eljutunk)
    ├── Dockerfile
    └── docker-compose.yml                             ← app + Postgres együtt indítva
```

## Csomag-elrendezés: feature-alapú, nem réteg-alapú

Minden **modulon** (`naptar`, `ugyfel`, `ertesites`, `tranzakcio`) belül a kód
**feature/entitás szerint** van csoportosítva, nem technikai réteg szerint. Tehát egy
modulon belüli összes service-t tartalmazó `service/` mappa helyett minden feature
kap egy saját almappát, amiben együtt van az entitása, repository-ja, service-e,
controllere, DTO-i és mappere.

Elnevezési konvenció:
- Az entitás-osztályok explicit `Entity` végződést kapnak (pl. `SzolgaltatasEntity`,
  `MunkavallaloEntity`), hogy első pillantásra egyértelmű legyen, melyik osztály a
  JPA-mappelt osztály, mivel most már a DTO-jaival, repository-jával stb. egy csomagban van.
- A Repository, Service, Controller, DTO, Mapper megtartja a meglévő végződés-konvenciót
  (`SzolgaltatasRepository`, `SzolgaltatasService`, ...).

## Modulhatár-szabály

Egy modul **csak** a másik modul `service` rétegét hívhatja meg – sosem a `repository`-ját
vagy az entitását közvetlenül. Példa: a `naptar.foglalas.FoglalasService` visszaigazolást
küld az `ertesites.NotificationService`-en keresztül, de sosem nyúl bele az `ertesites`
modul belső adatszerkezetébe. Ez a szabály a feature-alapú átcsomagolással nem változik –
**modulok között** érvényes, nem ugyanazon modul feature-almappái között.

## Amit ez a struktúra hoz a korábbi (réteg-alapú) térképhez képest

1. **Feature-alapú csomagok minden modulon belül** – `szolgaltatas/`, `munkavallalo/`,
   `foglalas/` stb., mindegyik önmagában zárt, a modul-szintű `entity/`, `repository/`,
   `service/`, `controller/` mappák helyett.
2. **`Entity` végződés az entitás-osztályokon** – szükséges, mert az entitás most már
   a DTO-jával és mapperjével egy csomagban van, elkerülendő a félreértést.
3. **Profil-alapú konfiguráció** (`application-dev.properties`, `application-prod.properties`) –
   a közös `application.properties` mellett környezet-specifikus felülírások.
4. **`src/test/resources/application.properties`** – a `src/main/resources` PostgreSQL/`.env`
   alapú konfigurációjától eltérő, H2 in-memory adatbázist használó felülírás, kifejezetten
   a `@SpringBootTest`-es teszteknek (pl. `IdopontfoglaloApplicationTests`), mert a `.env`
   betöltés a `main()`-ben történik, ami teszt-futtatáskor nem fut le.
5. **`src/test/java`** – a package-struktúra tükrözi a `main`-t, feature-önként.
6. **Minden entitáshoz teljes DTO + Mapper pár** – amint egy entitásnak kapcsolata van
   más entitással, a nyers entitás nem "szökhet ki" a Controlleren keresztül.
7. **`ertesites/template/`** – e-mail sablonok (pl. Thymeleaf-fel), amikbe a
   `EmailNotificationService` tölti be a konkrét adatokat.
8. **Docker (opcionális, a végén)** – app + Postgres egyben indítva `docker-compose`-szal,
   csak akkor aktuális, ha idáig eljutunk.

## Jelenlegi állapot (2026.09.13)

Ténylegesen létező fájlok (feature-alapú struktúrára migrálva):
- `naptar/szolgaltatas/` – `SzolgaltatasEntity`, `SzolgaltatasRepository`, `SzolgaltatasService`,
  `SzolgaltatasController` (teszttel)
- `naptar/munkavallalo/` – `MunkavallaloEntity`, `MunkavallaloRepository`, `MunkavallaloService`,
  `MunkavallaloController` (teszttel)
- `naptar/foglalas/` – `FoglalasEntity`, `FoglalasStatusz`, `FoglalasRepository`,
  `FoglalasService`, `FoglalasController` (teszttel)
- `ugyfel/ugyfel/` – `UgyfelEntity`, `UgyfelRepository`, `UgyfelService`,
  `UgyfelController` (teszttel)
- `common/exception/GlobalExceptionHandler.java`
- `src/test/resources/application.properties` – H2 in-memory teszt-konfiguráció

Minden más a fenti struktúrából még csak terv.
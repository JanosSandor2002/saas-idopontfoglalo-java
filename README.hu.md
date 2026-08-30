**Magyar** | [English](./STRUCTURE.md)

# idopontfoglalo – Cél-mappastruktúra

> Ez egy **célkép** arra, hogyan nézzen ki a projekt, amikor minden modul készen van.
> 2026.08.30-i állapot szerint a projekt réteg szerinti struktúráról
> (`naptar/entity`, `naptar/repository`, ...) **feature szerinti** struktúrára áll át:
> entitásonként/aggregátumonként egy alcsomag, `Entity` végződéssel az entitás osztályokon.
> Jelenleg csak a `naptar/szolgaltatas` van átalakítás alatt, minden más még csak terv.

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
│   │   │   ├── naptar/                              ← NAPTÁR MODUL (feature szerint)
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
│   │   │   ├── ugyfel/                              ← ÜGYFÉLADATOK MODUL (feature szerint)
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
│   │   │   └── tranzakcio/                          ← TRANZAKCIÓ MODUL (feature szerint)
│   │   │       ├── tranzakcio/
│   │   │       │   ├── TranzakcioEntity.java
│   │   │       │   ├── TranzakcioStatusz.java
│   │   │       │   ├── TranzakcioRepository.java
│   │   │       │   └── TranzakcioService.java
│   │   │       ├── PaymentService.java                ← interfész
│   │   │       ├── BarionPaymentService.java
│   │   │       └── config/
│   │   │           └── BarionConfig.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties               ← közös/alap beállítások
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
│           └── IdopontfoglaloApplicationTests.java   ← Spring Boot alap smoke test
│
└── (opcionális, ha idáig eljutunk)
    ├── Dockerfile
    └── docker-compose.yml                             ← app + Postgres együtt indítva
```

## Csomagfelépítés: feature szerint, nem réteg szerint

Minden **modulon** (`naptar`, `ugyfel`, `ertesites`, `tranzakcio`) belül a kód
**feature/entitás szerint** van csoportosítva, nem technikai réteg szerint. Tehát ahelyett,
hogy egy `service/` mappa tartalmazná a modul összes service-ét, minden feature kap egy saját
alcsomagot, amiben együtt van az entitása, repository-ja, service-e, controllere, DTO-i és
mapperje.

Elnevezési konvenció:
- Az entitás osztályok explicit `Entity` végződést kapnak (pl. `SzolgaltatasEntity`,
  `MunkavallaloEntity`), hogy első pillantásra egyértelmű legyen, melyik osztály a
  JPA-mappelt entitás, mivel most már ugyanabban a csomagban van, mint a DTO-i és mappere.
- A Repository, Service, Controller, DTO, Mapper osztályok megtartják a meglévő
  végződési konvenciójukat (`SzolgaltatasRepository`, `SzolgaltatasService`, ...).

## Modulhatár-szabály

Egy modul **csak** a másik modul `service` rétegét hívhatja meg – sosem a `repository`-ját
vagy az entitását közvetlenül. Példa: a `naptar.foglalas.FoglalasService` visszaigazolást küld
a `ertesites.NotificationService`-en keresztül, de sosem nyúl bele az `ertesites` modul belső
adatszerkezetébe. Ez a szabály nem változik a feature szerinti átcsomagolással – **modulok
között** érvényes, nem ugyanazon modul feature-alcsomagjai között.

## Amit ez a struktúra hoz a korábbi (réteg szerinti) térképhez képest

1. **Feature szerinti csomagok modulonként belül** – `szolgaltatas/`, `munkavallalo/`,
   `foglalas/` stb., mindegyik önmagában zárt, a modul-szintű `entity/`, `repository/`,
   `service/`, `controller/` mappák helyett.
2. **`Entity` végződés az entitás osztályokon** – erre azért van szükség, mert az entitás
   most már ugyanabban a csomagban van, mint a DTO-i és mapperje, elkerülve a félreértést.
3. **Profil-alapú konfiguráció** (`application-dev.properties`, `application-prod.properties`) –
   a közös `application.properties` mellett környezet-specifikus felülírások.
4. **`src/test/java`** – a package-struktúra tükrözi a `main`-t, feature-önként.
5. **Minden entitáshoz teljes DTO + Mapper pár** – amint egy entitásnak kapcsolata van
   más entitással, a nyers entitás nem "szökhet ki" a Controlleren keresztül.
6. **`ertesites/template/`** – e-mail sablonok (pl. Thymeleaf-fel), amikbe a
   `EmailNotificationService` tölti be a konkrét adatokat.
7. **Docker (opcionális, a végén)** – app + Postgres egyben indítva `docker-compose`-szal,
   csak akkor aktuális, ha idáig eljutunk.

## Jelenlegi állapot (2026.08.30)

Ténylegesen létező fájlok (feature szerinti átalakítás közben):
- `naptar/entity/Szolgaltatas.java` *(átalakítás alatt: `naptar/szolgaltatas/SzolgaltatasEntity.java`)*
- `naptar/repository/SzolgaltatasRepository.java` *(költözik: `naptar/szolgaltatas/`)*
- `naptar/service/SzolgaltatasService.java` *(költözik: `naptar/szolgaltatas/`)*
- `naptar/controller/SzolgaltatasController.java` *(költözik: `naptar/szolgaltatas/`)*
- `common/exception/GlobalExceptionHandler.java`

Minden más a fenti struktúrából még csak terv.
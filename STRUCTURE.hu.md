**Magyar** | [English](./STRUCTURE.md)

# idopontfoglalo – Cél-mappastruktúra

> Ez egy **célkép** arra, hogyan nézzen ki a projekt, amikor minden modul készen van.
> Jelenleg (2026.08.30-i állapot) csak a `naptar/entity`, `naptar/repository`, `naptar/service`,
> `naptar/controller` (csak `Szolgaltatas`-hoz) és a `common/exception` létezik ténylegesen.
> Mindent fokozatosan töltünk fel, ahogy haladunk a tanulásban.

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
│   │   │   ├── naptar/                              ← NAPTÁR MODUL
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
│   │   │   ├── ugyfel/                              ← ÜGYFÉLADATOK MODUL
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
│   │   │   └── tranzakcio/                          ← TRANZAKCIÓ MODUL
│   │   │       ├── entity/
│   │   │       │   ├── Tranzakcio.java
│   │   │       │   └── TranzakcioStatusz.java
│   │   │       ├── repository/
│   │   │       │   └── TranzakcioRepository.java
│   │   │       ├── service/
│   │   │       │   ├── PaymentService.java          ← interfész
│   │   │       │   └── BarionPaymentService.java
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
│           │   ├── service/
│           │   │   ├── SzolgaltatasServiceTest.java
│           │   │   └── FoglalasServiceTest.java
│           │   └── controller/
│           │       └── SzolgaltatasControllerTest.java
│           ├── ugyfel/
│           │   └── service/
│           │       └── UgyfelServiceTest.java
│           └── IdopontfoglaloApplicationTests.java   ← Spring Boot alap smoke test
│
└── (opcionális, ha idáig eljutunk)
    ├── Dockerfile
    └── docker-compose.yml                             ← app + Postgres együtt indítva
```

## Modulhatár-szabály

Egy modul **csak** a másik modul `service` rétegét hívhatja meg – sosem a `repository`-ját
vagy az entitását közvetlenül. Példa: a `naptar.FoglalasService` visszaigazolást küld a
`ertesites.NotificationService`-en keresztül, de sosem nyúl bele az `ertesites` modul
belső adatszerkezetébe.

## Amit ez a struktúra hoz a korábbi (csak `naptar`-t tartalmazó) térképhez képest

1. **Profil-alapú konfiguráció** (`application-dev.properties`, `application-prod.properties`) –
   a közös `application.properties` mellett környezet-specifikus felülírások.
2. **`src/test/java`** – a package-struktúra tükrözi a `main`-t, modulonként.
3. **Minden entitáshoz teljes DTO + Mapper pár** – amint egy entitásnak kapcsolata van
   más entitással, a nyers entitás nem "szökhet ki" a Controlleren keresztül.
4. **`ertesites/template/`** – e-mail sablonok (pl. Thymeleaf-fel), amikbe a
   `EmailNotificationService` tölti be a konkrét adatokat.
5. **Docker (opcionális, a végén)** – app + Postgres egyben indítva `docker-compose`-szal,
   csak akkor aktuális, ha idáig eljutunk.

## Jelenlegi állapot (2026.08.30)

Ténylegesen létező fájlok:
- `naptar/entity/Szolgaltatas.java`
- `naptar/repository/SzolgaltatasRepository.java`
- `naptar/service/SzolgaltatasService.java`
- `naptar/controller/SzolgaltatasController.java`
- `common/exception/GlobalExceptionHandler.java`

Minden más a fenti struktúrából még csak terv.
[🇬🇧 English](README.md) | 🇭🇺 Magyar

# idopontfoglalo

Egy Spring Boot backend időpontfoglaló rendszerhez. A pontos domain/felhasználási terület (pl. szalon, rendelő, általános célú foglalás) még nincs eldöntve — a projekt jelenleg az alap foglalási funkcionalitás és architektúra felépítésére fókuszál.

> **Állapot: korai fázis / folyamatban.** A projekt a legelső fázisában van — az entitások és az alapstruktúra még kialakítás alatt állnak.

## Technológiai stack

- **Nyelv:** Java
- **Keretrendszer:** Spring Boot
- **Adatbázis:** PostgreSQL
- **Környezet/config kezelés:** `.env` + Docker Compose (tervezett)

## Jelenlegi állapot

- [ ] PostgreSQL entitások kialakítás alatt
- [ ] Környezeti és secret kezelés (`.env` + Docker Compose) — tervezett, miután az entitások elkészülnek
- [ ] Alap foglalási logika (később)
- [ ] A domain/felhasználási terület még nincs eldöntve

## Első lépések

Részletesebb telepítési útmutató akkor kerül majd a repóba, amint a környezeti és adatbázis-konfiguráció elkészül.

```bash
git clone <repo-url>
cd idopontfoglalo
# részletes telepítési útmutató hamarosan
```

## A projekt célja

Ez a repó egy tanulási/gyakorló projekt, amelynek célja egy Spring Boot backend felépítése a nulláról — entitástervezés, PostgreSQL adatbázis integráció, és konténerizált környezet kialakítása Docker Compose-zal.

## Licenc

Személyes/oktatási célú projekt — jelenleg nincs újrafelhasználásra licencelve.

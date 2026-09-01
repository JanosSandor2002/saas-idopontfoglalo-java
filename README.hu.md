# Időpontfoglaló Platform

> 🇭🇺 **Magyar** | [🇬🇧 English](README.md)

---

## A projektről

Online időpontfoglaló platform szépségipari szolgáltatók számára, például fodrászoknak, kozmetikusoknak, körömszalonoknak és hasonló vállalkozásoknak.

A cél, hogy a szalonok kiváltsák a telefonos és Messenger-alapú időpont-egyeztetést egy egyszerű, önkiszolgáló foglalási felülettel, ezáltal csökkentve az adminisztrációt és az utolsó pillanatos lemondásokat (no-show).

## Két oldal

* **Szalon tulajdonos/menedzser** – bejelentkezik, beállítja a szolgáltatásokat, árakat, munkarendet, valamint egy áttekintő naptárban kezeli a foglalásokat.
* **Ügyfél** – fiók létrehozása nélkül, néhány kattintással foglalhat időpontot a szalon foglalási oldalán keresztül.

Egy fiókhoz több szalon is tartozhat.

## Csomagszintek

| Csomag      | Amit tartalmaz                                                                                    |
| ----------- | ------------------------------------------------------------------------------------------------- |
| **Alap**    | Naptár modul (szolgáltatások, munkarend, foglalás)                                                |
| **Pro**     | + Értesítés modul (e-mail visszaigazolás, emlékeztető) + Online Tranzakció modul (előleg/fizetés) |
| **Prémium** | + SMS értesítés, naptárszinkron + Web modul (beágyazható foglalófelület / mini weboldal)          |

## Fő modulok

* **Naptár modul** – szolgáltatások, munkaidő, szabad és foglalt időpontok kezelése.
* **Értesítés modul** – automatikus e-mailes (később SMS-es) kommunikáció az ügyfél és a szalon között, ügyféltörténet és megjegyzések.
* **Online Tranzakció modul** – előleg vagy teljes díj bekérése foglaláskor Barion integráción keresztül a no-show csökkentése érdekében.
* **Web modul** – a foglalófelület beágyazása meglévő weboldalba, vagy egyszerű saját mini weboldal biztosítása a szalon számára.

## Tervezett technológia

* **Frontend:** Next.js
* **Backend:** NestJS (moduláris felépítés, csomagszint alapján be- és kikapcsolható modulokkal)
* **Adatbázis:** MongoDB
* **Fizetés:** Barion
* **E-mail:** AWS SES
* **Naptárszinkronizáció:** Egyirányú `.ics` feed Google Calendar / Outlook felé

## Mérföldkövek

1. **Koncepció kidolgozása** – probléma és célközönség meghatározása, funkciólista (MVP vs. jövőbeli), user flow, wireframe, domain modell és technológiai döntések véglegesítése.
2. **Tervezés → Architektúra** – repository struktúra (monorepo: `apps/frontend`, `apps/backend`, `packages/shared`), API végpontok listája és az adatbázis-séma első vázlata.
3. **Fejlesztői környezet** – Node.js verzió rögzítése, Docker Compose (backend, frontend, adatbázis), `.env.example`, `.gitignore`.
4. **Backend (NestJS) – tesztekkel együtt** – projekt inicializálása, adatbázis-kapcsolat (Mongoose), feature-alapú modulok, implementáció unit tesztekkel együtt, JWT autentikáció és tesztek, DTO-k és validáció.
5. **Frontend (Next.js) – tesztekkel együtt** – projekt inicializálása, design rendszer (Tailwind), API kliens réteg, fő oldalak (admin dashboard, ügyfélfelület), komponens- és integrációs tesztek (pl. React Testing Library).
6. **Fizetési integráció (Barion) – tesztekkel együtt** – sandbox fiók, `payment/start` és `payment/callback` végpontok, split payment logika sandboxban, unit- és integrációs tesztek (sikeres, sikertelen és timeout esetek), számlázó integráció szükség esetén.
7. **E2E tesztelés** – a fő user flow-k automatizált végigtesztelése (regisztráció → foglalás/vásárlás → fizetés), valamint a teljes folyamat manuális sandbox tesztelése.
8. **CI/CD** – pipeline (lint → build → teszt) minden pull request esetén, Docker image build automatizálása és staging környezet.
9. **Éles környezet** – szerver, domain, SSL beállítása, titkos kulcsok biztonságos kezelése, Barion élő fiók aktiválása, monitorozás és logolás (pl. Sentry).
10. **Indulás** – első valódi kereskedő onboardolása, az első éles tranzakció ellenőrzése, support csatorna kialakítása és folyamatos monitorozás.

## Jelenlegi státusz

A projekt jelenleg az **1. mérföldkő (Koncepció kidolgozása)** fázisában van.

A részletes funkciólista, user flow-k, domain modell és osztályarchitektúra a bővebb koncepciós dokumentációban található.

A UI és a wireframe-ek tervezése **még nem kezdődött el**.

## Licenc és felhasználási korlátozások

**Copyright © 2026. Minden jog fenntartva.**

A projekt és annak teljes tartalma – beleértve, de nem kizárólagosan a forráskódot, dokumentációt, terveket, adatbázis-struktúrákat, grafikai elemeket és kapcsolódó anyagokat – a szerző kizárólagos tulajdonát képezi, és kifejezett engedély nélkül nem használható fel.

Előzetes írásbeli engedély nélkül **tilos**:

* a forráskód vagy annak jelentős részének másolása, sokszorosítása vagy terjesztése;
* a projekt vagy annak bármely részének más projektben történő felhasználása;
* a projekt módosítása, átdolgozása vagy származékos mű készítése;
* a forráskód nyilvánosságra hozatala vagy más módon történő közzététele;
* a projekt vagy annak bármely részének kereskedelmi célú felhasználása;
* a projekt vagy annak származékos változatainak értékesítése, licencelése vagy továbbadása;
* a projekt vagy annak bármely részének saját munkaként való feltüntetése;
* a projekt architektúrájának, megvalósításának vagy egyéb védett anyagainak felhasználása konkurens termék vagy szolgáltatás létrehozására.

A repository megtekintése, klónozása vagy az ahhoz való hozzáférés **nem biztosít felhasználási licencet vagy egyéb jogot** a projekt tartalmának felhasználására.

A repository személyes megtekintésén és tanulmányozásán túlmenő bármilyen felhasználáshoz a **jogtulajdonos előzetes, kifejezett írásbeli engedélye szükséges**.

Engedélykéréssel kapcsolatban a jogtulajdonost kell megkeresni.
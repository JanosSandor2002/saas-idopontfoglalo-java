# Időpontfoglaló Platform – Koncepciós Dokumentum

### (Fodrász, Kozmetikus szalonok számára – "Projekt János")

---

## 1. Probléma és célközönség

### 1.1 A probléma

Kis- és középméretű szépségipari szolgáltatók (fodrász, kozmetikus, körömszalon stb.) ma jellemzően:

- telefonon vagy Messengeren egyeztetik az időpontokat, ami sok manuális adminisztrációt és hibalehetőséget jelent,
- nem látják át egyben a több alkalmazott/több telephely elérhetőségét,
- sokat veszítenek az utolsó pillanatban lemondott vagy meg nem jelenő ügyfelek miatt (no-show),
- nem gyűjtenek strukturált adatot a visszatérő ügyfelekről (preferenciák, allergiák, előzmények),
- nincs egységes, ügyfélbarát online felületük a foglalásra – sokszor csak egy Facebook oldal vagy telefonszám létezik.

### 1.2 Célközönség – szalontulajdonos / menedzser (a fizető ügyfél)

- 1–10 fős szépségipari vállalkozások (egyéni vállalkozó vagy kisebb csapat).
- Nem feltétlenül technikai háttérrel rendelkező felhasználók → a felületnek egyszerűnek, gyorsan beüzemelhetőnek kell lennie.
- Igény: kevesebb adminisztráció, kevesebb no-show, professzionálisabb megjelenés az ügyfelek felé.

### 1.3 Végfelhasználó – az ügyfél (aki időpontot foglal)

- Nem regisztrál fiókot, gyorsan, 2–3 kattintással szeretne időpontot foglalni.
- Elvárás: azonnali visszaigazolás, emlékeztető, egyszerű lemondás/átfoglalás lehetősége.

---

## 2. Funkciólista – MVP vs. jövőbeli funkciók

| Terület              | MVP (induláshoz szükséges)                                                                                                                                              | Később bevezetendő                                                                                                       |
| -------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ |
| **Naptár modul**     | Szolgáltatások, árak, időtartamok kezelése; munkaidő beállítás; 1 munkavállaló kezelése; szabad/foglalt slotok automatikus számítása; ügyféloldali foglalás fiók nélkül | Több munkavállaló komplex beosztáskészítővel (műszakcsere, szabadság kezelése); erőforrás-alapú foglalás (pl. szék, gép) |
| **Értesítés modul**  | E-mail visszaigazolás; e-mail emlékeztető (24 óra); lemondás/átfoglalás linkkel                                                                                         | SMS értesítés; WhatsApp/Viber integráció; Google/Outlook naptár szinkron (.ics); ügyfélszegmentálás, célzott kampányok   |
| **Ügyféladatok**     | Alap ügyféllista (név, telefon, e-mail, előzmények)                                                                                                                     | Egyedi megjegyzések (allergia, preferencia) kereshető/szűrhető módon; visszatérő vendég automatikus felismerése          |
| **Tranzakció modul** | – (Pro csomagtól induló funkció, MVP-ben Barion integráció)                                                                                                             | Részleges előleg szabályrendszer; automatikus, szabály alapú visszatérítés                                               |
| **Web modul**        | –                                                                                                                                                                       | Beágyazható iframe/API widget; platform által épített mini weboldal                                                      |
| **Csomagkezelés**    | 3 fix csomagszint (Alap/Pro/Prémium), modulok ki-be kapcsolása csomag szerint                                                                                           | Egyedi, moduláris árazás; több áruház/telephely csoportos kezelése egy fiók alatt                                        |

> **MVP célja:** egyetlen szalon, egyetlen munkavállaló, alap naptár + e-mail értesítés + Barion fizetés működjön stabilan, mielőtt a komplexebb (több alkalmazott, SMS, web modul) funkciók bekerülnek.

---

## 3. Felhasználói folyamatok (User Flow)

### 3.1 Szalontulajdonos – regisztrációtól az első foglalásig

```mermaid
flowchart TD
    A[Regisztráció] --> B[Csomag kiválasztása<br/>Alap / Pro / Prémium]
    B --> C[Szalon alapadatok megadása<br/>név, cím, elérhetőség]
    C --> D[Szolgáltatások felvitele<br/>név, ár, időtartam]
    D --> E[Munkaidő és szünetek beállítása]
    E --> F{Több munkavállaló?}
    F -- Igen --> G[Munkavállalók felvitele<br/>ki mit végez, mikor elérhető]
    F -- Nem --> H[Naptár publikálása]
    G --> H
    H --> I[Foglalási link / oldal elérhetővé válik]
    I --> J[Tulajdonos figyeli a naptárt<br/>dashboard: mai + közelgő foglalások]
```

### 3.2 Ügyfél – foglalás menete

```mermaid
flowchart TD
    A[Ügyfél megnyitja a szalon foglalási oldalát] --> B[Szolgáltatás kiválasztása]
    B --> C{Több munkavállaló<br/>van a szalonban?}
    C -- Igen --> D[Munkavállaló kiválasztása<br/>opcionális]
    C -- Nem --> E[Elérhető időpontok listázása]
    D --> E
    E --> F[Időpont kiválasztása]
    F --> G[Adatok megadása<br/>név, telefon vagy e-mail]
    G --> H{Van Tranzakció modul?<br/>Pro / Prémium}
    H -- Igen --> I[Előleg / teljes díj<br/>fizetése Barionon keresztül]
    H -- Nem --> J[Foglalás rögzítése]
    I --> J
    J --> K[Automatikus visszaigazolás<br/>e-mailben]
    K --> L[Emlékeztető az időpont előtt]
    L --> M{Ügyfél lemond?}
    M -- Igen --> N[Lemondás linkkel<br/>+ visszatérítés szabály szerint]
    M -- Nem --> O[Időpont megtörténik]
```

---

## 4. Domain modell (entitások és kapcsolatok)

```mermaid
erDiagram
    FIÓK ||--o{ SZALON : "tulajdonol"
    FIÓK ||--|| ELŐFIZETÉS : "rendelkezik"
    ELŐFIZETÉS }o--|| CSOMAG : "hivatkozik"
    SZALON ||--o{ MUNKAVÁLLALÓ : "foglalkoztat"
    SZALON ||--o{ SZOLGÁLTATÁS : "kínál"
    MUNKAVÁLLALÓ ||--o{ MUNKAREND : "rendelkezik"
    MUNKAVÁLLALÓ ||--o{ FOGLALÁS : "elvégzi"
    SZOLGÁLTATÁS ||--o{ FOGLALÁS : "szerepel benne"
    FOGLALÁS }o--|| ÜGYFÉL : "kapcsolódik"
    FOGLALÁS ||--o| TRANZAKCIÓ : "kiváltja (opcionális)"
    ÜGYFÉL ||--o{ MEGJEGYZÉS : "kap"
    ÜGYFÉL ||--o{ FOGLALÁS : "korábbi foglalások"

    FIÓK {
        uuid id
        string email
        string nev
    }
    CSOMAG {
        uuid id
        string nev "Alap/Pro/Prémium"
        json elerheto_modulok
    }
    SZALON {
        uuid id
        string nev
        string cim
        string telefon
    }
    MUNKAVÁLLALÓ {
        uuid id
        string nev
        string szerepkor
    }
    MUNKAREND {
        uuid id
        date nap
        time kezdes
        time zaras
    }
    SZOLGÁLTATÁS {
        uuid id
        string nev
        int idotartam_perc
        int ar
    }
    ÜGYFÉL {
        uuid id
        string nev
        string telefon
        string email
    }
    MEGJEGYZÉS {
        uuid id
        string szoveg "pl. allergia, preferencia"
    }
    FOGLALÁS {
        uuid id
        datetime idopont
        string statusz "foglalt/lemondva/megtortent"
    }
    TRANZAKCIÓ {
        uuid id
        int osszeg
        string statusz "elolegezve/fizetve/visszaterítve"
        string szolgaltato "Barion"
    }
```

**Megjegyzés a modellhez:** egy Fiók (Account) több Szalonhoz kapcsolódhat (a specifikációban jelzett "egy fiókhoz több áruház csatlakozhat" elv szerint) – ez egy 1:N kapcsolat Fiók és Szalon között, míg az Előfizetés/Csomag a Fiók szintjén dől el, és ez határozza meg, mely modulok érhetők el az összes hozzá tartozó szalonban.

> **MongoDB-s megvalósítás megjegyzése:** a fenti ER-diagram a logikai kapcsolatokat mutatja. MongoDB-ben ez dokumentum-alapú modellezéssel valósul meg: pl. a `SZALON` dokumentum beágyazhatja a `MUNKAVÁLLALÓ` és `SZOLGÁLTATÁS` al-dokumentumokat (ritkán változó, szalononkénti adat), míg a `FOGLALÁS` és `TRANZAKCIÓ` külön kollekcióban, referenciával (`szalon_id`, `ugyfel_id`, `munkavallalo_id`) kapcsolódik hozzájuk – mivel ezek gyakran és nagy számban keletkeznek.

---

## 5. Osztályarchitektúra (backend modulok és fő osztályok)

```mermaid
classDiagram
    class Account {
        +String id
        +String email
        +String name
        +Subscription subscription
        +Salon[] salons
    }

    class Subscription {
        +String id
        +PackageType packageType
        +Date validUntil
        +hasModule(moduleName) bool
    }

    class Salon {
        +String id
        +String name
        +String address
        +String phone
        +Employee[] employees
        +Service[] services
    }

    class Employee {
        +String id
        +String name
        +String role
        +Schedule[] schedule
    }

    class Schedule {
        +String id
        +Date day
        +Time start
        +Time end
    }

    class Service {
        +String id
        +String name
        +int durationMinutes
        +int price
    }

    class Customer {
        +String id
        +String name
        +String phone
        +String email
        +Note[] notes
    }

    class Note {
        +String id
        +String text
    }

    class Booking {
        +String id
        +DateTime dateTime
        +BookingStatus status
        +confirm()
        +cancel()
        +reschedule()
    }

    class Transaction {
        +String id
        +int amount
        +TransactionStatus status
        +String provider
        +charge()
        +refund()
    }

    class NotificationService {
        +sendBookingConfirmation(booking)
        +sendReminder(booking)
        +sendCancellationNotice(booking)
    }

    class BarionPaymentService {
        +createPayment(transaction)
        +refundPayment(transaction)
        +getStatus(transaction)
    }

    class CalendarSyncService {
        +generateIcsFeed(employee)
    }

    Account "1" --> "1" Subscription
    Account "1" --> "*" Salon
    Salon "1" --> "*" Employee
    Salon "1" --> "*" Service
    Employee "1" --> "*" Schedule
    Employee "1" --> "*" Booking
    Service "1" --> "*" Booking
    Booking "*" --> "1" Customer
    Booking "0..1" --> "1" Transaction
    Customer "1" --> "*" Note
    Booking ..> NotificationService : uses
    Transaction ..> BarionPaymentService : uses
    Employee ..> CalendarSyncService : uses
```

**Fő architekturális elvek:**

- A **Subscription** (`Előfizetés`) dönti el, mely modulok (Naptár / Értesítés / Tranzakció / Web) aktívak egy Fiókhoz tartozó összes Szalonon – ez a `hasModule()` metódussal ellenőrizhető minden modulhívás előtt (feature-flag jellegű működés).
- A **NotificationService**, **BarionPaymentService** és **CalendarSyncService** külön szolgáltatás-osztályok (NestJS-ben külön modulok), amelyeket a `Booking` és `Transaction` entitások use-case szinten hívnak meg – így ezek a modulok csomagszint szerint önállóan ki- és bekapcsolhatók.
- A **BarionPaymentService** az egyetlen fizetési integráció; nincs más fizetési szolgáltató bevonva a tervbe.

---

## 6. Technológiai döntések

| Réteg                   | Választott technológia                           | Indoklás / megjegyzés                                                                                                                                                                   |
| ----------------------- | ------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Frontend                | **Next.js**                                      | SSR/ISR támogatás a gyors, SEO-barát ügyféloldali foglalási oldalakhoz; jó illeszkedés egy jövőbeli Web modulhoz is                                                                     |
| Backend                 | **NestJS**                                       | Strukturált, moduláris felépítés – jól illeszkedik a csomag szerinti modul be/kikapcsoláshoz (Naptár, Értesítés, Tranzakció, Web modul külön NestJS modulként)                          |
| Adatbázis               | **MongoDB**                                      | Dokumentum-alapú modell rugalmasan kezeli a szalononként eltérő szolgáltatás- és munkarend-struktúrákat; jól skálázható a nagy számban keletkező Foglalás/Tranzakció adatokra           |
| Fizetés                 | **Barion**                                       | Magyar piacra optimalizált, forintos elszámolás; az egyetlen, véglegesen kiválasztott fizetési szolgáltató                                                                              |
| E-mail értesítés        | **AWS SES**                                      | API-n keresztül integrálható, használat-alapú díjazás (~40 Ft / 1000 e-mail), bevált nagy szolgáltatóknál (Netflix, Duolingo)                                                           |
| SMS értesítés (Prémium) | **Vonage Messages API**                          | Egységes API SMS/MMS/RMS-hez, valamint WhatsApp/Messenger/Viber támogatással; költsége miatt (akár 60 Ft/SMS) csak Prémium csomagban indokolt                                           |
| Naptárszinkron          | **.ics fájl generálás, egyirányú GET végponton** | A szerver generálja a `.ics` feedet munkavállalónként/szalononként; Google Calendar / Outlook ezen keresztül szinkronizál – nincs szükség OAuth-alapú kétirányú integrációra az MVP-ben |

### 6.1 Nyitott kérdések

1. **Web modul iránya:** meglévő weboldalba ágyazható widget (iframe/API) vagy platform által épített mini weboldal – vagy mindkettő, csomagszinttől függően?
2. **Beosztáskészítő modul mélysége:** egyszerű heti munkarend elég-e, vagy kell műszakcsere/szabadságkezelés is már az első verzióban?
3. **Visszatérítési szabályrendszer:** mennyi idővel a foglalás előtt jár teljes/részleges/nincs visszatérítés – ez szalononként testre szabható legyen, vagy platformszintű alapértelmezés legyen?

---

## 7. Csomagszintek összefoglalása

| Modul                                 | Alap | Pro | Prémium |
| ------------------------------------- | :--: | :-: | :-----: |
| Naptár modul                          |  ✅  | ✅  |   ✅    |
| Értesítés modul (e-mail)              |  –   | ✅  |   ✅    |
| Értesítés modul (SMS, naptárszinkron) |  –   |  –  |   ✅    |
| Online Tranzakció modul (Barion)      |  –   | ✅  |   ✅    |
| Web modul                             |  –   |  –  |   ✅    |

---

_A dokumentum a megadott projektleírás alapján készült koncepciós összefoglaló; a nyitott kérdések (6.1 pont) még döntést igényelnek a fejlesztés megkezdése előtt. A kinézeti/UI vázlatok (wireframe) ebből a verzióból szándékosan kimaradtak._
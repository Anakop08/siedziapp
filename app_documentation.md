# SIEDZI! Dziennik Połowów – Specyfikacja Ekranów i Przepływów dla Agenta UI

> Dokument opisuje **wszystkie ekrany, przepływy i szczegóły UX** aplikacji do wędkowania.
> Powstał na podstawie wywiadu z użytkownikiem. Każdy szczegół był bezpośrednio podany przez właściciela produktu.
> Używaj tego dokumentu jako jedynego źródła prawdy przy projektowaniu ekranów.

---

## Filozofia Produktu

- **Offline-First**: Użytkownik często bywa bez zasięgu. Wszystkie kluczowe dane (mapy, dane sesji, słowniki) muszą być dostępne offline.
- **Szybkość**: Zapis połowu musi być możliwy w kilka sekund — jeden klik, jedno zdjęcie, minimum pól.
- **Prywatność**: Galeria i dane są prywatne. Udostępnianie jest zawsze inicjowane przez użytkownika.
- **Motyw**: Aplikacja obsługuje 3 motywy: **jasny, ciemny i kolorowy** (przełącznik dostępny z poziomu karty sesji oraz z ekranu Ustawień).

---

## Architektura Nawigacji (Dashboard → Ekrany)

Dashboard Główny ma **5 głównych wejść**:

| Przycisk na Dashboardzie | Dokąd prowadzi |
|---|---|
| Wspomnienia i Sukcesy | Historia + galeria archiwalnych wyjazdów |
| Zaplanuj Nową Wyprawę | Proces Planowania |
| Rozpocznij Sesję – nad wodą | Aktywna Sesja |
| Galeria Prywatna | Galeria zdjęć ryb |
| Historia WyjazdĂłw | Lista sesji (bezpośredni dostęp przez nav bar) |
| Ustawienia / Eksport | Ustawienia, słowniki, eksport |

> Dashboard powinien wyświetlać kartę **ostatniej sesji**, jeśli użytkownik właśnie ją zakończył.

---

## Mechanizm: Active Session Banner (persystentny)

Gdy użytkownik ma **aktywną sesję wędkarską**, na każdym ekranie aplikacji (Dashboard, Galeria, Historia, Ustawienia itp.) wyświetlany jest **persystentny baner** tuż nad dolnym paskiem systemowym Androida.

```
╔════════════════════════════════════╗
║ 🟢  Sesja aktywna · 3h 42min    › ║
╚════════════════════════════════════╝
```

### Działanie banera
- **Zawsze widoczny** – na każdym ekranie, gdy sesja trwa.
- **Jedno dotknięcie** → powrót do Osi Czasu Sesji (dokładnie tam, gdzie użytkownik był).
- **Znika** po zakończeniu sesji (dialog „Zakończyć sesję?" → Tak).

### Przypadek użycia (przykład)
> Wędkarz w trakcie sesji chce pokazać koledze zdjęcie z Galerii:
> `Oś Czasu → cofnij → Dashboard → Galeria → [pokazuje zdjęcie] → baner „🟢 Sesja aktywna" → powrót do Osi Czasu`

### Cel UX
- Swoboda przemieszczania się po całej aplikacji bez ryzyka „zgubienia" aktywnej sesji.
- Brak potrzeby stałego dolnego paska nawigacji (Bottom Navigation Bar).
- Wzorzec inspirowany: Spotify mini-player, Uber active ride banner, Google Maps navigation bar.

### Ekrany, na których baner JEST widoczny
Dashboard, Galeria, Historia, Wspomnienia, Ustawienia i wszystkie ich pod-ekrany.

### Ekrany, na których baner NIE jest widoczny
Splash Screen, Onboarding, ekrany wewnątrz aktywnej sesji (Oś Czasu, Formularz Zasadzki, Rejestracja Połowu).

---

## 1. EKRAN STARTOWY

- Splash screen z logo aplikacji.
- Przejście automatyczne do Dashboardu.

---

## 2. DASHBOARD GŁÓWNY

- 5 kafelków / przycisków nawigacyjnych (patrz tabela wyżej).
- Widoczna **karta ostatniej sesji** (jeśli sesja została niedawno zamknięta): pokazuje ikony złowionych ryb, sumaryczną wagę, datę i czas trwania.

---

## 3. WSPOMNIENIA I SUKCESY (Historia archiwalna z Dashboardu)

Ekran motywacyjny. Zawiera:
- Lista poprzednich trip z miniaturkami
- **Galeria Zdjęć Ryb** – archiwalne zdjęcia
- **Szczegóły Pogody Archiwum** – warunki z poprzednich sesji

---

## 4. PLANOWANIE WYPRAWY

### 4.1 Wybór Daty / Urlopu
- Kalendarz lub picker daty wyjazdu.
- Możliwość zaznaczenia całego urlopu (zakres dat).

### 4.2 Wyszukiwanie Łowiska
- Wyszukiwarka z listą zapisanych łowisk.
- Dwie ścieżki:
  - **Wybierz z listy** → przejdź do Podsumowania Planu
  - **Dodaj Nowe Łowisko** → formularz:
    - Wpisz nazwę i adres
    - Automatyczna lokalizacja GPS z mapy
    - Pole notatki: numer stanowiska
    - Przejście do Podsumowania Planu

### 4.3 Podsumowanie Planu → Analiza Strategiczna

Po wybraniu łowiska i daty, użytkownik trafia do ekranu analizy:

#### Strateg / Analityk Wyprawy
Jeden ekran z dwoma sekcjami:

**A) Warunki: Pogoda / Ciśnienie / Solunar**
- Prognoza pogody na wybrany termin
- Dane ciśnienia atmosferycznego + trend
- Kalendarz Solunarny: godziny aktywności ryb
- Opis: „co wróżą gwiazdy" na ten wyjazd

**B) Historia: Zeszły rok / 5-lat Avg**
- Porównanie warunków z analogicznym okresem rok temu
- Porównanie z 5-letnią średnią dla tego obszaru

#### Notatnik Strategii i Insightów
- Pole tekstowe do zapisania własnych obserwacji i strategii wynikających z analizy.
- Przejście: **Zatwierdź i Zapisz Wyprawę**.

---

## 5. DZIEŃ WYJAZDU (Przed Wyjazdem – w domu)

Po zapisaniu wyprawy, użytkownik przed wyjazdem otwiera ten ekran:

### 5.1 Odśwież Dane (wymaga internetu)
- **Aktualna Pogoda** – pobierz zaktualizowaną prognozę na dzień wyjazdu
- **Solunar i Gwiazdy** – odśwież dane solunarne
- **Synchronizuj Kopię Zapasową** – wyślij wszystkie dane na Google Drive (zanim straci zasięg)

### 5.2 Pobierz Mapy Offline (wymaga internetu, zapisuje na telefon)
Trzy typy map do pobrania:
1. **Mapa standardowa**
2. **Mapa topograficzna**
3. **Zdjęcie satelitarne HD** (z Geoportalu)

> Mapy są **tymczasowo zapisywane** na telefonie na czas wyjazdu.
> **Po zakończeniu sesji** mapy są automatycznie kasowane.
> **Zostaje tylko** miniatura widoku topograficznego – jako zdjęcie poglądowe łowiska (trafia do historii i kopii zapasowej).

### 5.3 Wyjazd
Przycisk: **„Jedź na łowisko!"** → prowadzi do Aktywnej Sesji.

---

## 6. AKTYWNA SESJA – NAD WODĄ

### 6.1 Meldunek na Stanowisku
- Pierwszy krok po uruchomieniu sesji.
- Potwierdzenie lokalizacji (może użyć GPS lub wybrać ręcznie).

### 6.2 Ekran Sesji (główny hub sesji)
Trzy sekcje wyświetlane **jednocześnie jako pionowe karty** (nie zakładki) — użytkownik scrolluje w dół:

| Sekcja | Zawartość |
|---|---|
| Opis łowiska | Lista gatunków ryb, zakazy, przepisy, ostrzeżenia |
| Moje statystyki | Sumaryczna waga połowów na tym łowisku, max długość ryby |
| Historia Wizyt | Lista moich poprzednich wyjazdów na to łowisko (jeśli brak: puste) |

Każda karta ma CTA prowadzące do **Formularza Zasadzki** (wszystkie trzy prowadzą w to samo miejsce).

> **Uwaga implementacyjna:** Sekcja „Historia Wizyt" może być pusta przy pierwszej sesji na nowym łowisku — ekran powinien wyświetlać stan pusty z komunikatem „Pierwszy raz tutaj — zacznij historię!" zamiast ukrywać sekcję.

### 6.3 Formularz Zasadzki (przed zarzuceniem wędki)

Trzy grupy pól (wybór z słownika lub wpisanie ręczne z podpowiedziami):
- **Wędka / Przynęta / Zanęta**
- **Kołowrotek / Żyłka / Osprzęt / Haczyki**
- **Łódka zanętowa** (toggle: tak/nie) + **Echosonda** (toggle: tak/nie)

Przycisk: **„Zarzuć wędkę"**

### 6.4 Oś Czasu Sesji (Session Timeline)

Po każdym zarzuceniu wędki na osi czasu pojawia się wpis z tagami zestawu użytego przy tym rzucie.

**Pętla sesji:**
```
Zarzuć → Oś Czasu (wpis) → Czekanie (app w kieszeni)
    ↓
    → Zmiana Zestawu → zarzuć ponownie → nowy wpis na osi czasu
    → Branie! → Rejestracja Połowu → karta na osi czasu → z powrotem na oś
    → Koniec Dnia → Zakończ Sesję?
```

### 6.5 Zmiana Zestawu w trakcie Sesji
- Użytkownik otwiera ten sam formularz co przy pierwszym rzucie.
- **Tylko zmienione pola** są edytowane – reszta zostaje bez zmian.
- Zapisuje nowy rzut → nowy wpis na osi czasu z zaktualizowanymi tagami.
- Różnica w przynęcie/haczyk/etc. jest widoczna jako tag przy wpisie.

### 6.6 Rejestracja Połowu (Branie!)

**Krok 1: Szybkie Zdjęcia**
- Jeden przycisk → natychmiastowe otwarcie aparatu.
- Zdjęcie zapisuje się automatycznie do Karty Połowu.
- Można zrobić kolejne zdjęcia (do **maksymalnie 5**).
- Przy próbie 6. zdjęcia: **komunikat o limicie** (max 5 zdjęć na karcie połowu).
- **Zdjęcie nieobowiązkowe**: użytkownik może pominąć krok 1 i przejść bezpośrednio do Karty Połowu (przycisk „Pomiń zdjęcie" lub „Bez zdjęcia"). Połów zostanie zapisany bez fotografii.

**Krok 2: Karta Połowu**
- Aplikacja automatycznie przechodzi do podglądu Karty Połowu po zrobieniu zdjęcia/zdjęć.
- Widoczne wszystkie zdjęcia z tego połowu.
- Pod zdjęciami **szybki formularz**:
  - Gatunek (wybór z listy) — **wymagany**
  - Waga (kg/g) — **wymagana**
  - Długość (cm) — opcjonalna
  - Nickname ryby (opcjonalny, tekstowy — np. „Wielka Berta", „Karpik Majowy")

> **Walidacja**: bez podania gatunku i wagi przycisk „Zapisz połów" jest nieaktywny. Długość i nickname można pominąć.

**Krok 2b: Zestaw Połowu (auto-wypełniony)**
Aplikacja automatycznie dołącza do Karty Połowu **pełne ustawienia aktualnie zarzuconej wędki** (z bieżącego wpisu na Osi Czasu):
- Wędka (nazwa, długość, klasa)
- Kołowrotek
- Żyłka (typ, średnica)
- Przynęta
- Zanęta
- Haczyki (typ, rozmiar)
- Osprzęt (klipsy, krętliki, etc.)
- Łódka zanętowa (tak/nie)
- Echosonda (tak/nie)

**Widok na ekranie:** najważniejsze pozycje (wędka, przynęta, zanęta) widoczne jako **tagi**.
Pełna lista domyślnie **zwinięta** – rozwijana jednym kliknięciem do weryfikacji lub edycji.
Edycja zestawu z poziomu Karty Połowu aktualizuje tylko ten konkretny wpis (bez zmiany bieżącego ustawienia na Osi Czasu).

**Krok 3: Zapisz Połów**
Po kliknięciu Zapisz, aplikacja **automatycznie pobiera metadane**:
- GPS ze zdjęcia (lub aktualny GPS)
- Godzina ze zdjęcia
- Aktualne warunki pogodowe
- Aktualne dane solunarne
- Zestaw z aktualnego rzutu (patrz Krok 2b)

**Krok 4: Karta na Osi Czasu**
- Karta połowu pojawia się na osi czasu sesji.
- Tagi: ikona gatunku ryby, miniaturka zdjęcia, informacje pogodowe.

---

## 7. ZAKOŃCZENIE SESJI

### 7.1 Dialog Potwierdzenia
- Wyskakuje z osi czasu na końcu dnia.
- „Czy na pewno chcesz zakończyć sesję?" → **Tak / Nie**

### 7.2 Karta Sesji (Auto-generowana)
Po potwierdzeniu, aplikacja automatycznie tworzy kartę sesji:
- Ikony złowionych gatunków ryb + ich ilość
- Sumaryczna waga wszystkich połowów
- Data sesji
- **Godzina otwarcia i zamknięcia** sesji
- Automatycznie obliczony **czas trwania** sesji

> **Sesja bez połowów:** Jeśli użytkownik nie zarejestrował żadnego połowu, karta sesji nadal jest generowana — pokazuje 0 połowów, 0 kg, czas trwania sesji i lokalizację. Widoczny komunikat: „Dzisiaj bez brania — ale byłeś nad wodą!". Karta trafia do historii jak każda inna sesja.

### 7.3 Przełącznik Motywu
Widoczny przy karcie sesji – użytkownik może przełączyć:
- Jasny / Ciemny / Kolorowy
(Cel: wygoda czytania wieczorem przed snem)

### 7.4 Powrót na Dashboard
- Po zapisaniu → powrót na Ekran Główny.
- Karta ostatniej sesji **widoczna na Dashboardzie**.

### 7.5 Auto-Porządkowanie
Automatycznie (bez interwencji użytkownika):
- Tymczasowe mapy są **kasowane**.
- Zostaje tylko **miniatura widoku topograficznego** jako zdjęcie poglądowe łowiska.
- Uruchamiana jest **synchronizacja z Google Drive** (finalny backup).

---

## 8. HISTORIA WYJAZDÓW

Dostęp: z Dashboardu (przez "Wspomnienia") lub z Karty Sesji po jej zakończeniu.

### Widok Listy Sesji
- Chronologiczna lista wszystkich sesji.
- Każda sesja widoczna jako karta z: datą, łowiskiem, ikonami ryb, czasem trwania.

### Wyszukiwanie i Filtrowanie
Filtry:
- **Data** (zakres)
- **Nazwa łowiska**
- **Gatunek ryby** (złowionej podczas sesji)

### Szczegóły Sesji
Ekran zawiera:
- Pełna oś czasu sesji z wpisami rzutów i połowów
- Zdjęcia ze wszystkich połowów
- Sprzęt użyty przy każdym połowie
- Dane pogodowe i solunarne z sesji

---

## 9. GALERIA PRYWATNA

Dostęp: z Dashboardu → "Galeria Prywatna"

### 9.1 Widok Siatki
- Zdjęcia grupowane po **5** — pierwsze zdjęcie jest na pierwszym planie (główne).
- Kliknięcie w grupę otwiera **karuzelę** dla danego połowu.

### 9.2 Tagi Zdjęcia
Każde zdjęcie ma widoczne tagi **generowane automatycznie** z danych połowu (nieedytowalne inline):
- Łowisko
- Waga ryby
- Wymiary ryby
- Data i godzina
- Przynęta użyta przy połowie

Tagi są **tylko do odczytu** w widoku galerii. Edycja danych połowu (np. korekta wagi) możliwa jest wyłącznie z poziomu szczegółów sesji w Historii — zmiana danych połowu automatycznie aktualizuje wyświetlane tagi.

### 9.3 Karuzela Połowu
- Przesuwanie między zdjęciami z jednego połowu.
- Opcje:
  - **Zmień kolejność** zdjęć w połowie
  - **Ustaw inne zdjęcie jako główne** (pierwsze w grupie)
  - **Edytuj**: przytnij, usuń

### 9.4 Udostępnianie
- Przycisk udostępnij → systemowy share sheet.
- **Auto-opis**: zdjęcie automatycznie dodaje opis z: wagą, wymiarami, nazwą łowiska.
- Przełącznik: **Włącz / Wyłącz** auto-opis przed wysłaniem.
- Przykładowe docelowe aplikacje: Instagram, Facebook, chat (WhatsApp/Messenger).

---

## 10. USTAWIENIA / EKSPORT

### 10.1 Eksport i Kopia Zapasowa
- Wyświetla status połączenia z Google Drive (zsynchronizowano / błąd / oczekuje).
- Pokazuje ostatnią datę i godzinę synchronizacji.
- Wskaźnik zajętości przestrzeni Drive (np. 2,3 GB / 15 GB).
- Dwa działania:
  - **Synchronizuj teraz** – ręczna synchronizacja danych i zdjęć na Drive
  - **Eksportuj jako ZIP** – pobiera pełną kopię danych jako archiwum ZIP do pobrania
- Kopia zapasowa obejmuje: **dane + zdjęcia**.
- (Przyszłościowo: własny darmowy Cloud zamiast Google Drive)

**Zawartość eksportu ZIP:**
```
siedzi-backup-YYYY-MM-DD.zip
├── sessions/
│   ├── session-001.json   ← dane sesji: łowisko, daty, czas trwania
│   ├── session-002.json
│   └── ...
├── catches/
│   ├── catch-001.json     ← dane połowu: gatunek, waga, GPS, pogoda, zestaw
│   └── ...
├── photos/
│   ├── catch-001-01.jpg
│   └── ...
├── dictionaries/
│   ├── rods.json          ← słownik wędek i zestawów
│   ├── baits.json
│   └── hooks.json
└── fisheries.json         ← lista łowisk z miniaturami topo
```

### 10.2 Słowniki Sprzętu i Przynęt
Użytkownik zarządza słownikami, które alimentują podpowiedzi w formularzach:
- **Słownik: Wędki i Zestawy**
- **Słownik: Przynęty i Zanęty**
- **Słownik: Haczyki i Osprzęt**

W formularzach (np. Formularz Zasadzki) pola mają:
- wybór z listy słownikowej
- wyszukiwanie z podpowiedziami (autocomplete)

### 10.3 Motyw Aplikacji
Przełącznik motywu dostępny bezpośrednio z ekranu Ustawień (oraz z Karty Sesji po jej zakończeniu):
- **Jasny** / **Ciemny** (domyślny) / **Kolorowy**

### 10.4 Dane i Prywatność
- **Pamięć podręczna map**: wyświetla rozmiar tymczasowej pamięci map offline; możliwość ręcznego wyczyszczenia (np. jeśli auto-czyszczenie nie nastąpiło po sesji).
- **Wyczyść dane aplikacji**: usuwa wszystkie lokalne sesje, połowy i słowniki (operacja nieodwracalna, poprzedzona dialogiem potwierdzenia).

### 10.5 Zarządzanie Łowiskami

**Lista Łowisk:**
- Pełna lista łowisk dostępnych do wyboru przy planowaniu.

**Dodaj Nowe Łowisko:**
1. Wpisz Nazwę i Adres
2. Otwórz mapę → kliknij dokładne miejsce → GPS pobierany automatycznie
3. Mapa ładuje się pełnoekranowo → możliwość dokładnego wyboru miejsca
4. **Zapisz wycinek mapy topograficznej**: użytkownik kadruje tak, by widoczne było całe łowisko
5. Nowe łowisko pojawia się natychmiast na liście dostępnej przy planowaniu wyjazdów

---

## 11. ONBOARDING (Pierwsze Uruchomienie)

Onboarding wyświetlany jest **wyłącznie przy pierwszym uruchomieniu** aplikacji, przed przejściem do Dashboardu. Każdy krok można pominąć przyciskiem „Pomiń". Po jednorazowym przejściu nie pojawia się ponownie (flaga `onboardingCompleted` w lokalnej bazie).

### Ranking wartości funkcji (podstawa treści onboardingu)

Onboarding eksponuje funkcje w kolejności wartości dla wędkarza:

| Tier | Funkcja | Dlaczego ważna |
|------|---------|----------------|
| ★★★ | Połów zarejestrowany w 3 sekundy | Najczęstsza akcja w aplikacji; szybkość kluczowa gdy ryba na haczyku |
| ★★★ | Solunar i prognoza pogody | Wędkarze planują wyjazdy pod godziny aktywności ryb |
| ★★★ | Offline-first | Brak zasięgu nad wodą to norma; brak internetu nie blokuje rejestracji |
| ★★ | GPS + pogoda + solunar auto | Nikt nie wpisuje tego ręcznie — to odróżnia SIEDZI! od notatnika |
| ★★ | Oś czasu sesji | Każdy rzut, zmiana zestawu, branie — pełna dokumentacja wyjazdu |
| ★★ | Zestaw wędki auto-zapamiętany | Catch card automatycznie wie, co było na haczyku |
| ★ | Galeria prywatna + udostępnianie | Zdjęcia z auto-opisem gotowe na Instagram/Facebook |
| ★ | Historia wyjazdów + filtrowanie | Porównanie sesji po gatunku, łowisku, warunkach |
| ★ | Backup Google Drive | Dane bezpieczne przed i po wyjeździe |

### 11.1 Ekran 24: Witaj w SIEDZI!

Pierwsza impresja — atmosferyczny ekran powitalny z logo i 3 głównymi propozycjami wartości.

**Hero:** Logo SIEDZI! + SVG wędki + tagline „Dziennik Połowów"

**3 karty wartości (Tier-1):**
1. **Teal** — „Połów w 3 sekundy" — Zdjęcie + gatunek i waga. GPS, pogoda i solunar dodają się same.
2. **Green** — „Offline. Zawsze gotowy" — Sesja, oś czasu i mapy topo działają bez zasięgu. Sync po powrocie.
3. **Amber** — „Solunar decyduje" — Najlepsze godziny połowów + historia z poprzednich wyjazdów.

**Szybkie pills (Tier-2/3):** Galeria prywatna · Historia wyjazdów · Słowniki sprzętu · Backup Drive

**Wskaźnik kroków:** ● ○ ○

**CTA główne:** „Zaczynamy!" (teal/woda gradient)
**CTA drugorzędne:** „Pomiń" (link)

### 11.2 Ekran 25: Jak działa aplikacja?

4 slajdy prezentujące główny przepływ aplikacji. Swipe lub przyciski Dalej/Wstecz.

**Slajd 1 — Zaplanuj wyprawę:**
- Ikona: kalendarz + słońce
- Tytuł: „Zaplanuj z wyprzedzeniem"
- Opis: Prognoza pogody, ciśnienie, solunar na wybrany dzień. Porównanie z historią z poprzednich lat.
- Mini-preview: fragment ekranu 07 (strateg/analityk)

**Slajd 2 — Zarzuć i rejestruj:**
- Ikona: wędka (fishing rod SVG)
- Tytuł: „Każdy rzut ma swoje miejsce"
- Opis: Formularz zestawu (wędka, przynęta, zanęta) → oś czasu sesji z każdym rzutem i zmianą.
- Mini-preview: fragment ekranu 12 (oś czasu)

**Slajd 3 — Branie! Trzy kroki:**
- Ikona: kamera + ryba
- Tytuł: „Połów w 3 sekundy"
- Opis: Zdjęcie → gatunek i waga → Zapisz. GPS, czas, pogoda i zestaw dodają się automatycznie.
- Mini-preview: fragment ekranu 14-15 (karta połowu)

**Slajd 4 — Historia i galeria:**
- Ikona: galeria / historia
- Tytuł: „Twoja historia połowów"
- Opis: Przeglądaj sesje, filtruj po gatunku i łowisku. Galeria prywatna z gotowymi opisami do social media.
- Mini-preview: fragment ekranu 17/19

**CTA na ostatnim slajdzie:** „Rozumiem! Zaczynamy" → Ekran 26

### 11.3 Ekran 26: Skonfiguruj Pierwsze Łowisko

Minimalny setup pozwalający od razu zacząć korzystać z aplikacji.

**Sekcja 1 — Twoje łowisko:**
- Pole: Nazwa łowiska (wymagane)
- Pole: Miejscowość / region (opcjonalne)
- Przycisk: „Ustaw na mapie" (link do widoku mapy)

**Sekcja 2 — Co chcesz skonfigurować teraz?**
Trzy przełączniki toggle (domyślnie włączone):
- Dodaj swoje wędki i zestawy → Słowniki Sprzętu
- Pobierz mapę offline dla łowiska → Mapa Topo
- Skonfiguruj backup Google Drive → Ustawienia

**CTA główne:** „Idę łowić! Zaczynamy" → Dashboard
**CTA drugorzędne:** „Pomiń — skonfiguruje później" → Dashboard

---

## 12. PRZYPADKI BRZEGOWE I BŁĘDY

> Definicje zachowania aplikacji w sytuacjach niestandardowych. Implementacja musi obsługiwać każdy z poniższych przypadków.

### 12.1 Brak internetu

| Sytuacja | Zachowanie aplikacji |
|----------|---------------------|
| Synchronizacja Drive bez internetu | Toast: „Brak połączenia — synchronizacja zostanie wznowiona automatycznie po odzyskaniu zasięgu." Dane buforowane lokalnie. |
| Pobieranie map bez internetu | Toast: „Wymagane połączenie z internetem do pobrania map." Przycisk pobierania nieaktywny. |
| Odświeżenie pogody/solunaru bez internetu | Wyświetlane są ostatnio pobrane dane z informacją „Dane z [data/godzina ostatniego odświeżenia]". |
| Eksport ZIP bez internetu | ZIP generowany jest lokalnie (bez Drive) i zapisywany na telefonie. |

### 12.2 Brak miejsca na dysku

| Sytuacja | Zachowanie aplikacji |
|----------|---------------------|
| Pobieranie map — brak miejsca | Dialog: „Niewystarczające miejsce. Potrzebujesz ~[X] MB. Aktualnie wolne: [Y] MB." + przycisk „Wyczyść mapy tymczasowe". |
| Zapis zdjęcia — brak miejsca | Toast: „Brak miejsca na nowe zdjęcie. Zwolnij przestrzeń w ustawieniach." Zdjęcie nie zostaje zapisane. |
| Eksport ZIP — brak miejsca | Dialog z informacją o wymaganym rozmiarze + link do Ustawień → Dane. |

### 12.3 Brak uprawnień systemowych

| Uprawnienie | Jeśli brak | Fallback |
|-------------|-----------|---------|
| Aparat | Komunikat: „Zezwól na dostęp do aparatu w Ustawieniach systemu." + przycisk „Otwórz ustawienia" | Zablokowany krok 1 (zdjęcia), ale można przejść do Karty Połowu bez zdjęcia |
| Lokalizacja (GPS) | Toast: „GPS niedostępny — wskaż lokalizację ręcznie na mapie." | Mapa z pinem do ręcznego ustawienia przy meldunku i formularzach |
| Zapis plików (Storage) | Dialog o braku uprawnień + link do ustawień systemu | Eksport ZIP niedostępny |

### 12.4 Inne przypadki brzegowe

| Sytuacja | Zachowanie |
|----------|-----------|
| Pierwsza sesja na nowym łowisku | Sekcja „Historia Wizyt" w Ekranie Sesji: empty state „Pierwszy raz tutaj — zacznij historię!" |
| Sesja zakończona bez żadnego połowu | Karta sesji generowana z 0 połowów i komunikatem motywacyjnym |
| Słownik pusty przy Formularzu Zasadzki | Pole z podpowiedzią „Dodaj sprzęt w Ustawieniach → Słowniki" + możliwość wpisania ręcznie |
| Brak łowisk przy planowaniu | Ekran wyszukiwania łowiska wyświetla empty state z CTA „Dodaj pierwsze łowisko" |
| Utrata połączenia w trakcie synchronizacji | Synchronizacja wstrzymana, dane lokalne bezpieczne, wznowienie automatyczne |

---

## Legenda Kolorów (Flowchart / Design System)

| Obszar | Kolor | Hex |
|---|---|---|
| Dashboard / Main | Niebieski | `#1e40af` |
| Wspomnienia / Historia | Fioletowy ciemny | `#5b21b6` |
| Planowanie Wyprawy | Zielony ciemny | `#065f46` |
| Dzień Wyjazdu | Bursztynowy | `#92400e` |
| Aktywna Sesja | Morski / Teal | `#0e7490` |
| Galeria | Fioletowy jasny | `#7c3aed` |
| Ustawienia / Słowniki | Szary | `#374151` |

---

## Kluczowe Zasady UX

1. **Offline zawsze działa**: sesja, oś czasu, formularz zasadzki, mapy – wszystko offline.
2. **Rejestracja połowu = maks. 3 kroki**: zdjęcie → gatunek/waga/długość → zapisz.
3. **Metadata automatyczna**: GPS, godzina, pogoda i solunar nigdy nie są wpisywane ręcznie.
4. **Tymczasowe mapy**: po zakończeniu sesji kasują się automatycznie (brak akcji użytkownika).
5. **Sprzęt: delta-zmiana**: przy zmianie zestawu wystarczy edytować tylko to, co się zmieniło.
6. **Limit zdjęć**: max 5 na karcie połowu + komunikat przy próbie 6.
7. **Motyw**: przełącznik jasny/ciemny/kolorowy musi być łatwo dostępny z poziomu karty sesji.
8. **Udostępnianie z przełącznikiem**: auto-opis można wyłączyć przed wysłaniem.
9. **Backup dwukrotny**: raz przed wyjazdem (offline-safety) i raz po powrocie (kompletny zapis).
10. **Łowisko = mapa topo**: każde łowisko ma zapisany wycinek mapy topograficznej jako stały podgląd.

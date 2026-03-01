# Plan Implementacji – Śledzik brań (SIEDZI!)

> **Źródło prawdy:** Ten dokument jest głównym planem implementacji (szczegółowe fazy, deliverable, mapowanie ekranów).  
> `main_prompt.md` – wizja wysokopoziomowa; `implementation_plan.md` – wykonanie krok po kroku.  
> Dokument łączy specyfikację z `main_prompt.md`, przepływy z `app_documentation.md`, design system z `design-system.md` oraz flowchart z `flowchart.md`.  
> Każda faza kończy się checkpointem umożliwiającym kontynuację według metodologii Vibe Coding.
>
> **Reguły:** `.cursor/rules/auto_rules.mdc` – autonomia agenta, Git (sygnał przy braku repo, commit+push przy zakończeniu sesji).

---

## Stan wyjściowy

| Zasób | Status |
|-------|--------|
| Prototypy HTML (ekrany 01–26) | ✅ Gotowe w `screens/` |
| Flowchart Mermaid | ✅ `flowchart.md` |
| Design system | ✅ `design-system.md` |
| Specyfikacja ekranów | ✅ `app_documentation.md` |
| Projekt Android (Kotlin) | ✅ W trakcie – `SIEDZIapp/` |

---

## Status realizacji (aktualizowany przy każdej sesji)

| Etap | Faza | Checkpoint | Status |
|------|------|------------|--------|
| I | 1.1 | Baza Room + SQLCipher | ✅ |
| I | 1.2 | Słownik ryb, lista, szczegóły | ✅ |
| I | 1.3 | Splash, Onboarding, Dashboard, Disclaimery | ✅ |
| II | 2.1 | CRUD łowisk, import CSV/JSON | ✅ |
| II | 2.2 | Mapa w formularzu (OsmDroid) | ✅ 2.2.1–2.2.4; GugikMapProvider, MapCropScreen, OfflineTileCache |
| II | 2.3 | Pogoda, Solunar, Dzień Wyjazdu | ❌ |
| III | 3.1 | Trip/Session, PlanningFlow, Meldunek, Hub | ✅ |
| III | 3.2 | TackleSetup, Oś Czasu, Formularz Zasadzki | ✅ |
| III | 3.3 | Rejestracja Okazu, Catch | ✅ |
| IV | 4.x | Dashboard Wyprawy, Historia, Galeria | 🔶 4.1.1, 4.1.4 |
| V | 5.x | Drive, MD4, Testy | ❌ |

**Ostatnia aktualizacja:** Faza 3.3 (marzec 2026)

---

## Etap I: Fundamenty i Architektura (Setup)

### Faza 1.1: Konfiguracja środowiska Android i bazy danych

**Cel:** Projekt Kotlin + Room + SQLCipher uruchamiający się na emulatorze/urządzeniu.

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 1.1.1 | Utworzenie projektu Android (Kotlin, minSdk 26, targetSdk 35) | `build.gradle.kts`, struktur katalogów |
| 1.1.2 | Konfiguracja Room + SQLCipher | `build.gradle.kts`, `SiedziDatabase.kt` |
| 1.1.3 | Klucz szyfrowania (Secure SharedPrefs lub Keystore) | `EncryptionProvider.kt` |
| 1.1.4 | Pusta baza z placeholder schema v1 | `@Database`, migracje podstawowe |

**Schema Room – wersja bazowa (placeholder):**
```kotlin
@Entity(tableName = "app_meta")
data class AppMeta(@PrimaryKey val id: Int, val onboarding_completed: Boolean)

@Entity(tableName = "settings")
data class Settings(@PrimaryKey val id: Int, val theme: String)
```

**Checkpoint 1.1:** Aplikacja się buduje, baza zaszyfrowana jest inicjalizowana, log potwierdza połączenie.

---

### Faza 1.2: Moduł Słownika Ryb

**Cel:** Import danych ryb z HTML, widok listy i szczegółów.

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 1.2.1 | Model `FishSpecies` w Room | `FishSpecies.kt`, DAO, migracja |
| 1.2.2 | Parser HTML → `FishSpecies` | `FishHtmlParser.kt` (lub asset JSON po konwersji) |
| 1.2.3 | Seed bazy przy pierwszym uruchomieniu | `DatabaseSeeder.kt` |
| 1.2.4 | Ekran listy gatunków (search, filtrowanie) | `FishListScreen.kt` |
| 1.2.5 | Ekran szczegółów gatunku | `FishDetailScreen.kt` |

**Schema – rozszerzenie:**
```kotlin
@Entity(tableName = "fish_species")
data class FishSpecies(
    @PrimaryKey val id: String,
    val name: String,
    val nameLatin: String?,
    val description: String?,
    val minSize: Int?, // cm
    val closedSeason: String?,
    val imageUri: String?
)
```

**Mapowanie na prototypy:** Brak dedykowanego ekranu w screens – słownik służy do dropdownów w Formularzu Zasadzki i Karcie Połowu. Widok może być prostą listą z wyszukiwarką.

**Checkpoint 1.2:** Użytkownik widzi listę gatunków ryb z wyszukiwaniem i szczegółami.

---

### Faza 1.3: Moduł Prawny (Disclaimery) i nawigacja bazowa

**Cel:** Ekrany prawne, splash, onboarding i nawigacja do Dashboardu.

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 1.3.1 | Splash Screen | `screens/01-splash-screen.html` → `SplashScreen.kt` |
| 1.3.2 | Onboarding (24–26) lub uproszczony pierwszy run | `OnboardingScreen.kt`, flaga `onboardingCompleted` |
| 1.3.3 | Dashboard główny (5 kafelków) | `screens/02-dashboard.html` → `DashboardScreen.kt` |
| 1.3.4 | Nawigacja: NavHost, routes | `SiedziNavGraph.kt`, `SiedziRoutes.kt` |
| 1.3.5 | Ekran Disclaimera (regulamin / ryzyko wędkowania) | `DisclaimerScreen.kt`, wyświetlany raz lub z linku w Ustawieniach |

**Checkpoint 1.3:** Użytkownik przechodzi: Splash → (Onboarding) → Dashboard. Kafelki nawigują do placeholderów ekranów.

---

## Etap II: Przygotowanie do Wyprawy (Pre-Trip)

### Faza 2.1: Zarządzanie bazą łowisk

**Cel:** CRUD łowisk, import CSV/XLSX, wyszukiwanie.

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 2.1.1 | Model `Fishery`, DAO | `Fishery.kt`, `FisheryDao.kt` |
| 2.1.2 | Import CSV/XLSX (wbudowany asset lub plik) | `FisheryImporter.kt` |
| 2.1.3 | Ekran listy łowisk | `screens/23-zarzadzanie-lowiskami.html` → `FisheryListScreen.kt` |
| 2.1.4 | Formularz dodawania/edycji łowiska | `screens/06-planowanie-nowe-lowisko.html` → `FisheryFormScreen.kt` |
| 2.1.5 | Import `fisheries_clean.json` do bazy | `FisheryJsonImporter.kt`, seed przy instalacji lub z Ustawień |

**Schema:**
```kotlin
@Entity(tableName = "fisheries")
data class Fishery(
    @PrimaryKey val id: String,
    val name: String,
    val address: String?,
    val lat: Double,
    val lon: Double,
    val stationNote: String?,
    val speciesIds: List<String>, // JSON lub relacja
    val topoClipUri: String?,
    val createdAt: Long
)
```

**Format `fisheries_clean.json`** (asset, ~1269 łowisk):
```json
{ "name", "region", "city", "address", "latitude", "longitude", "sortScore", "pinVerified", "geoportalLink", "source", "osmMatch" }
```
Mapowanie: `name`→name, `address`→address, `latitude`/`longitude`→lat/lon. Opcjonalnie: region/city w stationNote, geoportalLink do przyszłego rozszerzenia.

**Checkpoint 2.1:** Użytkownik dodaje łowisko, wyszukuje, importuje z pliku (CSV lub JSON).

---

### Faza 2.2: Integracja map (GUGiK)

**Cel:** Ortofotomapa i topograficzna, cache offline.

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 2.2.1 | Integracja GUGiK WMTS / TMS | `GugikMapProvider.kt` |
| 2.2.2 | Widok mapy w formularzu łowiska (wybór GPS) | Mapa w `FisheryFormScreen` |
| 2.2.3 | Kadrowanie i zapis wycinka topo | `screens/23` – sekcja crop |
| 2.2.4 | Pobieranie tile’ów do cache | `OfflineTileCache.kt` |

**Checkpoint 2.2:** Użytkownik wybiera punkt na mapie, zapisuje wycinek topo dla łowiska.

---

### Faza 2.3: Moduł Pogody i Solunarny

**Cel:** Open-Meteo, Open Source Solunar, cache.

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 2.3.1 | Klient Open-Meteo API | `OpenMeteoClient.kt`, cache w Room |
| 2.3.2 | Obliczenia solunarne (biblioteka OS) | `SolunarCalculator.kt` |
| 2.3.3 | Widżet pogody (Weather Strip) | `design-system.md` §7.1 |
| 2.3.4 | Ekran Dzień Wyjazdu (08) | `screens/08-dzien-wyjazdu.html` → `DepartureDayScreen.kt` |

**Checkpoint 2.3:** Użytkownik widzi pogodę i solunar na ekranie przed wyjazdem.

---

## Etap III: Aktywny Połów (MVP Core)

### Faza 3.1: Tworzenie Dziennika Wyjazdu i logowanie sesji

**Cel:** Trip/Session entity, flow od planowania do meldunku.

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 3.1.1 | Model `Trip`, `Session` | `Trip.kt`, `Session.kt`, DAO |
| 3.1.2 | Planowanie: ekrany 04–07 | `PlanningFlowScreen.kt` (kompozycja) |
| 3.1.3 | Zapis wyprawy po analizie | `SaveTripUseCase.kt` |
| 3.1.4 | Aktywna sesja: meldunek (09) | `screens/09-aktywna-sesja-meldunek.html` → `CheckInScreen.kt` |
| 3.1.5 | Ekran Sesji (hub) – 10 | `SessionHubScreen.kt` |

**Schema:**
```kotlin
@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey val id: String,
    val fisheryId: String,
    val startDate: Long,
    val endDate: Long,
    val strategyNote: String?,
    val createdAt: Long
)

@Entity(tableName = "sessions")
data class Session(
    @PrimaryKey val id: String,
    val tripId: String,
    val fisheryId: String,
    val startTime: Long,
    val endTime: Long?,
    val lat: Double?,
    val lon: Double?,
    val isActive: Boolean
)
```

**Checkpoint 3.1:** Użytkownik planuje wyprawę, zapisuje, uruchamia sesję i melduje się na stanowisku.

---

### Faza 3.2: System Kart Brań

**Cel:** Formularz Zasadzki, Oś Czasu, zmiana zestawu.

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 3.2.1 | Model `TackleSetup`, `TimelineEntry` | `TackleSetup.kt`, `TimelineEntry.kt` |
| 3.2.2 | Formularz Zasadzki (11) | `TackleFormScreen.kt` |
| 3.2.3 | Oś Czasu Sesji (12) | `SessionTimelineScreen.kt` |
| 3.2.4 | Zmiana zestawu (13) – kopiowanie, delta-edycja | `ChangeSetupScreen.kt` |
| 3.2.5 | Słowniki sprzętu (22) – wędki, przynęty, haczyki | `DictRodsScreen.kt`, `DictBaitsScreen.kt`, `DictHooksScreen.kt` |

**Schema:**
```kotlin
@Entity(tableName = "tackle_setups")
data class TackleSetup(
    @PrimaryKey val id: String,
    val rodId: String?,
    val reelId: String?,
    val lineId: String?,
    val baitId: String?,
    val hookId: String?,
    val hasBoat: Boolean,
    val hasEchosounder: Boolean,
    val customJson: String? // dla dowolnych pól
)

@Entity(tableName = "timeline_entries")
data class TimelineEntry(
    @PrimaryKey val id: String,
    val sessionId: String,
    val tackleSetupId: String,
    val timestamp: Long,
    val type: String, // "cast" | "catch"
    val catchId: String? // gdy type == "catch"
)
```

**Checkpoint 3.2:** Użytkownik zarzuca wędkę, widzi wpisy na osi czasu, zmienia zestaw i zarzuca ponownie.

---

### Faza 3.3: Rejestracja Okazu

**Cel:** Zdjęcia, Karta Połowu, auto-metadane.

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 3.3.1 | Model `Catch` | `Catch.kt`, `CatchDao.kt` |
| 3.3.2 | Ekran zdjęć (14) – aparat, max 5 | `CatchPhotoScreen.kt` |
| 3.3.3 | Karta Połowu (15) – gatunek, waga, długość, nickname | `CatchCardScreen.kt` |
| 3.3.4 | Auto-metadane: GPS, czas, pogoda, solunar, zestaw | `CatchMetadataService.kt` |
| 3.3.5 | Zapisywanie i wyświetlanie na Osi Czasu | Integracja z `SessionTimelineScreen` |

**Schema:**
```kotlin
@Entity(tableName = "catches")
data class Catch(
    @PrimaryKey val id: String,
    val sessionId: String,
    val timelineEntryId: String,
    val speciesId: String,
    val weightKg: Double,
    val lengthCm: Int?,
    val nickname: String?,
    val lat: Double?,
    val lon: Double?,
    val timestamp: Long,
    val weatherSnapshot: String?, // JSON
    val solunarSnapshot: String?,
    val tackleSetupId: String,
    val photoUris: List<String> // JSON lub relacja
)
```

**Checkpoint 3.3:** Użytkownik robi zdjęcie, wypełnia gatunek i wagę, zapisuje – połów pojawia się na osi czasu z tagami.

---

## Etap IV: Analityka i Retrospekcja (Post-Trip)

### Faza 4.1: Dashboard Wyprawy

**Cel:** Statystyki sesji, chmura tagów gatunków.

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 4.1.1 | Karta Sesji (16) – auto-generowana | `SessionCardScreen.kt` |
| 4.1.2 | Statystyki: waga, długość, ilość | `SessionStatsViewModel.kt` |
| 4.1.3 | Chmura tagów ryb (avatary gatunków) | Komponent `SpeciesTagCloud.kt` |
| 4.1.4 | Bottom Sheet „Zakończ Sesję" | `design-system.md` §7.21 |

**Checkpoint 4.1:** Po zakończeniu sesji użytkownik widzi kartę z podsumowaniem i statystykami.

---

### Faza 4.2: Wykresy czasowe i notatki strategiczne

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 4.2.1 | Kaskadowy wykres brań (histogram czasu) | `CatchChartComponent.kt` |
| 4.2.2 | Notatki strategiczne na karcie sesji/tripu | Pole `strategyNote`, edycja w szczegółach |

**Checkpoint 4.2:** Użytkownik widzi wykres aktywności i może edytować notatki.

---

### Faza 4.3: Rejestr i Galeria Historyczna

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 4.3.1 | Historia Wyjazdów (17–18) | `HistoryListScreen.kt`, `SessionDetailScreen.kt` |
| 4.3.2 | Galeria Prywatna (19–20) | `GalleryScreen.kt`, `PhotoCarouselScreen.kt` |
| 4.3.3 | Filtrowanie: data, łowisko, gatunek | `HistoryFilters.kt` |
| 4.3.4 | Udostępnianie z auto-opisem | Share intent, `AutoDescriptionBuilder.kt` |

**Checkpoint 4.3:** Użytkownik przegląda historię, galerię, filtruje i udostępnia zdjęcia.

---

## Etap V: Bezpieczeństwo i Publikacja

### Faza 5.1: Integracja z Google Drive

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 5.1.1 | Google Drive API – autoryzacja | `DriveAuthManager.kt` |
| 5.1.2 | Backup: dane + zdjęcia | `BackupToDriveUseCase.kt` |
| 5.1.3 | Restore | `RestoreFromDriveUseCase.kt` |
| 5.1.4 | Ekran Ustawień (21) – status sync, eksport ZIP | `SettingsScreen.kt` |

**Checkpoint 5.1:** Użytkownik synchronizuje dane z Drive, eksportuje ZIP.

---

### Faza 5.2: Optymalizacja UI pod MD4

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 5.2.1 | High Contrast Mode | Zgodność z `design-system.md` §2.7 |
| 5.2.2 | Duże touch-targety (min 48dp) | Przejrzenie wszystkich ekranów |
| 5.2.3 | Tryb ciemny domyślny | Zgodnie z design-system |

**Checkpoint 5.2:** Aplikacja spełnia wymagania MD4 dla deszczu i nocy.

---

### Faza 5.3: Testy końcowe i publikacja

| # | Zadanie | Deliverable |
|---|---------|-------------|
| 5.3.1 | Testy jednostkowe (DAO, ViewModels) | `*Test.kt` |
| 5.3.2 | Testy UI (Compose) | `*ScreenTest.kt` |
| 5.3.3 | Checklist publikacji (Play Store) | `RELEASE_CHECKLIST.md` |

**Checkpoint 5.3:** Aplikacja gotowa do publikacji.

---

## Mapowanie ekranów prototypu → Compose

| Ekran HTML | Compose Screen | Faza |
|------------|----------------|------|
| 01-splash-screen | SplashScreen | 1.3 |
| 02-dashboard | DashboardScreen | 1.3 |
| 03-wspomnienia | WspomnieniaScreen | 4.3 |
| 04–07 planowanie | PlanningFlowScreen(s) | 2.1, 2.3, 3.1 |
| 08-dzien-wyjazdu | DepartureDayScreen | 2.3 |
| 09-meldunek | CheckInScreen | 3.1 |
| 10-hub | SessionHubScreen | 3.1 |
| 11-formularz-zasadzki | TackleFormScreen | 3.2 |
| 12-os-czasu | SessionTimelineScreen | 3.2 |
| 13-zmiana-zestawu | ChangeSetupScreen | 3.2 |
| 14–15 rejestracja | CatchPhotoScreen, CatchCardScreen | 3.3 |
| 16-zakonczenie | SessionCardScreen + BottomSheet | 4.1 |
| 17–18 historia | HistoryListScreen, SessionDetailScreen | 4.3 |
| 19–20 galeria | GalleryScreen, PhotoCarouselScreen | 4.3 |
| 21–23 ustawienia | SettingsScreen, DictScreen, FisheryManageScreen | 2.1, 5.1 |
| 24–26 onboarding | OnboardingScreen(s) | 1.3 |

---

## Przepływ przy wznawianiu prac (Vibe Coding)

1. Otwórz `implementation_plan.md` i znajdź ostatni ukończony Checkpoint.
2. Sprawdź `design-system.md` dla komponentów UI wymaganych w bieżącej fazie.
3. Zweryfikuj spójność schematu Room – migracje.
4. Kontynuuj od implementacji UI dla logiki biznesowej z poprzedniej fazy.

---

## Kolejność zalecana na start

**Najbliższy krok:** Faza 3.3 – Rejestracja Okazu (Catch, zdjęcia, Karta Połowu).  
Alternatywnie: Faza 2.3 (pogoda, solunar) lub Faza 3.2.5 (Słowniki sprzętu – DictRodsScreen, DictBaitsScreen, DictHooksScreen).

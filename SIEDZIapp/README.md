# SIEDZI! – Dziennik Połowów

Aplikacja mobilna dziennika wędkarskiego (Android, Kotlin, Jetpack Compose).  
Plan implementacji: `../implementation_plan.md`.

## Wymagania

- Android Studio Ladybug lub nowszy
- JDK 17
- Android SDK (minSdk 26, targetSdk 35)

**Uwaga:** Plik `local.properties` (ścieżka `sdk.dir`) jest w `.gitignore`. Przy pierwszym otwarciu projektu w Android Studio zostanie wygenerowany automatycznie.

## Budowanie

```bash
./gradlew assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`

## Uruchomienie na emulatorze

```bash
./gradlew installDebug
adb shell am start -n pl.siedzi.app/.MainActivity
```

## Struktura projektu (aktualna)

```
app/src/main/java/pl/siedzi/app/
├── SiedziApplication.kt
├── MainActivity.kt
├── data/
│   ├── db/SiedziDatabase.kt          # v4, migracje 1→2→3→4
│   ├── entity/                       # AppMeta, Settings, FishSpecies, Fishery, Trip, Session
│   ├── dao/                          # + FisheryDao, TripDao, SessionDao
│   ├── FisheryImporter.kt            # CSV
│   ├── FisheryJsonImporter.kt        # fisheries_clean.json
│   └── FishSpeciesLoader.kt
├── di/                              # DatabaseModule, SeedModule
├── navigation/                      # SiedziRoutes, SiedziNavGraph
├── ui/
│   ├── dashboard/DashboardScreen.kt
│   ├── splash/, onboarding/, disclaimer/
│   ├── fish/                        # FishListScreen, FishDetailScreen
│   ├── fishery/                     # FisheryListScreen, FisheryFormScreen
│   ├── map/OsmMapView.kt            # mapa OSM w formularzu łowiska
│   ├── planning/                    # PlanningFlowScreen, PlanningViewModel
│   ├── session/                     # CheckInScreen, SessionHubScreen
│   ├── placeholder/PlaceholderScreen.kt
│   └── theme/
├── util/                            # EncryptionProvider, DatabaseInitializer
└── assets/
    ├── fish_species.json
    ├── fisheries_sample.csv
    └── fisheries_clean.json         # ~1269 łowisk
```

## Checkpointy (zgodnie z implementation_plan.md)

### Etap I – Fundamenty ✅
- **1.1** – Room + SQLCipher, EncryptionProvider, schema v1
- **1.2** – FishSpecies, seed JSON, FishListScreen, FishDetailScreen
- **1.3** – Splash, Onboarding, Dashboard, nawigacja, Disclaimery

### Etap II – Przygotowanie do Wyprawy
- **2.1** ✅ – Fishery CRUD, FisheryImporter CSV, FisheryJsonImporter, FisheryListScreen, FisheryFormScreen
- **2.2** ⚠️ – Mapa OSM w FisheryFormScreen (OsmDroid); brak GUGiK WMTS, crop topo, OfflineTileCache
- **2.3** ❌ – Pogoda, Solunar, DepartureDayScreen

### Etap III – Aktywny Połów (MVP Core)
- **3.1** ✅ – Trip, Session, TripDao, SessionDao; PlanningFlowScreen (wybór łowiska, daty, notatka); CheckInScreen; SessionHubScreen
- **3.2** ❌ – TackleSetup, TimelineEntry, Formularz Zasadzki, Oś Czasu
- **3.3** ❌ – Catch, zdjęcia, Karta Połowu

### Etap IV–V
- **4.x, 5.x** – nie rozpoczęte

## Mapowanie ekranów (implementation_plan § Mapowanie)

| Ekran HTML | Compose | Status |
|------------|---------|--------|
| 01 | SplashScreen | ✅ |
| 02 | DashboardScreen | ✅ |
| 04 | Planowanie: Wybór Daty | ⚠️ w PlanningFlowScreen (pojedynczy ekran) |
| 05 | Planowanie: Wyszukaj Łowisko | ⚠️ w PlanningFlowScreen |
| 06 | Planowanie: Nowe Łowisko | ✅ FisheryFormScreen (osobno) |
| 07 | Planowanie: Strateg/Analityk | ⚠️ brak (tylko pole notatki) |
| 08 | DepartureDayScreen | ❌ |
| 09 | CheckInScreen | ✅ |
| 10 | SessionHubScreen | ✅ |
| 11–16, 17–26 | Placeholders / do zrobienia | ❌ |

## Następny krok (Vibe Coding)

1. Otwórz `implementation_plan.md` – ostatni ukończony: **Checkpoint 3.1**
2. Kolejny: **Faza 3.2** – System Kart Brań (TackleSetup, Oś Czasu, Formularz Zasadzki)
3. Przed 3.2 opcjonalnie: **Faza 2.3** (pogoda/solunar) lub **2.2** (GUGiK, crop)

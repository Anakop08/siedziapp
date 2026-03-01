# SIEDZI! – Dziennik Połowów

Aplikacja mobilna dziennika wędkarskiego (Android, Kotlin, Jetpack Compose).

## Wymagania

- Android Studio Ladybug lub nowszy
- JDK 17
- Android SDK (minSdk 26, targetSdk 35)

**Uwaga:** Plik `local.properties` (ścieżka `sdk.dir`) jest w `.gitignore`. Przy pierwszym otwarciu projektu w Android Studio zostanie wygenerowany automatycznie. Można go też utworzyć ręcznie z zawartością: `sdk.dir=C\:\\ścieżka\\do\\Android\\Sdk`

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

## Struktura projektu

```
app/src/main/java/pl/siedzi/app/
├── SiedziApplication.kt
├── MainActivity.kt
├── data/
│   ├── db/SiedziDatabase.kt
│   ├── entity/              # AppMeta, Settings, FishSpecies
│   ├── dao/                 # AppMetaDao, SettingsDao, FishSpeciesDao
│   └── FishSpeciesLoader.kt # Import z JSON
├── di/
├── navigation/              # SiedziRoutes, SiedziNavGraph
├── ui/
│   ├── HomeScreen.kt
│   ├── fish/                # FishListScreen, FishDetailScreen, ViewModels
│   └── theme/
├── util/
└── assets/fish_species.json # 10 gatunków (karp, amur, sandacz...)
```

## Checkpoint 1.1 ✅

- [x] Projekt Android (Kotlin, Compose)
- [x] Room + SQLCipher (zaszyfrowana baza)
- [x] EncryptionProvider (klucz w SecureStorage)
- [x] Placeholder schema: `app_meta`, `settings`
- [x] Aplikacja się buduje i uruchamia

## Checkpoint 1.2 ✅

- [x] Model FishSpecies w Room
- [x] Seed danych z JSON (10 gatunków: karp, amur, sandacz, szczupak…)
- [x] Ekran listy z wyszukiwaniem
- [x] Ekran szczegółów gatunku (wymiar ochronny, okres ochronny)
- [x] Nawigacja: Home → Słownik ryb → Szczegóły

## Checkpoint 1.3 ✅

- [x] Splash Screen (logo, gradient)
- [x] Onboarding (witaj + 3 karty wartości, Zaczynamy/Pomiń)
- [x] Dashboard z 5 kafelkami (Sesja, Planowanie, Wspomnienia, Galeria, Ustawienia)
- [x] Placeholdery dla przyszłych ekranów
- [x] Ekran Disclaimera (dostępny z Ustawień)

## Następny krok

Faza 2.1: Zarządzanie bazą łowisk – model Fishery, import CSV/XLSX, lista i formularz.

# Raport audytu UI/MD3 – ekrany Compose SIEDZI!

**Data:** 1 marca 2026  
**Wykonano:** zgodnie z `.cursor/skills/ui-md3-quality-check/SKILL.md` i `design-system.md`

---

## Zakres audytu

Przeanalizowano **18 ekranów** (wszystkie pliki `*Screen.kt` oprócz minimalnego PlaceholderScreen):

| Ekran | Plik |
|-------|------|
| Splash | SplashScreen.kt |
| Onboarding | OnboardingScreen.kt |
| Dashboard | DashboardScreen.kt |
| Disclaimer | DisclaimerScreen.kt |
| Fish List | FishListScreen.kt |
| Fish Detail | FishDetailScreen.kt |
| Fishery List | FisheryListScreen.kt |
| Fishery Form | FisheryFormScreen.kt |
| Map Crop | MapCropScreen.kt |
| Planning Flow | PlanningFlowScreen.kt |
| Check-In | CheckInScreen.kt |
| Session Hub | SessionHubScreen.kt |
| Tackle Form | TackleFormScreen.kt |
| Session Timeline | SessionTimelineScreen.kt |
| Change Setup | ChangeSetupScreen.kt |
| Catch Photo | CatchPhotoScreen.kt |
| Catch Card | CatchCardScreen.kt |
| Session Card | SessionCardScreen.kt |

---

## Checklist – podsumowanie

### 1. Zgodność z Material Design 3 ✅
- **Komponenty:** Wszystkie ekrany używają `material3` (Scaffold, TopAppBar, Card, Button, TextField, itd.)
- **Typografia:** `MaterialTheme.typography` stosowana konsekwentnie (tytuły, body, label)
- **Zaokrąglenia:** 14dp pola formularzy, 18dp przyciski CTA – zgodne z design-system §7.17, §7.18
- **Ikony:** Material Icons (Filled/Outlined), ArrowBack w wersji AutoMirrored

### 2. Układ na ekranie ✅
- **Przewijanie:** Ekrany z długą treścią mają `verticalScroll(rememberScrollState())` lub `LazyColumn`/`LazyVerticalGrid`
- **Listy:** FisheryListScreen, FishListScreen – LazyColumn z `weight(1f)` (poprawiono FishListScreen)
- **Formularze:** FisheryFormScreen, PlanningFlowScreen, TackleFormScreen, ChangeSetupScreen, CatchCardScreen – verticalScroll

### 3. Odstępy ✅
- **Padding główny:** 16.dp stosowany spójnie
- **Content padding:** `Scaffold` padding przekazywany do treści
- **Spacing między elementami:** 8.dp, 12.dp, 16.dp – wartości spójne
- **Poprawka:** Przyciski z ikoną+tekstem – Spacer z `Modifier.size(8.dp)` zamiast `height(8.dp)` (w Row)

### 4. Przewijanie ✅
- `verticalScroll` na formularzach
- `LazyColumn` / `LazyVerticalGrid` z `contentPadding`
- Brak `fillMaxHeight()` wewnątrz scroll bez ograniczenia

### 5. Spójność elementów obok siebie ✅
- **CTA obok siebie:** `Modifier.weight(1f)` w Row – SessionTimelineScreen, CatchPhotoScreen
- **Formularze:** Waga/Długość w CatchCardScreen – `weight(1f)`
- **Statystyki:** SessionCardScreen – StatCard z `weight(1f)`
- **Ikony + tekst:** `verticalAlignment = Alignment.CenterVertically`

### 6. Kontrast i czytelność ✅
- **Design-system §2.7:** Etykiety min. rgba(255,255,255,0.55)
- **Poprawki:** 
  - SessionCardScreen StatCard: label 0.5 → 0.55
  - CatchPhotoScreen AddPhotoSlot: tekst/ikona 0.35 → 0.55/0.65
  - OnboardingScreen „Pomiń”: 0.5 → 0.55
- Tekst na ciemnym tle: nagłówki #ffffff, opisy 0.65–0.75

### 7. Design system ✅
- **Kolory obszarów:** CyanTeal (#22d3ee), EmeraldGreen, Amber zgodne z design-system
- **Border-radius:** 14dp pola, 18dp CTA
- **Ikony:** Material Icons Outlined/Filled – brak emoji w nawigacji

---

## Wprowadzone poprawki

| Plik | Zmiana |
|------|--------|
| FishListScreen.kt | `LazyColumn` – dodano `Modifier.weight(1f)` – poprawny scroll w Column |
| SessionCardScreen.kt | StatCard label: alpha 0.5 → 0.55 (min. design-system) |
| CatchPhotoScreen.kt | AddPhotoSlot: ikona 0.35→0.55, tekst 0.35→0.65 (placeholder) |
| PlanningFlowScreen.kt | Przyciski z ikoną: Spacer `height(8.dp)` → `size(8.dp)` (poprawny odstęp w Row) |
| OnboardingScreen.kt | TextButton „Pomiń”: alpha 0.5 → 0.55 |
| CheckInScreen.kt | Button z ikoną: Spacer `height(8.dp)` → `size(8.dp)` |

---

## Raport UI/MD3 – finalny

### ✅ Zgodne
- Material Design 3 (material3, typografia, zaokrąglenia)
- Przewijanie (verticalScroll, LazyColumn)
- Odstępy (16.dp standard)
- Spójność elementów (weight(1f))
- Kontrast po poprawkach
- Design system (kolory, radius)

### ❌ Do poprawy (brak)
Wszystkie zidentyfikowane problemy zostały naprawione.

---

## Rekomendacje na przyszłość

1. **Unikać** `Spacer(Modifier.height(X.dp))` wewnątrz `Row`/`Button` – używać `Modifier.size(X.dp)` lub `Modifier.width(X.dp)`.
2. **Przy nowych ekranach:** zawsze sprawdzać, czy `LazyColumn` w `Column` ma `weight(1f)`.
3. **Placeholder/tekst na ciemnym tle:** minimum 0.55 (etykiety), 0.65 (placeholder).
4. **Testy na urządzeniu:** sprawdzić SessionCardScreen, CatchPhotoScreen i FishListScreen na różnych rozdzielczościach.

5. **Tytuły z nazwą łowiska** (marzec 2026): SessionHubScreen, TackleFormScreen, SessionTimelineScreen, ChangeSetupScreen – długa nazwa łowiska nie mieściła się w TopAppBar. Wprowadzono `TopAppBarTitle` z `fillMaxWidth`, `maxLines=1`, `overflow=Ellipsis` i `basicMarquee()` – automatyczne przewijanie gdy tekst przekracza szerokość.

---

## Weryfikacja skilla ui-md3-quality-check (1 marca 2026)

Skill zaktualizowano na podstawie audytu:
- **Sekcja 4 (Przewijanie):** dodano punkt o `LazyColumn` + `Modifier.weight(1f)` w `Column`
- **Sekcja 6 (Kontrast):** dodano min. 0.65 dla placeholder
- **Szybkie wzorce:** dodano wzorzec Spacer w Row (size zamiast height)
- **Nowa sekcja:** „Typowe błędy” – tabela z najczęstszymi problemami i poprawkami

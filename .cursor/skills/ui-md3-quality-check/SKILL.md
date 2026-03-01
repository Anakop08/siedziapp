---
name: ui-md3-quality-check
description: Sprawdza implementacje UI pod kątem zgodności z Material Design 3, prawidłowości layoutu i jakości wykonania. Stosuj po implementacji ekranów Compose, przy code review UI lub gdy użytkownik prosi o kontrolę jakości interfejsu.
---

# UI / MD3 – Kontrola jakości implementacji

## Kiedy stosować

- Po dodaniu lub modyfikacji ekranu Compose (Screen, Composable)
- Przed zakończeniem sesji, jeśli zmiany dotyczyły UI
- Gdy użytkownik prosi o sprawdzenie layoutu, spójności lub zgodności z MD3
- Przy code review plików `*Screen.kt`, `*Form*.kt`, komponentów UI

---

## Checklist – wykonaj weryfikację

Skopiuj poniższą listę i odhaczaj każde sprawdzenie. Każde ❌ wymaga poprawki.

### 1. Zgodność z Material Design 3

- [ ] Komponenty z `material3` (MaterialTheme, Button, Card, TextField, TopAppBar itd.) – nie legacy `material`
- [ ] Kolory z `MaterialTheme.colorScheme` lub theme (nie hardcodowane poza design-system)
- [ ] Typografia z `MaterialTheme.typography` (titleLarge, bodyMedium, labelSmall itd.)
- [ ] Zaokrąglenia, elevation zgodne z MD3 (np. Card 12dp, Button 18dp)
- [ ] Ikony: Material Icons (Outlined/Filled), preferowane wersje AutoMirrored gdy dostępne

### 2. Układ na ekranie – mieszczenie się treści

- [ ] Treść ma `modifier = Modifier.verticalScroll()` lub `Column` w `Scaffold` z padding – dłuższe listy nie wychodzą poza ekran
- [ ] Brak zakładania, że wszystko się zmieści na jednym ekranie (np. listy, formularze)
- [ ] Ekrany z długą treścią: `Column` + `verticalScroll(rememberScrollState())` lub `LazyColumn`

### 3. Odstępy (padding, margin)

- [ ] Standardowe paddingi: 16.dp wokół głównej treści (Screen padding)
- [ ] Spójność: ten sam poziom padding (np. 16.dp) dla podobnych sekcji
- [ ] Odstępy od krawędzi: Scaffold content padding stosowany konsekwentnie
- [ ] Spacing między elementami: `Arrangement.spacedBy()`, `Spacer`, `Modifier.padding()` – spójne wartości (np. 8.dp, 12.dp, 16.dp)
- [ ] Brak elementów przyklejonych do krawędzi bez padding

### 4. Przewijanie

- [ ] Gdy treść przekracza wysokość: `verticalScroll` lub `LazyColumn` – przewijanie działa
- [ ] `Scaffold` content: `Column` z `verticalScroll` – nie blokowanie scroll
- [ ] **LazyColumn w Column:** `LazyColumn` wewnątrz `Column` MUSI mieć `Modifier.weight(1f)` – inaczej nie będzie się przewijać (brak ograniczonej wysokości)
- [ ] Brak `Modifier.fillMaxHeight()` wewnątrz scroll bez ograniczenia – może powodować nieskończoną wysokość

### 5. Spójność elementów obok siebie

- [ ] Przyciski w jednym rzędzie: ten sam rozmiar – np. `Modifier.weight(1f)` w `Row` lub równa szerokość
- [ ] Karty / kafelki obok siebie: `Modifier.weight(1f)` lub `fillMaxWidth()` spójnie
- [ ] Ikony + tekst: wyrównanie `verticalAlignment = Alignment.CenterVertically`
- [ ] Chips, przyciski w `FlowRow`/`Row`: równy `height`, spójny `padding`

### 6. Kontrast i czytelność (design-system)

- [ ] Tekst na ciemnym tle: minimum `rgba(255,255,255,0.55)` dla etykiet, `#ffffff` dla nagłówków
- [ ] Brak opacity < 0.5 dla tekstu – nieczytelny
- [ ] Placeholder: min. `0.65` (design-system §2.7)
- [ ] Kolory akcentów z projektu (np. CyanTeal, EmeraldGreen) zgodne z design-system.md

### 7. Design system projektu (jeśli istnieje `design-system.md`)

- [ ] Ikony: Material Icons Outlined (nie emoji w nawigacji/kartach)
- [ ] Border-radius pól formularza: 14dp (wg design-system §7.17)
- [ ] CTA: wzorzec z design-system §7.18 (gradient, border-radius 18dp, ikona w box)
- [ ] Kolory obszarów (Session = Teal, Planowanie = Emerald) – spójne z design-system §2

---

## Raport po weryfikacji

Na koniec checklisty podsumuj:

```
## Raport UI/MD3
✅ Zgodne: [lista punktów]
❌ Do poprawy: [lista z numerami linii / komponentów]
```

Dla każdego ❌ wskaż plik, linię lub komponent oraz proponowaną poprawkę.

---

## Szybkie wzorce (Compose)

**Długi ekran z przewijaniem:**
```kotlin
Column(modifier = Modifier
    .fillMaxWidth()
    .padding(paddingValues)
    .verticalScroll(rememberScrollState())) { ... }
```

**Dwa przyciski obok siebie (równe):**
```kotlin
Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
    Button(modifier = Modifier.weight(1f), ...)
    OutlinedButton(modifier = Modifier.weight(1f), ...)
}
```

**Padding standardowy:**
- Główna treść: `padding(16.dp)` lub `padding(paddingValues)` z Scaffold
- Między sekcjami: `Spacer(Modifier.height(16.dp))` lub `Arrangement.spacedBy(12.dp)`

**Długi tytuł TopAppBar (np. nazwa łowiska):**
```kotlin
title = {
    Text(
        text = "Sesja: $fisheryName",
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth().basicMarquee()
    )
}
```
Użyj `TopAppBarTitle` gdy tytuł zawiera dynamiczną, potencjalnie długą wartość.

**Spacer w Row / Button (ikona + tekst):**
```kotlin
// ❌ Błąd – height(8.dp) w Row nie tworzy odstępu poziomego
Row { Icon(...); Spacer(Modifier.height(8.dp)); Text(...) }

// ✅ Poprawnie
Row { Icon(...); Spacer(Modifier.size(8.dp)); Text(...) }
```

---

## Typowe błędy (zweryfikowane w audycie SIEDZI!)

| Błąd | Objaw | Poprawka |
|------|-------|----------|
| `LazyColumn` w `Column` bez `weight(1f)` | Lista się nie przewija | `LazyColumn(modifier = Modifier.weight(1f), ...)` |
| `Spacer(Modifier.height(X.dp))` w `Row`/`Button` | Brak odstępu między ikoną a tekstem | `Spacer(Modifier.size(X.dp))` lub `Modifier.width(X.dp)` |
| Opacity tekstu &lt; 0.55 | Nieczytelne etykiety na ciemnym tle | Min. `0.55` (etykiety), `0.65` (placeholder) |

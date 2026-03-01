# SIEDZI! – Design System

> Plik przewodni dla designera / grafika aplikacji.
> Aktualizowany po każdej akceptacji lub zmianie wyglądu ekranu.
> Ostatnia aktualizacja: ekrany 01–26 (wszystkie zaakceptowane). Analiza spójności i plan sesji naprawczych: luty 2026.

---

## 1. Typografia

| Rola | Font | Grubaść | Rozmiar | Letter-spacing |
|------|------|---------|---------|----------------|
| Nazwa aplikacji (hero) | Roboto Condensed | 700 | 62px | -1px |
| Tagline / Label caps | Roboto | 400 | 13px | 3.5px |
| Czas w status barze | Roboto | 600 | 14px | 0.2px |
| Etykieta wersji | Roboto | 400 | 11px | 0.8px |
| Etykieta ekranu (poza telefonem) | Roboto | 400 | 11px | 1.5px |
| Nagłówek top bar | Roboto | 600 | 18px | 0 |
| Nagłówek top bar (logo) | Roboto Condensed | 700 | 26px | -0.5px |
| Nagłówek sekcji (caps) | Roboto | 500 | 11px | 1.5px |
| Tytuł karty | Roboto | 600 | 14–15px | 0 |
| Tekst pomocniczy / meta | Roboto | 400 | 10–11px | 0.5px |
| Wartośc metryk | Roboto | 700 | 18–22px | 0 |

**Import Google Fonts:**
```html
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&family=Roboto+Condensed:wght@700&display=swap" rel="stylesheet">
```

---

## 2. Kolory

### 2.1 Kolory obszarów nawigacyjnych

| Obszar | Nazwa | Kolor główny | Kolor akcentu (border/glow) |
|--------|-------|-------------|------------------------------|
| Dashboard / Main | Niebieski | `#1e40af` | `#3b82f6` |
| Wspomnienia / Historia | Fioletowy ciemny | `#5b21b6` | `#8b5cf6` |
| Planowanie Wyprawy | Zielony ciemny | `#065f46` | `#10b981` |
| Dzień Wyjazdu | Bursztynowy | `#92400e` | `#f59e0b` |
| Aktywna Sesja | Morski / Teal | `#0e7490` | `#22d3ee` |
| Galeria | Fioletowy jasny | `#7c3aed` | `#a78bfa` |
| Ustawienia / Słowniki | Szary | `#374151` | `#9ca3af` |

### 2.2 Kolory tła (Dark Mode – domyślny)

| Rola | Hex |
|------|-----|
| Tło strony (poza telefonem) | `#121212` |
| Tło telefonu / ekranu | `#101014` |
| Tło splash (gradient base) | `#0a0d12` → `#0d1117` → `#0a0f0d` |
| Obramowanie ramki telefonu | `#2a2a2e` |

### 2.3 Kolory Dashboard (Ekran 02)

| Element | Hex | Uwagi |
|---------|-----|-------|
| Tło główne | `#101014` | |  
| Cyan akcent | `#22d3ee` | Logo, glow, ikony |
| Cyan glow | `rgba(34,211,238,0.7)` | text-shadow |
| Karta ostatniej sesji (border) | `rgba(30,64,175,0.5)` | niebieski |
| Kafelek Start Sesji (border) | `rgba(34,211,238,0.5)` | |
| Kafelek Plan (gradient) | `#052016 → #073d25` | zielony |
| Kafelek Wspomnienia (gradient) | `#1e0845 → #2d1171` | fioletowy |
| Kafelek Galeria (gradient) | `#1c0e3a → #2e1263` | fioletowy jasny |
| Kafelek Ustawienia (gradient) | `#16191f → #1f2430` | szary |
| Active Session Banner (bg) | `rgba(14,116,144,0.18)` | |
| Active Session Banner (border) | `rgba(34,211,238,0.4)` | |

### 2.4 Kolory Wspomnienia (Ekran 03)

| Element | Hex |
|---------|-----|
| Tło główne | `#080b14` |
| Akcent fioletowy | `#7c3aed` / `#8b5cf6` |
| Hero banner gradient | `#1e0845 → #2d1171 → #170935` |
| Karta tripu (bg) | `rgba(139,92,246,0.07)` |

### 2.5 Kolory Planowania (Ekrany 04–07)

| Element | Hex |
|---------|-----|
| Tło główne | `#080f0a` |
| Akcent zielony | `#34d399` (emerald-400) |
| Akcent zielony ciemny | `#059669` / `#047857` |
| Zaznaczenie kalendarza (bg) | `rgba(52,211,153,0.1)` |
| Zaznaczenie kalendarza start/end | `#059669` |
| CTA gradient | `#059669 → #047857` |
| Search bar border | `rgba(52,211,153,0.35)` |


### 2.6 Kolor wiodący Splash Screen

| Element | Kolor | Hex | Opacity |
|---------|-------|-----|---------|
| Logo haczyk (stroke) | Cyan teal | `#22d3ee` | 100% |
| Wykrzyknik w nazwie | Cyan teal | `#22d3ee` | 100% |
| Tagline tekst | Cyan teal | `#22d3ee` | 65% |
| Glow logo (drop-shadow) | Cyan teal | `#22d3ee` | 45–80% |
| Kręgi ripple (border) | Cyan teal | `#22d3ee` | 12% |
| Kropki ładowania (idle) | Cyan teal | `#22d3ee` | 40–50% |
| Kropki ładowania (active) | Cyan teal | `#22d3ee` | 90% |
| Linia żyłki (dashed) | Cyan teal | `#22d3ee` | 40% |
| Bait/lure fill | Cyan teal | `#22d3ee` | 25% |
| Bait/lure stroke | Cyan teal | `#22d3ee` | 50% |

### 2.8 Kolory tekstu ogólne

| Rola | Hex |
|------|-----|
| Tekst główny (biały) | `#ffffff` |
| Tekst w status barze | `#e0e0e0` |
| Tekst pomocniczy / wersja | `rgba(255,255,255,0.18)` |
| Etykieta ekranu (poza telefonem) | `#888888` |
| Metadane telefonu (poza) | `#555555` |

### 2.7 Kontrast tekstu na ciemnym tle – ZASADY OBOWIĄZKOWE

**Stosuj przy każdym nowym ekranie.** Tekst na ciemnym tle musi być czytelny.

| Element | Minimum | Zalecane |
|---------|---------|----------|
| Tytuł / nagłówek | `#ffffff` | — |
| Opis / subtitle pod tytułem | `rgba(255,255,255,0.65)` | 0.7 |
| Etykiety (Waga:, Max:, itp.) | `rgba(255,255,255,0.75)` | — |
| Wartości wyróżnione (liczby, akcent) | kolor obszaru (np. `#34d399`) | — |
| Etykieta sekcji (sect-label, caps) | `rgba(255,255,255,0.55)` | 0.6 |
| Tekst pusty / placeholder | `rgba(255,255,255,0.65)` | — |
| Strzałki / chevron | `rgba(255,255,255,0.5)` | — |

**NIE używaj** opacity poniżej 0.5 dla tekstu na ciemnym tle – staje się nieczytelny.

---

## 3. Ikony

### 3.0 Zasady obowiązkowe – ikony nawigacyjne i w kartach

**Stosuj przy każdym nowym ekranie.** Zachowaj spójność wizualną z Dashboardem (ekran 02).

- **NIE używaj emoji** (📋, 📊, 📅, 🌿, ⚓ itp.) w kafelkach, kartach, przyciskach nawigacyjnych
- **Używaj Material Icons Outlined** – ten sam styl co kafelek „Zaplanuj Wyprawę", „Wspomnienia", „Galeria", „Ustawienia"
- **Import:** `<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">`
- **Format:** `<span class="material-icons-outlined">nazwa_ikony</span>`
- **Rozmiar:** 22px w boxie 42–44px
- **Kontener:** `overflow: hidden` na wrapperze – ikony Material mogą mieć computed width 260px i wymagają przycięcia

**Mapowanie (przykłady):** description, bar_chart, history, event_note, auto_stories, photo_library, settings, list_alt, analytics – [material.io/icons](https://fonts.google.com/icons)

**Wyjątek – ikony pogodowe:**  
Dla prognozy, warunków na miejscu, solunara – używaj **wyłącznie** `screens/weather-icons.js`:
```html
<script src="weather-icons.js"></script>
<div data-weather-icon="clear-day" data-size="28"></div>
```
NIE używaj Material Icons ani emoji do ikon pogody. Przykład: ekran 08 (Dzień Wyjazdu), ekran 09 (Meldunek). Szczegóły: sekcja 9.

---

- **System ikon:** Material Icons Outlined (Google CDN)
- **Import CDN:** `https://fonts.googleapis.com/icon?family=Material+Icons+Outlined`
- **Logo haczyka:** Ręcznie rysowany SVG (inline w HTML), viewBox 88×88

### 3.1 Logo – Haczyke Wędkarski (SVG spec)

```
viewBox: 0 0 88 88
Elementy:
  - Okrąg zewnętrzny (dekoracyjny): cx=44 cy=44 r=40, fill rgba(14,116,144,0.15)
  - Okrąg wewnętrzny: r=34, fill rgba(14,116,144,0.10)
  - Trzon haczyka: line (44,14)→(44,52), stroke #22d3ee, stroke-width 3.5
  - Krzywizna haczyka: path quad bezier (44,52)→(55,70)→(66,70)→(66,59)→(59,52)
  - Ostrze: path (59,52)→(55,58), stroke-width 2.5
  - Ucho haczyka: circle cx=44 cy=18 r=5, stroke-only, stroke-width 3
  - Otwór ucha: path quad (44,13)→(47,10), stroke-width 2.5
  - Żyłka (dashed): line (44,10)→(44,4), dash 2 2, opacity 0.4
  - Przynęta (ellipse): cx=51 cy=62 rx=4 ry=6, semi-transparent fill+stroke
Rozmiar wyświetlany: 88×88px
Filter: drop-shadow(0 0 24px rgba(34,211,238,0.55)) + animowany glow
```

### 3.2 Status Bar – ikony systemowe (SVG inline)

Trzy ikony inline SVG (16×12px) z prawej strony:
- **Zasięg GSM**: 4 prostokąty wzrastające, ostatni opacity 0.35 (brak pełnego zasięgu)
- **WiFi**: 2 łuki + wypełniona kropka, stroke `#e0e0e0` width 1.3
- **Bateria**: rect 22×12 z końcówką (2.5×5) + wypełnienie 13px (ok. 72%), fill `#e0e0e0`

---

## 4. Ramka Telefonu (Android Frame)

| Parametr | Wartość |
|----------|---------|
| Szerokość | 390px |
| Wysokość | 844px |
| Promień zaokrąglenia | 52px |
| Tło ramki | `#101014` |
| Obramowanie zewnętrzne | `2px solid #2a2a2e` |
| Drugi ring obramowania | `5px solid #1a1a1e` |
| Cień zewnętrzny | `0 30px 80px rgba(0,0,0,0.8)` |
| Inner border | `inset 0 0 0 1px #2e2e35` |
| Przycisk boczny (prawy) | `3px × 60px` at top 140px, `#2a2a2e` |
| Przyciski boczne (lewe) | `3px × 36px` × 3 (z odstępem 44px), `#2a2a2e` |

### Dynamic Island
```css
position: absolute; top: 12px; left: 50%; transform: translateX(-50%);
width: 120px; height: 34px; background: #000; border-radius: 20px;
```

### Status Bar
```css
height: 44px; padding: 14px 24px 0;
```

### Navigation Bar (Android gesture)
```css
height: 34px;
/* Zawartość: jeden pill (100×4px, rgba(255,255,255,0.3), border-radius 2px) */
```

---

## 5. Animacje

### 5.1 Animacje Splash Screen

| Nazwa | Element | Efekt | Czas | Easing |
|-------|---------|-------|------|--------|
| `breathe` | Tło gradientowe | opacity 1 → 0.85 → 1 | 6s | ease-in-out, infinite |
| `ripple` | Kręgi wodne (×4) | scale 0.6→1.2, opacity 0.6→0 | 4s | ease-out, infinite |
| Opóźnienia ripple | ring 1–4 | delay: 0s / 0.8s / 1.6s / 2.4s | – | – |
| `iconGlow` | Logo SVG | drop-shadow 18px → 32px | 3s | ease-in-out, infinite |
| `logoReveal` | Logo (wrapper) | translateY(24px)→0, scale 0.9→1, opacity 0→1 | 1.2s | cubic-bezier(0.34,1.56,0.64,1) |
| `taglineFade` | Tagline | opacity 0→1, letter-spacing 6px→3.5px | 1.8s | ease, delay 0.6s |
| `dotsAppear` | Loading dots | opacity 0→1 | 0.5s | ease, delay 1.4s |
| `dotBounce` | Każda kropka | scale 0.8→1.25, opacity 0.4→1 | 1.4s | ease-in-out, delay +0.2s |

### Zasady animacji ogólne
- Animacje wejścia logo: **spring-like easing** `cubic-bezier(0.34, 1.56, 0.64, 1)`
- Pętle ciągłe: `ease-in-out, infinite` dla efektu *breathing*
- Opóźnienia kaskadowe dla sekwencji elementów

---

## 6. Skalowanie w Przeglądarce

Ekrany wyświetlane są w Chrome jako lokalne pliki HTML. Telefon (390×844px) jest automatycznie skalowany do okna przeglądarki bez przewijania. **Standard wymiarów: ekran 02 (Dashboard)**.

```javascript
// Standard autoscale (ekran 02 = wzór, stosowany we wszystkich ekranach):
function autoScale() {
  var wrap = document.getElementById('phoneWrap');
  var headerH = 60;   // rezerwa na screen-label + padding (ekrany 01-09, 07)
  var contentH = 870; // phone (844) + phone-meta (~26) – pełna wysokość phone-wrap
  var availH = window.innerHeight - headerH;
  var availW = window.innerWidth - 40;
  var scale = Math.min(availH / contentH, availW / 400, 1);
  wrap.style.transform = 'scale(' + scale + ')';
  wrap.style.transformOrigin = 'center center';
  wrap.style.marginBottom = Math.round((844 * scale - 844) / 2) + 'px';
}
window.addEventListener('resize', autoScale);
window.addEventListener('load', autoScale);
```

**Uwaga:** Ekrany prototypowe 07.1–07.3 (testy wariantów ikon) zostały usunięte z repozytorium — służyły wyłącznie do porównania wariantów i nie są częścią flow.

```css
/* Standard layout */
html { overflow: hidden; height: 100%; }
body { overflow: hidden; height: 100vh; display: flex; flex-direction: column; align-items: center; justify-content: center; }
.phone-wrap { transform-origin: center center; }
```

---

## 7. Komponenty UI (wzorce wielokrotnego użytku)

### 7.1 Weather Strip (Ekran 02)
- Flex row, `align-items: stretch`, `gap: 10px`, `padding: 10px 12px`
- Ikona pogody: inline SVG 22×22px w boxie 44×44px (amber `rgba(251,191,36,0.14)`, border `rgba(251,191,36,0.28)`, `border-radius: 12px`)
- Kolumny tekstowe: label `10px caps`, value `14px / 500`
- Divider: `1px solid rgba(255,255,255,0.08)`
- Solunar badge: ten sam amber styl, `flex-direction: column`, emoji + text `9px bold #fbbf24`
- **Wniosek z implementacji**: ikona NIE używa `material-icons-outlined` (problem z computed width CDN = 260px); używamy inline SVG

### 7.2 Karta Sesji / Tripu (Ekrany 02, 03)
- `border-radius: 18–20px`, gradient tło, `border: 1px solid rgba(kolor,0.5)`
- Linia highlight na górze: `::before`, `height: 1px`, gradient `transparent → kolor → transparent`
- `box-shadow: 0 4px 20px rgba(kolor, 0.15)`
- Hover: `transform: scale(0.98)` przy `:active`

### 7.3 Kafelek Nawigacyjny (Ekran 02)
- Grid 2-kolumnowy, gap 12px
- `border-radius: 20px`, `min-height: 110px`
- Ikona w circle: 42×42px, `border-radius: 14px`, `background: rgba(kolor, 0.2)`
- Efekt światła: `::after` z `linear-gradient(135deg, rgba(255,255,255,0.04), transparent)`
- Tile startowy: `grid-column: 1/-1`, row flex

### 7.4 Krok-po-Kroku Indicator (Ekrany 04, 05)
```html
<div class="step-indicator">
  <div class="step-dot done"></div>
  <div class="step-label done">Termin</div>
  <div class="step-line done"></div>
  <div class="step-dot active"></div>
  ...
</div>
```
- Dot: 8×8px circle; `.done`: `rgba(52,211,153,0.4)`; `.active`: `#34d399` + glow
- Line: `height: 2px`; `.done`: `rgba(52,211,153,0.3)`

### 7.5 Kalendarz Zakresowy (Ekran 04)
- Grid 7-kolumnowy, `height: 40px` per komórka
- `range-start`: `border-radius: 20px 0 0 20px`, zielone tło
- `range-end`: `border-radius: 0 20px 20px 0`
- `range-mid`: `background: rgba(52,211,153,0.1)`
- Start/End day-num: filled circle 34×34px, `background: #059669`
- Chipy szybkiego wyboru: `border-radius: 20px`, aktywny: zielone tło + border

### 7.6 Karta Łowiska (Ekran 05)
- Flex row, `gap: 12px`, `border-radius: 18px`
- Miniatura mapy: 58×58px inline SVG (symulowane dane topograficzne)
- Zaznaczona karta: `border-color: rgba(52,211,153,0.45)`, zielone tło + checkmark circle
- Meta kolumna: gwiazdki, odległość, data ostatniej wizyty

### 7.7 Active Session Banner (cross-screen)
- Persystentny, tuż nad `.nav-bar`
- `margin: 4px 16px 0`, `border-radius: 14px`
- Background: `rgba(14,116,144,0.18)`, border: `rgba(34,211,238,0.4)`
- Animowany pulsujący dot (8×8px, cyan)
- **Widoczny na**: Dashboard, Galeria, Historia, Wspomnienia (gdy sesja aktywna)
- **Niewidoczny na**: Splash, Onboarding, ekrany wewnątrz sesji

### 7.8 Formularz GPS Łowiska (Ekran 06)
- `text-field`: `border-radius: 14px`, aktywne pole: `border: 1.5px solid rgba(52,211,153,0.5)`, `background: rgba(52,211,153,0.05)`
- Współrzędne GPS: zielony kafelek `rgba(52,211,153,0.07)` z subtytułem 9px caps
- Przycisk GPS: `border: 1px dashed rgba(52,211,153,0.35)`, ikona + dwa wiersze tekstu
- Animacja GPS dot: `@keyframes gpsPulse` – `opacity 1→0.4, scale 1→1.4` (NIE `pingPulse` – powoduje shift pozycji)
- Tag chipy gatunków: standard chips + chip `.add` z `border-style: dashed`
- Mapa tło: inline SVG topo, `height: 160px`, `border-radius: 20px`; pin haczyk z pulsującymi ringami

### 7.9 Wykres Ciśnienia (Ekran 07)
- Inline SVG `viewBox="0 0 326 44"`, `stroke: #34d399`, `stroke-width: 2`
- Wypełnienie pod linią: gradient `linearGradient` (opacity 0.4 → 0)
- Kropki na punktach danych: `r="3"`, aktywna (dziś): `r="3.5"` z `stroke: bg-color`
- Etykiety osi X: flex row, `font-size: 9px`, `color: rgba(255,255,255,0.25)`
- Badge trendu: zielone `↗ Rosnące` / czerwone `↘ Spada`

### 7.10 Karta Solunar (Ekran 07)
- Tło amber: `rgba(251,191,36,0.06)`, `border: 1px solid rgba(251,191,36,0.2)`
- Paski jakości (5 szt.): `height: 8px`, wypełniony `#fbbf24`, pół-wypełniony: gradient 50/50
- 3 okna czasowe obok siebie (`display: flex`, `gap: 10px`): label 9px caps, czas 14px/700, czas trwania, typ emoji
- Opis na dole: 11px italic, akcent na kluczowym fragmencie (`strong`)

### 7.11 Tabela Historii z różnicami (Ekran 07)
- Wiersze: `padding: 7px 0`, separator `1px solid rgba(255,255,255,0.05)`
- Badge różnicy: `.up` zielone, `.down` czerwone, `.same` szare
- Zakładki: "Przed rokiem" / Śr. 5-letnia (ten samy chip-style co filter chips)

### 7.12 Insight Badge AI (Ekran 07)
- Fioletowy lewy border: `border-left: 3px solid #7c3aed`, `border-radius: 0 14px 14px 0`
- Tło: `rgba(139,92,246,0.08)`, label 9px caps `rgba(139,92,246,0.6)`
- Akcent tekstu: `#a78bfa`

### 7.13 Kołowy Progress Pobierania (Ekran 08)
- SVG circle `r="13"`, `stroke-dasharray="81.68"` (2π×13), offset oblicza procent
- Tło okręgu: `stroke: rgba(255,255,255,.08)`, postęp: `stroke: #34d399`
- `stroke-linecap: round`, obrót: `transform="rotate(-90 16 16)"` (start od góry)
- Procent tekstowy na środku: `font-size: 8px; font-weight: 700; color: #34d399`

### 7.14 Stany Wierszy Odświeżania (Ekran 08)
- **OK** (`status-ok`): zielony dot 7×7px `::before` + tekst `#34d399`
- **Stare** (`status-old`): amber dot + przycisk `btn-refresh` z ikoną ↺, `border: 1px solid rgba(251,191,36,.3)`
- **W trakcie** (`status-loading`): obracający się spinner `14×14px` + procent `rgba(255,255,255,.3)`
- Spinner: `border-top-color: #34d399`, animacja `spin .8s linear infinite`

### 7.15 Hero Banner Dnia Wyjazdu (Ekran 08)
- Gradient: `rgba(6,95,70,.5) → rgba(4,55,40,.4)`, `border: rgba(52,211,153,.22)`
- Pulsujący dot: 6×6px, `animation: pulse 1.5s ease-in-out infinite` (opacity + scale)
- Chipy metadanych: `rgba(255,255,255,.06)` z ikoną SVG + tekst
- Radial glow: `::before` – `background: radial-gradient(circle, rgba(52,211,153,.12), transparent)`

### 7.16 Hub Card Ekran Sesji (Ekran 10)
- Karta: `border-radius: 16px`, `background: rgba(255,255,255,.03)`, `border: 1px solid rgba(255,255,255,.07)`
- Ikona: 44×44px box, `rgba(52,211,153,.12)`, **Material Icons Outlined** (description, bar_chart, history), `overflow: hidden`
- Tytuł 14px/600, opis 11px/500, strzałka `›` po prawej
- Trzy sekcje: Opis łowiska (fish-tags), Moje statystyki (stats-row), Historia wizyt (history-empty)
- Fish-tag: tekst `rgba(255,255,255,.75)`, tło `rgba(52,211,153,.1)`, border `rgba(52,211,153,.18)` — **NIE** `color: #34d399`
- Fish-tag `.warn`: tekst `#fbbf24`, tło `rgba(251,191,36,.1)`, border `rgba(251,191,36,.2)`
- Hover: `border-color: rgba(52,211,153,.25)`, `:active` scale 0.98
- **CTA**: wzorzec 7.18 – ikona wędki SVG + „Formularz zasadzki" + sub + `›`

### 7.17 Formularz Zasadzki / Zmiana Zestawu (Ekrany 11, 13)
- Pola formularza: `border-radius: 14px` (NIE 12px)
- Toggle items: `border-radius: 14px`
- Stany pól na ekranie 13 (Zmiana Zestawu):
  - `.loaded` – z poprzedniego rzutu: `border rgba(255,255,255,.1)`, tekst `rgba(255,255,255,.55)` (celowo przyciemniony)
  - `.changed` – zmienione: `border rgba(96,165,250,.45)`, tło `rgba(96,165,250,.07)`, tekst 500/jasny, badge „zmiana"
- Pasek „Poprzedni zestaw": tło `rgba(96,165,250,.06)`, border-bottom `rgba(96,165,250,.15)`, tagi 10px/`rgba(255,255,255,.65)`
- **CTA**: wzorzec 7.18 – „Zarzuć wędkę" / „Zarzuć ponownie"

### 7.18 Przycisk CTA – Wzorzec Obowiązkowy (Ekrany 09–13+)

**Stosuj przy każdym nowym ekranie z przyciskiem akcji.** Wzorzec referencyjny: ekran 09.

#### Pojedynczy CTA (pełna szerokość)
```html
<div class="cta-wrap">
  <!-- padding: 12px 16px 16px; border-top: 1px solid rgba(255,255,255,.06); flex-shrink: 0 -->
  <div class="cta-btn">
    <!-- gradient; border-radius: 18px; padding: 14px 20px; justify-content: space-between -->
    <div class="cta-left">  <!-- display: flex; align-items: center; gap: 12px -->
      <div class="cta-icon">  <!-- 40x40px; border-radius: 12px; background: rgba(255,255,255,.18) -->
        <svg width="22" height="22"><!-- inline SVG --></svg>
      </div>
      <div>
        <div class="cta-label">Tekst akcji</div>       <!-- 16px/700 #fff -->
        <div class="cta-sub">Podtytuł (opcjonalny)</div> <!-- 11px rgba(255,255,255,.7) -->
      </div>
    </div>
    <div class="cta-arrow">›</div>  <!-- 24px rgba(255,255,255,.9) -->
  </div>
</div>
```

#### Dwa CTA obok siebie (np. ekran 12)
```html
<div class="cta-wrap">  <!-- display: flex; gap: 12px -->
  <div class="cta-btn-A">
    <!-- flex: 1; justify-content: center; gap: 10px; padding: 14px 10px -->
    <div class="cta-icon">  <!-- 32x32px; border-radius: 10px; rgba(255,255,255,.18) -->
      <svg width="18" height="18"><!-- inline SVG --></svg>
    </div>
    <span class="cta-label">Tekst</span>  <!-- 14px/700 -->
  </div>
  <div class="cta-btn-B">...</div>
</div>
```

#### Ikona CTA – zasady
- **Używaj inline SVG** (NIE `<span class="material-icons-outlined">`) — spójność z ekranem 09
- Akcja zarzucenia wędki: ikona wędki SVG (viewBox 0 0 24 24, linia trzon + łuk żyłki + kółko przynęty)
- Inne akcje: custom inline SVG semantycznie pasujący (np. kamera SVG dla „Branie!")
- **NIE używaj MDI w przycisku CTA** — MDI jest dla ikon w kafelkach/kartach nawigacyjnych

#### Kolory gradientu CTA
| Typ akcji | Gradient | Border |
|-----------|----------|--------|
| Główna (rzut, formularz, przejście) | `#059669 → #047857` | `rgba(52,211,153,.4)` |
| Krytyczna/pilna (Branie!) | `#d97706 → #b45309` | `rgba(251,191,36,.45)` |

### 7.19 Struktura Layoutu Ekranu – Wzorzec Obowiązkowy

**Błąd krytyczny**: `.top-bar` i `.cta-wrap` w `.screen-body` powodują, że scrollują się z treścią — CTA znika, top bar się chowa.

**Prawidłowa struktura `.phone`:**
```
.phone (flex column)
├── .dynamic-island        (position: absolute, z-index: 20)
├── .status-bar            (flex-shrink: 0)
├── .top-bar               (flex-shrink: 0)  ← POZA screen-body
├── [.context-strip, ...]  (flex-shrink: 0)  ← opcjonalne pasy kontekstu, też POZA
├── .screen-body           (flex: 1, overflow-y: auto)  ← TYLKO treść scrollowalna
│   ├── treść...
│   └── .scroll-bottom-pad (height: 8px, bufor na dole)
├── .cta-wrap              (flex-shrink: 0)  ← POZA screen-body
└── .nav-bar               (flex-shrink: 0)
```

**NIE wstawiaj** `.top-bar` ani `.cta-wrap` wewnątrz `.screen-body`.

### 7.20 Section Labels – Zasada

- Etykieta sekcji (`sect-label`, caps 11px) to **krótka nazwa kategorii**, nie instrukcja UX
- Prawidłowo: „Informacje", „Wędka · Przynęta", „Dodatki"
- Błędnie: „Wybierz sekcję → Formularz Zasadzki" (instrukcja należy do opisu karty, nie etykiety)

---

## 7.21 Bottom Sheet – Wzorzec „Zakończ Sesję"

Zaakceptowany w ekranie 16 i prototypie 16.1.

### Zasada

Szuflada (bottom sheet) **zawsze widoczna** w stanie peek na Osi Czasu Sesji — nie wymaga żadnej dodatkowej akcji użytkownika by znaleźć przycisk zakończenia sesji.

### Stany

| Stan | Wysokość | Co widać |
|------|----------|----------|
| **Peek (zwinięta)** | 54px | Handle bar · „Zakończ sesję" · mini-statsy (połowy + waga) · chevron ↑ |
| **Open (rozwinięta)** | auto (do ~280px) | Pełny dialog: tytuł, opis, 3 statsy, przyciski „Zostaję" / „Kończę sesję" |

### Peek strip (54px)

```css
.bottom-sheet-wrap.peek {
    transform: translateY(calc(100% - 54px));
}
```

- Pozycja: `bottom: 34px` (nad nav-barem), `z-index: 30`
- Tło: `#111c15`, `border-radius: 22px 22px 0 0`
- Border-top: `1px solid rgba(255,255,255,.1)`
- Zawartość: `handle (36×4px)` + label „Zakończ sesję" + mini-statsy + chevron

### Animacja

```css
transition: transform .35s cubic-bezier(.32,.72,0,1);
```

Przy rozwinięciu: overlay `rgba(0,0,0,.6)` + `backdrop-filter: blur(3px)` przykrywa treść za szufladą. CTA buttons (`cta-wrap`) dostają `filter: blur(1px) brightness(.5)`.

### Ergonomia

- CTA (Branie! / Zmień zestaw) **nie są zasłonięte** przez peek strip — siedzą nad nim
- Peek znajduje się w dolnej tercji ekranu — strefa naturalnego zasięgu kciuka
- Kliknięcie peek header → otwiera / zamyka (toggle)
- Kliknięcie overlay za szufladą → zamyka

### Kiedy stosować

Wyłącznie dla **nieodwracalnych akcji kończących kontekst** (koniec sesji). Nie stosować dla normalnych dialogów potwierdzenia (np. „Usuń połów?" → standardowy AlertDialog).

---

## 7.22 Galeria Prywatna – Uwagi Implementacyjne (Ekran 19)

### Architektura komponentów (Jetpack Compose)

```
LazyColumn
 ├── stickyHeader { MonthSeparator("Luty 2026") }
 ├── items(catchGroups) { group ->
 │    CatchGroupCard(group)
 │     ├── PhotoSection(group.photos)      // Row: główne + 2×2 grid
 │     └── TagStrip(group.tags)            // FlowRow: chipy
 └── stickyHeader { MonthSeparator("Styczeń 2026") }
```

### Układ zdjęć (PhotoSection)

Proporcje: zdjęcie główne **58%** szerokości, kolumna miniaturek **42%**.
```kotlin
Row(modifier = Modifier.height(144.dp).padding(10.dp, 10.dp, 10.dp, 0.dp)) {
    // Główne zdjęcie
    Box(modifier = Modifier.weight(0.58f).fillMaxHeight().clip(RoundedCornerShape(12.dp))) {
        AsyncImage(model = group.photos.first(), contentScale = ContentScale.Crop, ...)
        // Overlay: share button (top-right), badge "1/N" (bottom-left)
    }
    Spacer(modifier = Modifier.width(3.dp))
    // Siatka miniaturek 2×2
    Column(modifier = Modifier.weight(0.42f).fillMaxHeight()) {
        Row(modifier = Modifier.weight(1f)) {
            ThumbSlot(group.photos.getOrNull(1))
            Spacer(Modifier.width(3.dp))
            ThumbSlot(group.photos.getOrNull(2))
        }
        Spacer(Modifier.height(3.dp))
        Row(modifier = Modifier.weight(1f)) {
            ThumbSlot(group.photos.getOrNull(3))
            Spacer(Modifier.width(3.dp))
            // "+N" jeśli więcej niż 5 zdjęć w grupie
            if (group.photos.size > 5)
                MoreThumb(count = group.photos.size - 4)
            else
                ThumbSlot(group.photos.getOrNull(4))
        }
    }
}
```

`ThumbSlot` wyświetla `AsyncImage` albo pusty slot (dashed border) jeśli `null`.

### Ładowanie zdjęć – Coil

```kotlin
// build.gradle
implementation("io.coil-kt:coil-compose:2.x")

// Użycie z crossfade + placeholder
AsyncImage(
    model = ImageRequest.Builder(ctx)
        .data(photo.uri)
        .crossfade(true)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build(),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier.fillMaxSize()
)
```

### Tag chips – FlowRow

Wymaga `androidx.compose.foundation:foundation` ≥ 1.7 lub `accompanist-flowlayout`.
```kotlin
FlowRow(horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.padding(10.dp)) {
    SuggestionChip(onClick = {}, label = { Text("Karp · 4,1 kg · 58 cm") }, colors = amberChipColors)
    SuggestionChip(onClick = {}, label = { Text("„Wielka Berta"") }, colors = nicknameChipColors)
    // ...
}
```

### Separator miesięcy

```kotlin
LazyColumn {
    groupedByMonth.forEach { (month, groups) ->
        stickyHeader(key = month) {
            Text(month, modifier = Modifier.background(bgColor).padding(16.dp, 4.dp))
        }
        items(groups, key = { it.id }) { group ->
            CatchGroupCard(group)
        }
    }
}
```

### Ważne uwagi

1. **Wydajność**: Zdjęcia ładuj zawsze przez Coil z `memoryCachePolicy + diskCachePolicy`. Bez cache'owania galeria z dziesiątkami zdjęć będzie lagować przy scrollu.
2. **Offline**: `diskCachePolicy(ENABLED)` zapewnia dostęp do miniaturek bez internetu. Oryginały w `MediaStore` zawsze dostępne lokalnie.
3. **Orientacja**: `AsyncImage` z `ContentScale.Crop` — zdjęcia z aparatu są pionowe (portrait). Thumbnail będzie kadrowany do kwadratu — to zamierzone i zgodne z projektem.
4. **Prywatność**: Galeria **nie używa** `READ_EXTERNAL_STORAGE` — zdjęcia są zapisywane w przestrzeni prywatnej aplikacji (`context.filesDir` lub `MediaStore` z własnym `RELATIVE_PATH`). Brak dostępu dla innych aplikacji bez udostępnienia przez użytkownika.
5. **Kolejność zdjęć w grupie**: Pierwsza pozycja w liście = zdjęcie główne. Przy zmianie kolejności (ekran 20) wystarczy swap indeksów w bazie — bez kopiowania plików.
6. **Limit 5 zdjęć na połów**: Egzekwuj już na ekranie aparatu (ekran 14). W galerii wyświetlaj max 4 + badge „+N" dla grup ponad 5 (edge case przy migracji danych).
7. **Dotknięcie grupy → karuzela**: Przekazuj `catchId` do ekranu 20. Nie przekazuj całej listy zdjęć przez nawigację — ładuj je w docelowym ViewModelu po ID.

---

## 8. Status Ekranów

| # | Ekran | Status |
|---|-------|--------|
| 01 | Ekran Startowy | ✅ Zaakceptowany |
| 02 | Dashboard Główny | ✅ Zaakceptowany |
| 03 | Wspomnienia i Sukcesy | ✅ Zaakceptowany |
| 04 | Planowanie: Wybór Daty | ✅ Zaakceptowany |
| 05 | Planowanie: Wyszukaj Łowisko | ✅ Zaakceptowany |
| 06 | Planowanie: Dodaj Nowe Łowisko | ✅ Zaakceptowany |
| 07 | Planowanie: Strateg / Analityk | ✅ Zaakceptowany |
| 08 | Dzień Wyjazdu | ✅ Zaakceptowany |
| 09 | Aktywna Sesja: Meldunek | ✅ Zaakceptowany |
| 10 | Aktywna Sesja: Ekran Sesji (hub) | ✅ Zaakceptowany |
| 11 | Aktywna Sesja: Formularz Zasadzki | ✅ Zaakceptowany |
| 12 | Aktywna Sesja: Oś Czasu Sesji | ✅ Zaakceptowany |
| 13 | Aktywna Sesja: Zmiana Zestawu | ✅ Zaakceptowany |
| 14 | Rejestracja Połowu: Szybkie Zdjęcia | ✅ Zaakceptowany |
| 15 | Rejestracja Połowu: Karta Połowu (+ Zestaw) | ✅ Zaakceptowany |
| 16 | Zakończenie Sesji: Dialog + Karta Sesji | ✅ Zaakceptowany |
| 16.1 | Test Bottom Sheet (prototyp ergonomii) | ✅ Zaakceptowany |
| 17 | Historia Wyjazdów: Lista Sesji | ✅ Zaakceptowany |
| 18 | Historia: Szczegóły Sesji | ✅ Zaakceptowany |
| 19 | Galeria Prywatna: Widok Siatki | ✅ Zaakceptowany |
| 20 | Galeria: Karuzela + Udostępnianie | ✅ Zaakceptowany |
| 21–26 | Pozostałe ekrany | ⬜ W kolejce |

---

## 9. Ikony Pogody – Standard Projektu

### 9.1 Biblioteka: `screens/weather-icons.js`

Custom inline SVG, zero zależności zewnętrznych. Działa offline.  
Zaakceptowany wariant: **Custom Inline SVG z design tokenami** (wybrany na podstawie porównania prototypów 07.1–07.3, pliki prototypów usunięte).

**Wczytanie (w każdym ekranie z pogodą):**
```html
<script src="weather-icons.js"></script>
```

**Użycie przez data-atrybut (preferowane):**
```html
<div data-weather-icon="clear-day" data-size="28"></div>
```
`WeatherIcons.renderAll()` wywoływany automatycznie po DOMContentLoaded.

**Użycie programowe (JS):**
```js
element.innerHTML = WeatherIcons.get('rain', 32);
```

### 9.2 Dostępne klucze ikon

| Klucz | Opis | Kolor dominujący |
|-------|------|------------------|
| `clear-day` | Słonecznie | amber `#fbbf24` |
| `clear-night` | Bezchmurna noc | violet `#a78bfa` |
| `partly-cloudy-day` | Zmienne zachmurzenie | amber + blue-300 |
| `partly-cloudy-night` | Zmienne zachmurzenie noc | violet + blue-300 |
| `cloudy` | Zachmurzenie | blue-300 `#93c5fd` |
| `overcast` | Całkowite zachmurzenie | slate `#64748b` |
| `drizzle` | Mżawka | blue-400 `#60a5fa` |
| `rain` | Deszcz | blue-400 `#60a5fa` |
| `heavy-rain` | Ulewa | blue-400 (grubsze kreski) |
| `thunderstorm` | Burza z piorunami | slate + amber |
| `snow` | Śnieg | blue-200 `#bfdbfe` |
| `sleet` | Deszcz ze śniegiem | blue-400 + blue-200 |
| `fog` | Mgła / Zamglenie | slate `#94a3b8` |
| `haze-day` | Zamglenie ze słońcem | amber + slate |
| `wind` | Wiatr | slate + circles |
| `frost` | Szron / Mróz | blue-200 (kryształ) |
| `sunrise` | Wschód słońca | amber |
| `sunset` | Zachód słońca | amber + orange |

### 9.3 Tokeny kolorów ikon

```js
sun:        '#fbbf24'  // amber-400
rainBlue:   '#60a5fa'  // blue-400
cloudLight: '#93c5fd'  // blue-300
cloudDark:  '#64748b'  // slate-500
snowWhite:  '#bfdbfe'  // blue-200
night:      '#a78bfa'  // violet-400
fog:        '#94a3b8'  // slate-400
lightning:  '#fbbf24'  // amber-400 (ten sam co sun)
```

### 9.4 Zasady

- **NIE używaj emoji** do ikon pogody (różne renderowanie na Android/iOS)
- **NIE używaj** Material Icons ani żadnego CDN do ikon pogody
- Rozmiar domyślny: **28px** w wierszach forecast, **32px** w widżetach
- Przy nowych ikonach: dodaj do `weather-icons.js` → sekcja `const icons`
- Kontener ikony: `display: flex; align-items: center; justify-content: center;`

---

## 10. Ustawienia / Eksport – Wzorce (Ekran 21)

### 10.1 Kolor i tło ekranu

Ekrany ustawień i słowników używają neutralnego, szarego schematu kolorów:

| Token | Wartość |
|-------|---------|
| Kolor główny sekcji | `#374151` |
| Akcent | `#9ca3af` |
| Tło backup-card / grup | `rgba(255,255,255,.03)` |
| Border grup | `rgba(255,255,255,.07)` |
| Border backup-card | `rgba(156,163,175,.18)` |

### 10.2 Karta Eksportu (backup-card)

Wyróżniona karta na szczycie ekranu pokazująca stan synchronizacji:

```css
.backup-card {
  margin: 14px 16px 0;
  padding: 16px;
  background: rgba(55,65,81,.12);
  border: 1px solid rgba(156,163,175,.18);
  border-radius: 18px;
}
```

Elementy karty:
- **Logo serwisu** (np. Google Drive SVG inline) w boxie `44×44px`, `border-radius: 14px`
- **Wskaźnik sync**: zielona kropka (`#34d399`, glow `rgba(52,211,153,.5)`) + tekst statusu
- **Pasek przestrzeni** (storage-bar): `height: 5px`, fill `linear-gradient(90deg, #6b7280, #9ca3af)`
- **Dwa przyciski**: primary (gradient `#374151→#4b5563`) + secondary (transparent), `border-radius: 14px`, `font-size: 13px`

### 10.3 Grupy ustawień (settings-group)

Wiersze nawigacyjne grupowane w karty z separatorami:

```css
.settings-group {
  margin: 0 16px;
  background: rgba(255,255,255,.03);
  border: 1px solid rgba(255,255,255,.07);
  border-radius: 16px;
  overflow: hidden;
}

/* separator między wierszami (nie po ostatnim) */
.settings-row:not(:last-child)::after {
  content: '';
  position: absolute;
  bottom: 0; left: 54px; right: 0;
  height: 1px;
  background: rgba(255,255,255,.05);
}
```

### 10.4 Wiersz ustawień (settings-row)

Anatomia wiersza: `icon-box (36×36px)` + `row-texts (flex:1)` + opcjonalny `row-badge` + `chevron`.

**Paleta icon-box dla ustawień:**

| Klasa | Tło | Ikona |
|-------|-----|-------|
| `.ib-grey` | `rgba(107,114,128,.18)` | `#9ca3af` |
| `.ib-teal` | `rgba(14,116,144,.18)` | `#22d3ee` |
| `.ib-green` | `rgba(5,150,105,.18)` | `#34d399` |
| `.ib-amber` | `rgba(217,119,6,.18)` | `#fbbf24` |
| `.ib-purple` | `rgba(124,58,237,.18)` | `#a78bfa` |
| `.ib-red` | `rgba(220,38,38,.14)` | `#f87171` |

Reguły:
- Akcje destruktywne (np. „Wyczyść dane") → `.ib-red`
- Nawigacja do słowników/łowisk → kolorowe ikony tematyczne (teal, amber)
- Informacje i meta → `.ib-grey` lub `.ib-purple`

### 10.5 Badge z liczbą pozycji

```css
.row-badge {
  background: rgba(156,163,175,.15);
  border: 1px solid rgba(156,163,175,.25);
  border-radius: 10px;
  padding: 2px 9px;
  font-size: 11px; font-weight: 600;
  color: #9ca3af;
}
```

Używany przy wierszach słownikowych i łowiskowych, aby pokazać liczbę elementów bez wchodzenia w ekran.

### 10.6 Sekcja Motyw

Trójprzyciskowy przełącznik (Jasny / Ciemny / Kolorowy) zamiast osobnej strony:

```css
.theme-btn        { flex: 1; padding: 11px 8px; border-radius: 14px; }
.theme-btn.active { background: rgba(55,65,81,.2); border-color: rgba(156,163,175,.4); }
```

- Stan aktywny: jaśniejsze tło + wyróżniony border w szarości akcentu
- Ikony wewnątrz `theme-icon-box` (30×30px, `border-radius: 9px`)

---

## 11. Słowniki Sprzętu – Wzorce (Ekran 22)

### 11.1 Układ zakładkowy

Ekran słowników używa poziomych zakładek (Tabs) zamiast osobnych ekranów dla każdego słownika. Zakładka aktywna wyróżniona dolną kreską (`2px, border-radius: 1px`).

```css
.tab.active { color: #9ca3af; }
.tab.active::after {
  content: ''; position: absolute;
  bottom: 0; left: 16%; right: 16%;
  height: 2px; background: #9ca3af;
  border-radius: 1px 1px 0 0;
}
```

Badge z liczbą pozycji przy każdej zakładce:
```css
.tab-badge {
  background: rgba(255,255,255,.08);
  border-radius: 9px;
  padding: 0 5px; font-size: 10px;
}
.tab.active .tab-badge { background: rgba(156,163,175,.2); color: #9ca3af; }
```

### 11.2 Karta Słownikowa (dict-card)

Karta każdego wpisu słownikowego:

```css
.dict-card {
  background: rgba(255,255,255,.03);
  border: 1px solid rgba(255,255,255,.08);
  border-radius: 16px;
  overflow: hidden;
  margin-bottom: 10px;
}
```

Anatomia karty: `dict-icon-box (38×38px, border-radius: 11px)` + `dict-main (flex:1)` + `dict-edit-btn (32×32px)`.

### 11.3 Karta Aktywna (obecnie zarzucona)

Gdy zestaw jest aktualnie w użyciu (zarzucony na aktywnej sesji), karta otrzymuje wyróżnienie:

```css
.dict-card.active-card {
  border-color: rgba(156,163,175,.25);
  background: rgba(55,65,81,.1);
}
```

Badge z zieloną kropką (`#34d399`) i tekstem „Aktualnie zarzucony" widoczny u góry karty.

### 11.4 Chip-tagi zestawu

Tagi z kluczowymi parametrami zestawu (widoczne bez rozwijania):

| Klasa | Kolor | Przeznaczenie |
|-------|-------|---------------|
| `.chip-rod` | teal `#22d3ee` | Wędka / długość / klasa |
| `.chip-bait` | amber `#fbbf24` | Przynęta |
| `.chip-hook` | szary `#9ca3af` | Haczyk / rozmiar |
| `.chip-line` | green `#34d399` | Żyłka / plecionka |

### 11.5 Rozwijalne szczegóły zestawu

Przycisk „Pokaż pełne ustawienia" rozwijający panel z pełnymi danymi (kołowrotek, żyłka, zanęta, osprzęt, łódka, echosonda):

```css
.card-details {
  border-top: 1px solid rgba(255,255,255,.05);
  padding: 11px 14px;
}
.detail-row { display: flex; justify-content: space-between; padding: 4px 0; }
.detail-key { font-size: 11px; color: rgba(255,255,255,.4); }
.detail-val { font-size: 11px; font-weight: 500; color: rgba(255,255,255,.7); }
```

### 11.6 Formularz Dodawania (stan 2)

Formularz nowego wpisu podzielony na sekcje labelowane nagłówkami `sect-label`:
- **Podstawowe** – nazwa zestawu
- **Wędka i Kołowrotek** – wędka, klasa (lb), kołowrotek, żyłka, grubość
- **Przynęta i Zanęta** – przynęta, zanęta, haczyk (rozmiar), osprzęt
- **Wyposażenie dodatkowe** – toggle Łódka zanętowa, toggle Echosonda

Pola z dropdownem używają `arrow_drop_down` jako suffix-ikony. CTA „Zapisz zestaw" zgodne z wzorcem 7.18 (szary gradient `#374151→#4b5563`).

---

## 12. Zarządzanie Łowiskami – Wzorce (Ekran 23)

### 12.1 Karta Łowiska (fishery-card)

Karta z poziomym układem: miniaturka mapy topo (lewa) + dane (prawa) + akcje (skrajnie prawa):

```css
.fishery-card {
  background: rgba(255,255,255,.03);
  border: 1px solid rgba(255,255,255,.08);
  border-radius: 18px;
  margin-bottom: 12px;
  overflow: hidden;
}
```

Miniaturka `.topo-thumb` (90×76px, `border-radius: 12px`) zawiera inline SVG z warstwicami, akwenem i pinem. Badge „TOPO" w prawym dolnym rogu miniaturki.

### 12.2 Miniaturka Mapy Topograficznej

Każde łowisko przechowuje wycinek mapy topograficznej jako obraz poglądowy. W wizualizacji – inline SVG z:
- Tłem `#1a2235` (ciemny granat)
- Warstwicami jako `<ellipse>` z `fill="none"`, `stroke="rgba(100,180,80,.XX)"` (rosnąca opacity dla niższych poziomów)
- Akwenem jako wypełniona elipsa `rgba(20,70,140,.XX)`
- Pinem `<circle fill="#ef4444">` + biały środek
- Badge `TOPO` na `position: absolute; bottom: 4px; right: 4px`

### 12.3 Mapa Pełnoekranowa + Kadrowanie

Trzeci stan ekranu (po liście i formularzu) to pełnoekranowa mapa z nakładką kadrowania:

**Efekt przyciemnienia poza kadrem:**
```svg
<!-- Przyciemnij całość -->
<rect width="390" height="600" fill="rgba(0,0,0,.35)"/>
<!-- Rozjaśnij obszar kadru via clipPath -->
<clipPath id="cropClip"><rect x="39" y="132" width="312" height="246"/></clipPath>
<g clip-path="url(#cropClip)">
  <!-- pełna mapa bez przyciemnienia -->
</g>
```

**Ramka crop:**
```css
.crop-frame {
  position: absolute;
  border: 2px dashed rgba(255,255,255,.7);
  border-radius: 8px;
}
/* 4 narożniki: crop-corner.tl/.tr/.bl/.br */
/* solid white border 3px, tylko dwa boki na narożnik */
```

**Overlay top-bar**: `position: absolute; top: 0`, `backdrop-filter: blur(8px)`, zawiera przycisk zamknięcia (powrót do formularza) i etykietę „Kadruj widok TOPO".

**Baner GPS**: wyśrodkowany baner z zieloną kropką i współrzędnymi (np. `53°46'24.1"N · 21°18'09.3"E`).

**CTA „Zapisz wycinek topo"**: szary gradient, ikona `crop`.

Sesja Banner: **niewidoczny** w stanie mapy pełnoekranowej (ukrywany razem z top-barem).

### 12.4 Formularz Dodawania Łowiska

Sekcje formularza:
- **Informacje podstawowe**: Nazwa, Adres (z ikoną `my_location`), Nr stanowiska
- **GPS row**: zielony panel (`rgba(5,150,105,.08)`, border `rgba(52,211,153,.2)`) ze współrzędnymi auto-pobranymi ze znacznika
- **Lokalizacja na mapie**: `map-preview-wrap` (mini-mapa 152px high, border-radius: 16px) z pinem i przyciskiem „Otwórz mapę pełnoekranową"
- **Gatunki ryb**: checkbox-chips (`.sp-check.checked` z wypełnionym checkboxem `#9ca3af`)
- **Notatka**: pole tekstowe multi-line

---

## 13. Onboarding – Wzorce Wizualne (Ekrany 24–26)

### 13.1 Kolor i tło

Onboarding nie należy do żadnej sekcji kolorystycznej — używa **wielokolorowego gradientu atmosferycznego** nawiązującego do wędkowania nocą:

```css
/* Tło ekranu onboarding */
background: radial-gradient(ellipse at 30% 20%,
  rgba(14,116,144,.35) 0%,
  rgba(5,150,105,.15) 40%,
  transparent 70%),
  radial-gradient(ellipse at 70% 80%,
  rgba(30,64,175,.25) 0%,
  transparent 60%),
  #060c10;
```

### 13.2 Wskaźnik Kroków (Step Dots)

Trzy krople/kropki wskazujące aktualny krok onboardingu:

```css
.step-dot { width: 8px; height: 8px; border-radius: 50%; background: rgba(255,255,255,.2); }
.step-dot.active { width: 24px; border-radius: 4px; background: #22d3ee; }
```

Aktywna kropka jest wydłużona (`width: 24px`, `border-radius: 4px`).

### 13.3 Karta Wartości (value-card)

Trzy karty z propozycjami wartości na ekranie powitalnym:

```css
.value-card {
  display: flex; align-items: flex-start; gap: 14px;
  padding: 14px 16px;
  background: rgba(255,255,255,.04);
  border: 1px solid rgba(255,255,255,.08);
  border-radius: 18px;
}
```

Każda karta ma kolorowy `icon-box` (44×44px, `border-radius: 14px`) z ikoną Material Outlined i dwa wiersze tekstu (tytuł 14px bold + opis 12px).

Kolory kart (w kolejności):
- **Teal** — `rgba(14,116,144,.2)` / border `rgba(34,211,238,.2)` → szybkość rejestracji
- **Green** — `rgba(5,150,105,.18)` / border `rgba(52,211,153,.2)` → offline-first
- **Amber** — `rgba(217,119,6,.15)` / border `rgba(251,191,36,.2)` → solunar/analityk

### 13.4 CTA Onboardingu

Główny CTA onboardingu używa **teal/water gradient** (zamiast zielonego jak akcje zapisu):

```css
.cta-onboarding {
  background: linear-gradient(135deg, #0e7490, #0891b2);
  border: 1px solid rgba(34,211,238,.3);
  box-shadow: 0 4px 24px rgba(14,116,144,.4);
}
```

CTA drugorzędne (Pomiń): czysty tekst `font-size: 13px; color: rgba(255,255,255,.45)`, brak tła/obramowania.

### 13.5 Pills (szybkie funkcje)

Małe pill-badges eksponujące dodatkowe funkcje:

```css
.feature-pill {
  padding: 5px 12px; border-radius: 20px;
  background: rgba(255,255,255,.05);
  border: 1px solid rgba(255,255,255,.1);
  font-size: 11px; font-weight: 500; color: rgba(255,255,255,.55);
}
```

### 13.6 Zasady Onboardingu

- **Brak top-bar i nav-bar** (tylko status bar + krok-wskaźnik)
- **Brak Active Session Banner** — onboarding to pierwsze uruchomienie, sesja niemożliwa
- **Logo SIEDZI!** używa `Roboto Condensed 700`, rozmiar 52–58px, z SVG ikony wędki (fishing rod)
- **Każdy ekran ma jeden główny CTA** i opcjonalny link „Pomiń"

### 13.7 Ekran Konfiguracji (Screen 26)

Ekran ostatniego kroku onboardingu — minimalna liczba elementów, układ pionowo wyśrodkowany (`justify-content: center`).

**Pasek postępu krok-po-kroku** zastępuje step-dots — trzy `ps-circle` połączone `ps-line`:
- Kroki ukończone: `border: 1.5px solid rgba(52,211,153,.45)`, ikona `check` w kolorze `#34d399`
- Krok aktywny: `border: 2px solid #22d3ee`, `box-shadow: 0 0 12px rgba(34,211,238,.25)`, cyfra w kolorze `#22d3ee`
- Linie: ukończona `rgba(52,211,153,.35)`, aktywna gradient do `rgba(34,211,238,.35)`

**Pojedyncze pole formularza** — tylko nazwa łowiska, bez dropdownów ani GPS w tym kroku. Animowany kursor `field-cursor` (blink 0.9s).

**Toggle-row** — uproszczona wersja bez opisu (`toggle-sub`), tylko ikona + `toggle-title` + track. Trzy ikony: `phishing` (teal), `map` (green), `cloud_done` (gray). Domyślnie wszystkie ON.

**Zasady dodatkowe ekranu 26:**
- Brak top-bar, nav-bar, session-banner (onboarding)
- `screen-body` używa `display: flex; flex-direction: column; justify-content: center` zamiast `overflow-y: auto`
- Gap między sekcjami: `28px`


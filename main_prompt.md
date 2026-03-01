# Specyfikacja Projektu: Aplikacja Mobilna "Śledzik brań"

**Rola:** Senior Project Manager & MD3/MD4 Architect  
**Metodologia:** Vibe Coding (Iteracyjne budowanie z zachowaniem ciągłości)  
**Cel:** Stworzenie zaawansowanego dziennika wędkarskiego z modułem analitycznym dla wędkarzy sportowych (MVP: Karpiarze).

---

## 1. Wizja i Cele Produktu

"Śledzik brań" to nie tylko dziennik, ale **inteligentny asystent wędkarski**. Aplikacja ma pomagać w przygotowaniach, dokumentowaniu połowów oraz analizie korelacji między warunkami zewnętrznymi a aktywnością ryb.

### Kluczowe wartości:

- **Privacy First:** Dane należą do użytkownika (lokalnie + własny Google Drive)
- **Offline-First:** Pełna funkcjonalność w miejscach bez zasięgu
- **Zero-Cost Maintenance:** Wykorzystanie darmowych API i stabilnych źródeł Open Source
- **Modern UI:** Adaptacyjny design MD3/MD4 zoptymalizowany pod trudne warunki (deszcz, noc)

---

## 2. Mapa Drogowa (Roadmapa) – Etapy i Fazy

Projekt został rozbity na mniejsze jednostki, aby umożliwić powrót do pracy w dowolnym momencie.

### Etap I: Fundamenty i Architektura (Setup)

- **Faza 1.1:** Konfiguracja środowiska Android (Kotlin) i baz danych (Room/SQLite) z szyfrowaniem
- **Faza 1.2:** Moduł Słownika Ryb (Import danych z HTML, widoki detali)
- **Faza 1.3:** Moduł Prawny (Disclaimery) i nawigacja bazowa

### Etap II: Przygotowanie do Wyprawy (Pre-Trip)

- **Faza 2.1:** Zarządzanie bazą łowisk (Import CSV/XLSX, wyszukiwanie)
- **Faza 2.2:** Integracja map (GUGiK - ortofotomapy i topograficzne)
- **Faza 2.3:** Moduł Pogody i Solunarny (Open-Meteo, Open Source Solunar)

### Etap III: Aktywny Połów (Dziennik Wyprawy - MVP Core)

- **Faza 3.1:** Tworzenie Dziennika Wyjazdu i logowanie sesji
- **Faza 3.2:** System "Kart Brań" (Zarzucenie, zmiana strategii, kopiowanie danych)
- **Faza 3.3:** Rejestracja Okazu (Zdjęcia, Metadata GPS/Timestamp, automatyczna pogoda)

### Etap IV: Analityka i Retrospekcja (Post-Trip)

- **Faza 4.1:** Dashboard Wyprawy (Statystyki: waga, długość, chmura tagów gatunków)
- **Faza 4.2:** Wykresy czasowe i notatki strategiczne
- **Faza 4.3:** Rejestr i Galeria Historyczna Dzienników

### Etap V: Bezpieczeństwo i Publikacja

- **Faza 5.1:** Integracja z Google Drive (Backup & Restore)
- **Faza 5.2:** Optymalizacja UI pod MD4 (kontrast, ergonomia)
- **Faza 5.3:** Testy końcowe i przygotowanie do publikacji

---

## 3. Szczegółowy Opis Funkcjonalny

### 3.1. Przepływ Przed Wyprawą

- Użytkownik planuje datę i wybiera łowisko (z bazy lub po GPS)
- Aplikacja automatycznie pobiera prognozę pogody i mapy do cache (Offline-Ready)
- Wgląd w historię: *"Co złowiłem tu ostatnim razem?"*

### 3.2. System Kart Brań (Karta Połowów)

Każde zarzucenie wędki to nowa karta.

**Pola wejściowe:** Sprzęt (wędka, żyłka, haczyk), Przynęta, Zanęta (sposób + waga), Metoda, Stanowisko (brzeg/łódź), Echosonda.

**Zdarzenia:**

- **Zmiana strategii:** Możliwość skopiowania poprzedniej karty i edycji tylko wybranych pól
- **Złowienie ryby:** Max 3-5 zdjęć, auto-pobieranie czasu i pogody, wybór gatunku, nickname (np. "Bolek"), waga, długość, pinezka na mapie łowiska
- **Automatyzacja:** Wyliczanie czasu trwania sesji między kartami

### 3.3. Dashboard i Statystyki

- Sumaryczne statystyki wyjazdu
- **Chmura tagów ryb:** Wizualne przedstawienie sukcesu (im większa ryba/ilość, tym większy avatar gatunku)
- **Kaskadowy wykres brań:** Histogram czasu aktywności (orientacja pionowa dla wygody przewijania)

---

## 4. Specyfikacja Techniczna

| Komponent | Technologia / Źródło |
|-----------|----------------------|
| Platforma | Android OS (Kotlin) |
| Design | Material Design 3 / 4 (High Contrast Mode) |
| Baza Danych | SQLite (Room) + Szyfrowanie (SQLCipher) |
| Mapy | GUGiK (ortofotomapa / topograficzna) |
| Pogoda | Open-Meteo / IMGW (z mechanizmem cache) |
| Backup | Google Drive API (User-owned) |
| Dane Ryb | HTML Research Report (Lokalnie) |
| Dane Łowisk | Plik CSV/XLSX (Wbudowany/Import) |

---

## 5. Wytyczne UI/UX (MD4 Context)

Aplikacja musi być użyteczna w nocy i deszczu:

- Duże touch-targety (przyciski łatwe do kliknięcia mokrym palcem)
- Tryb ciemny o wysokim kontraście
- Szybkie akcje: *"Zrób zdjęcie i wróć do karty"* (minimalizacja kliknięć)
- Ikony parametrów na liście kart (szybki skan wzrokowy bez wchodzenia w detale)

---

## 6. Przerwanie i Kontynuacja (Vibe Coding Logic)

Każda faza kończy się "Checkpointem". W przypadku wstrzymania prac:

1. Sprawdź ostatnio ukończoną Fazę w Etapie
2. Zweryfikuj spójność bazy danych (Room Schema)
3. Kontynuuj od implementacji UI dla danych logiki biznesowej z poprzedniej fazy

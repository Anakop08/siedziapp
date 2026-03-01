# Skrypty pomocnicze

## clean_fishery_names.py

Czyści kolumnę `name` w `fisheries_clean.json`, usuwając nadmiarowe słowa kluczowe (np. „Łowisko wędkarskie”, „Staw wędkarski”), gdy obok jest bardziej konkretna nazwa.

### Użycie

```bash
# Raport – tylko podgląd proponowanych zmian (bez zapisu)
python scripts/clean_fishery_names.py --report

# Czyszczenie – zapisuje wynik do fisheries_clean_cleaned.json
python scripts/clean_fishery_names.py --clean

# Własna ścieżka do pliku
python scripts/clean_fishery_names.py --report "../moj_plik.json"
```

### Tryby

| Tryb    | Opis                                                                 |
|---------|----------------------------------------------------------------------|
| `--report` | Listuje wszystkie proponowane zmiany (przed → po) oraz pozycje z samym ogólnikiem |
| `--clean`  | Zapisuje oczyszczony plik jako `fisheries_clean_cleaned.json`        |

### Windows (UTF-8)

```powershell
$env:PYTHONIOENCODING="utf-8"; python scripts/clean_fishery_names.py --report
```

### Uwagi

- Nazwy będące samym ogólnikiem (np. „Łowisko wędkarskie”) nie są zmieniane – wymagają ręcznej edycji.
- Po `--clean` sprawdź plik wynikowy przed podmianą oryginału.

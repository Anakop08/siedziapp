#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Skrypt do czyszczenia nazw łowisk w fisheries_clean.json.
Usuwa nadmiarowe słowa kluczowe (np. "Łowisko wędkarskie" gdy jest już "Łowisko karpiowe").
Uruchom: python scripts/clean_fishery_names.py [--report|--clean] [ścieżka_do_json]
"""

import json
import io
import sys
import re
import sys
from pathlib import Path

# Nadmiarowe frazy – do usunięcia, gdy występują obok konkretniejszej nazwy
REDUNDANT_PHRASES = [
    # Całe segmenty (np. " - sportowe łowisko wędkarskie - ")
    r"\s*-\s*sportowe\s+łowisko\s+wędkarskie\s*-\s*",
    r",?\s*Łowisko\s+wędkarskie\s*,\s*",
    r",?\s*łowisko\s+wędkarskie\s*,\s*",
    # Na końcu lub po myślniku
    r",?\s*Łowisko\s+wędkarskie\b",
    r",?\s*łowisko\s+wędkarskie\b",
    r"\s*-\s*Łowisko\s+Wędkarskie\b",
    r"\s*-\s*łowisko\s+wędkarskie\b",
    r",?\s*Staw\s+wędkarski\b",
    r",?\s*staw\s+wędkarski\b",
    r",?\s*Łowienie\s+ryb\b",
    r"\s*-\s*Wędkowanie\s+i\s+noclegi\s*-\s*",
    r"\s+Wędkowanie\s+i\s+noclegi\s*-\s*",
]

# Frazy, które mogą stanowić całą nazwę – nie usuwamy ich, jeśli to jedyna treść
GENERIC_NAMES = [
    "Łowisko wędkarskie",
    "łowisko wędkarskie",
    "Łowisko Wędkarskie",
    "Staw wędkarski",
    "staw wędkarski",
]


def is_generic_only(name: str) -> bool:
    """Czy nazwa to wyłącznie ogólnik (nie czyścimy – trzeba ręcznie poprawić)."""
    n = name.strip()
    return any(n.lower() == g.lower() for g in GENERIC_NAMES)


def clean_name(name: str) -> tuple[str, bool]:
    """
    Usuwa nadmiarowe frazy z nazwy.
    Zwraca (wynik, czy_coś_zmieniono).
    """
    if not name or not name.strip():
        return name, False

    result = name
    changed = False
    for pattern in REDUNDANT_PHRASES:
        new_result = re.sub(pattern, " ", result, flags=re.IGNORECASE)
        if new_result != result:
            changed = True
        result = new_result

    # Wielokrotne przecinki, spacje, podwójne myślniki
    result = re.sub(r"\s*,\s*,\s*", ", ", result)
    result = re.sub(r"\s*-\s*-\s*", " - ", result)
    result = re.sub(r"\s+", " ", result)
    result = re.sub(r"^\s*[,]\s*", "", result)
    result = re.sub(r"\s*[,]\s*$", "", result)
    # Końcowe i początkowe myślniki (pozostałe po usunięciu segmentów)
    result = re.sub(r"\s*-\s*$", "", result)
    result = re.sub(r"^\s*-\s*", "", result)
    result = result.strip()

    # Nie zostawiaj pustego wyniku – zachowaj oryginał
    if not result and name.strip():
        return name.strip(), False

    return result, changed or result != name.strip()


def analyze(fisheries: list[dict]) -> list[dict]:
    """Analizuje nazwy i zwraca listę: {original, cleaned, changed, index}."""
    report = []
    for i, f in enumerate(fisheries):
        original = f.get("name", "")
        if not original:
            continue
        cleaned, changed = clean_name(original)
        report.append({
            "index": i,
            "original": original,
            "cleaned": cleaned,
            "changed": changed,
            "generic_only": is_generic_only(original),
        })
    return report


def run_report(data: list[dict], verbose: bool = True) -> int:
    """Wypisuje raport i zwraca liczbę proponowanych zmian."""
    report = analyze(data)
    changed = [r for r in report if r["changed"]]
    generic = [r for r in report if r["generic_only"]]

    print("=" * 60)
    print("RAPORT: Nazwy łowisk – potencjalne do poprawy")
    print("=" * 60)
    print(f"Łącznie wpisów: {len(data)}")
    print(f"Proponowane zmiany (usunięcie nadmiaru): {len(changed)}")
    print(f"Tylko ogólnik (wymaga ręcznej edycji): {len(generic)}")
    print()

    if changed:
        print("--- Proponowane zmiany (przed → po) ---")
        for r in changed[:50]:  # pierwszych 50
            print(f"  [{r['index']}] {r['original']}")
            print(f"       -> {r['cleaned']}")
            print()
        if len(changed) > 50:
            print(f"  ... i jeszcze {len(changed) - 50} pozycji")
        print()

    if generic:
        print("--- Tylko ogólnik (bez konkretnej nazwy) ---")
        for r in generic[:20]:
            print(f"  [{r['index']}] {r['original']}")
        if len(generic) > 20:
            print(f"  ... i jeszcze {len(generic) - 20} pozycji")

    return len(changed)


def run_clean(data: list[dict], output_path: Path) -> int:
    """Zapisuje oczyszczony JSON. Zwraca liczbę zmienionych nazw."""
    report = analyze(data)
    count = 0
    for r in report:
        if r["changed"] and r["cleaned"] and not r["generic_only"]:
            data[r["index"]]["name"] = r["cleaned"]
            count += 1

    with open(output_path, "w", encoding="utf-8") as f:
        json.dump(data, f, ensure_ascii=False, indent=2)

    return count


def setup_encoding():
    """Ustawia kodowanie UTF-8 dla stdout (Windows)."""
    import sys
    if hasattr(sys.stdout, "reconfigure"):
        sys.stdout.reconfigure(encoding="utf-8")


def main():
    mode = "--report"
    json_path = Path(__file__).resolve().parent.parent / "fisheries_clean.json"

    args = sys.argv[1:]
    if args:
        if args[0] in ("--report", "--clean"):
            mode = args[0]
            if len(args) > 1:
                json_path = Path(args[1])
        else:
            json_path = Path(args[0])

    if not json_path.exists():
        print(f"Błąd: Nie znaleziono pliku {json_path}")
        sys.exit(1)

    with open(json_path, encoding="utf-8") as f:
        data = json.load(f)

    if mode == "--report":
        run_report(data)
    else:
        out = json_path.parent / f"{json_path.stem}_cleaned.json"
        n = run_clean(data, out)
        print(f"Zapisano oczyszczony plik: {out}")
        print(f"Zmieniono {n} nazw.")


if __name__ == "__main__":
    setup_encoding()
    main()

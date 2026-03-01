graph TD
Start(Otwarcie aplikacji) --> Splash[Ekran powitalny]
Splash -->|pierwsze uruchomienie| OnboardingWelcome[Witaj w SIEDZI! - Ekran 24]
OnboardingWelcome --> OnboardingHow[Jak dziala aplikacja? - Ekran 25]
OnboardingHow --> OnboardingSetup[Skonfiguruj pierwsze lowisko - Ekran 26]
OnboardingSetup --> Home[Dashboard Glowny]
OnboardingWelcome -->|Pomin| Home
OnboardingHow -->|Pomin| Home
OnboardingSetup -->|Pomin - skonfiguruje pozniej| Home
Splash -->|powracajacy uzytkownik| Home

Home --> Nostalgia[Wspomnienia i Sukcesy]
Home --> Planning[Zaplanuj Nowa Wyprawe]
Home --> ActiveSession[Rozpocznij Sesje - nad woda]
Home --> GalleryNav[Galeria Prywatna]
Home --> History
Home --> SettingsNav[Ustawienia / Eksport]

subgraph Motywacja
    Nostalgia --> PastTrips[Historia Wypraw]
    PastTrips --> PhotoGallery[Galeria Zdjec Ryb]
    PastTrips --> WeatherHistory[Szczegoly Pogody - Archiwum]
end

subgraph Planowanie
    Planning --> SelectDate[Wybierz Date i Urlop]
    SelectDate --> SearchFishery[Wyszukaj Lowisko]
    SearchFishery -->|Wybierz z listy| TripSummary[Podsumowanie Planu]
    SearchFishery -->|Dodaj Nowe| NewFishery[Dodaj Nowe Lowisko]
    NewFishery -->|Anuluj| SearchFishery
    NewFishery --> FisheryDetails[Wpisz Nazwe i Adres]
    FisheryDetails --> AutoGPS[Automatyczna Lokalizacja GPS]
    AutoGPS --> StationNote[Dodaj Notatke: Nr Stanowiska]
    StationNote --> TripSummary
    TripSummary --> DeepAnalysis[Strateg / Analityk Wyprawy]
    DeepAnalysis --> AnalysisUI[Warunki: Pogoda / Cisnienie / Solunar]
    DeepAnalysis --> HistoryComp[Historia: Zeszly rok / 5-lat Avg]
    AnalysisUI --> Insights[Notatnik Strategii i Insightow]
    HistoryComp --> Insights
end

Insights --> SaveTrip[Zatwierdz i Zapisz Wyprawe]

subgraph DzienWyprawy[Dzien Wyprawy - Przed Wyjazdem]
    SaveTrip --> RefreshData[Odswiez dane przed wyjazdem]
    RefreshData --> CheckWeather[Sprawdz aktualna pogode]
    RefreshData --> CheckSolunar[Sprawdz Solunar i gwiazdy]
    RefreshData --> SyncBackup[Synchronizuj kopie zapasowa - Google Drive]
    RefreshData --> DownloadMaps[Pobierz mapy offline]
    DownloadMaps --> MapStd[Mapa standardowa]
    DownloadMaps --> MapTopo[Mapa topograficzna]
    DownloadMaps --> MapSat[Zdjecie satelitarne HD - Geoportal]
    MapStd --> MapsReady[Mapy zapisane tymczasowo na telefonie]
    MapTopo --> MapsReady
    MapSat --> MapsReady
end

MapsReady --> GoFishing[Jedz na Lowisko!]
GoFishing --> ActiveSession

subgraph NadWoda[Nad Woda - Aktywna Sesja]
    ActiveSession --> CheckIn[Meldunek na Stanowisku]
    CheckIn --> SessionScreen[Ekran Sesji]
    SessionScreen --> FisheryInfo[Opis lowiska - ryby i zakazy]
    SessionScreen --> FisheryStats[Moje statystyki na tym lowisku]
    SessionScreen --> FisheryTrips[Lista moich wyjazdow tu - historia]
    FisheryInfo --> TackleForm[Formularz Zasadzki]
    FisheryStats --> TackleForm
    FisheryTrips --> TackleForm
    TackleForm --> RodBait[Wedka / Przyneta / Zaneta]
    TackleForm --> ReelLine[Kolowrotek / Zylka / Osprzet / Haczyki]
    TackleForm --> Extras[Lodka zanetowa / Echosonda]
    RodBait --> CastRod[Zarzuć wędkę]
    ReelLine --> CastRod
    Extras --> CastRod
    CastRod --> Timeline[Os Czasu Sesji - wpis z tagami zestawu]
    Timeline --> Waiting[Aplikacja w kieszeni - czekasz na branie]
    Waiting -->|Zmiana zestawu| ChangeSetup[Zmiana zestawu - edytujesz tylko co sie zmienilo]
    ChangeSetup --> CastRod2[Zarzuc ponownie - nowy wpis na os czasu]
    CastRod2 --> Timeline
    Waiting -->|Branie! Mam ryb e!| QuickPhoto[Szybkie zdjecie - 1 klik]
    QuickPhoto -->|do 5 zdj ec| MorePhotos[Kolejne zdjecia do karty polowu]
    QuickPhoto -->|6 zdjecie| PhotoLimit[Komunikat: max 5 zdj ec na karcie]
    MorePhotos --> CatchCard[Karta Polowu - podglad zdj ec]
    CatchCard --> CatchForm[Gatunek / Waga / Dlugosc / Nickname]
    CatchCard --> AutoTackle[Zestaw auto: wedka + przyneta + zaneta + osprzet]
    CatchForm --> SaveCatch[Zapisz polow]
    AutoTackle --> SaveCatch
    SaveCatch --> AutoMeta[Auto: GPS + Godzina + Pogoda + Solunar]
    AutoMeta --> CatchOnTimeline[Karta na osi czasu - tagi pogody + miniaturka ryby]
    CatchOnTimeline --> Timeline
    Timeline -->|Koniec dnia| EndSession{Zakonczyc sesje?}
end

subgraph ZakonczenieSeji[Koniec Sesji]
    EndSession -->|Tak| SessionCard[Karta Sesji - auto-obliczenia]
    EndSession -->|Nie - wracam lowic| Timeline
    SessionCard --> SessionStats[Ikony ryb + ilosc + waga + czas trwania]
    SessionCard --> ThemeSwitch[Przelacz motyw: jasny / ciemny / kolorowy]
    ThemeSwitch --> HomeReturn[Ekran Glowny - widoczna karta ostatniej sesji]
    HomeReturn --> History[Historia Wyjazdow]
    History --> SessionDetail[Szczegoly Sesji - zdjecia + szczegoly + sprzet]
end

HomeReturn --> CleanupMaps[Usun tymczasowe mapy - zostaje miniatura topo]
CleanupMaps --> SyncFinal[Synchronizuj backup - Google Drive]

subgraph HistoriaScreen[Historia Wyjazdow]
    History --> HistoryList[Lista sesji]
    HistoryList --> HistorySearch[Filtruj: data / lowisko / gatunek ryby]
    HistorySearch --> SessionDetail
end

subgraph GaleriaScreen[Galeria Prywatna]
    GalleryNav --> GalleryGrid[Grupy po 5 zdj ec - pierwsze na froncie]
    GalleryGrid --> PhotoCarousel[Karuzela zdj ec polowu]
    PhotoCarousel --> ReorderPhoto[Zmien kolejnosc / ustaw zdjecie glowne]
    PhotoCarousel --> EditPhoto[Edytuj: przytnij / usun]
    PhotoCarousel --> SharePhoto[Udostepnij do innej aplikacji]
    SharePhoto --> AutoTag[Auto-opis: waga + wymiary + lowisko]
    AutoTag -->|Wlacz / Wylacz| ShareFinal[Wyslij - Instagram / Facebook / Chat]
end

subgraph UstawieniaScreen[Ustawienia - Ekran 21]
    SettingsNav --> ExportData[Synchronizuj / Eksportuj - Google Drive]
    ExportData --> BackupFull[Kopia zapasowa: dane + zdjecia]
    SettingsNav --> DictManager[Slowniki Sprzetu i Przynety]
    SettingsNav --> FisheryManager
    SettingsNav --> ThemeSettings[Motyw: jasny / ciemny / kolorowy]
    SettingsNav --> AppData[Dane i Aplikacja]
    AppData --> ClearCache[Pamiec podreczna map - zwolnij miejsce]
    AppData --> ClearData[Wyczysc dane aplikacji]
    AppData --> AppVersion[Wersja / Pomoc / Polityka prywatnosci]
end

subgraph SlownikiScreen[Slowniki Sprzetu - Ekran 22]
    DictManager --> DictRods[Slownik: Wedki i Zestawy]
    DictManager --> DictBaits[Slownik: Przynety i Zanety]
    DictManager --> DictHooks[Slownik: Haczyki i Osprzet]
end

subgraph LowiskaScreen[Zarzadzanie Lowiskami - Ekran 23]
    FisheryManager[Lista Lowisk] --> AddNewFisheryExt[Dodaj Nowe Lowisko]
    AddNewFisheryExt --> FisheryName[Nazwa i Adres]
    FisheryName --> MapPicker[Kliknij na mapie - pobierz GPS]
    MapPicker --> MapFullscreen[Mapa pelnoekranowa - wybierz miejsce]
    MapFullscreen --> SaveTopoClip[Zapisz wycinek mapy topograficznej]
    SaveTopoClip --> FisheryReady[Lowisko dostepne w planowaniu wyjazdow]
end

classDef main fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#fff
classDef memory fill:#5b21b6,stroke:#8b5cf6,stroke-width:1px,color:#fff
classDef planning fill:#065f46,stroke:#10b981,stroke-width:1px,color:#fff
classDef departure fill:#92400e,stroke:#f59e0b,stroke-width:1px,color:#fff
classDef session fill:#0e7490,stroke:#22d3ee,stroke-width:1px,color:#fff
classDef gallery fill:#7c3aed,stroke:#a78bfa,stroke-width:1px,color:#fff
classDef settings fill:#374151,stroke:#9ca3af,stroke-width:1px,color:#fff
classDef banner fill:#0e7490,stroke:#22d3ee,stroke-width:2px,stroke-dasharray:5 3,color:#fff

subgraph BannerMechanism["🟢 Active Session Banner (cross-screen)"]
    Banner["🟢 Sesja aktywna · [czas] ›\n– widoczny na WSZYSTKICH ekranach poza sesją –"]
    Banner -->|"1 tap"| Timeline
end

Banner -.->|widoczny gdy sesja aktywna| Home
Banner -.->|widoczny gdy sesja aktywna| GalleryNav
Banner -.->|widoczny gdy sesja aktywna| Nostalgia
Banner -.->|widoczny gdy sesja aktywna| SettingsNav
Banner -.->|widoczny gdy sesja aktywna| History
Banner -.->|widoczny gdy sesja aktywna| SessionDetail

class Home main
class Nostalgia,PastTrips,PhotoGallery,WeatherHistory memory
class Planning,SelectDate,SearchFishery,NewFishery,FisheryDetails,TripSummary,DeepAnalysis,AnalysisUI,HistoryComp,Insights,AutoGPS,StationNote planning
class RefreshData,CheckWeather,CheckSolunar,SyncBackup,DownloadMaps,MapStd,MapTopo,MapSat,MapsReady,GoFishing departure
class CheckIn,SessionScreen,FisheryInfo,FisheryStats,FisheryTrips,TackleForm,RodBait,ReelLine,Extras,CastRod,Timeline,Waiting,ChangeSetup,CastRod2,QuickPhoto,MorePhotos,PhotoLimit,CatchCard,CatchForm,AutoTackle,SaveCatch,AutoMeta,CatchOnTimeline,EndSession session
class OnboardingWelcome,OnboardingHow,OnboardingSetup main
class SessionCard,SessionStats,ThemeSwitch,HomeReturn,History,SessionDetail,CleanupMaps,SyncFinal,HistoryList,HistorySearch memory
class GalleryNav,GalleryGrid,PhotoCarousel,ReorderPhoto,EditPhoto,SharePhoto,AutoTag,ShareFinal gallery
class SettingsNav,ExportData,BackupFull,DictManager,DictRods,DictBaits,DictHooks,FisheryManager,AddNewFisheryExt,FisheryName,MapPicker,MapFullscreen,SaveTopoClip,FisheryReady,ThemeSettings,AppData,ClearCache,ClearData,AppVersion settings
class Banner banner

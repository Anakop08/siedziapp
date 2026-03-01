/**
 * SIEDZI! – Weather Icons Library
 * Custom inline SVG icons using project design tokens.
 * Zero external dependencies. Works offline.
 *
 * Usage:
 *   <script src="../weather-icons.js"></script>
 *   <div id="myIcon"></div>
 *   <script>
 *     document.getElementById('myIcon').innerHTML = WeatherIcons.get('clear-day', 30);
 *   </script>
 *
 *   Or as element attribute:
 *   <span data-weather-icon="rain" data-size="28"></span>
 *   WeatherIcons.renderAll(); // call once on DOMContentLoaded
 *
 * Available icon keys:
 *   clear-day | clear-night | partly-cloudy-day | partly-cloudy-night
 *   cloudy | overcast | drizzle | rain | heavy-rain
 *   thunderstorm | snow | sleet | fog | haze-day
 *   wind | frost | sunrise | sunset
 *
 * Design tokens used:
 *   Sun      – #fbbf24 (amber-400)
 *   Rain     – #60a5fa (blue-400)
 *   Cloud    – #93c5fd (blue-300) light / #64748b dark
 *   Snow     – #bfdbfe (blue-200)
 *   Night    – #a78bfa (violet-400)
 *   Lightning– #fbbf24 (amber-400)
 *   Fog      – #94a3b8 (slate-400)
 */

const WeatherIcons = (() => {

    // ── Design Token Colors ──────────────────────────────────────
    const C = {
        sun: '#fbbf24',  // amber-400
        sunGlow: '#fde68a',  // amber-200
        rainBlue: '#60a5fa',  // blue-400
        cloudLight: '#93c5fd',  // blue-300
        cloudDark: '#64748b',  // slate-500
        snowWhite: '#bfdbfe',  // blue-200
        night: '#a78bfa',  // violet-400
        fog: '#94a3b8',  // slate-400
        lightning: '#fbbf24',  // amber-400
        star: '#fde68a',  // amber-200
        green: '#34d399',  // emerald-400 (accent)
    };

    // ── SVG Icons ────────────────────────────────────────────────
    const icons = {

        'clear-day': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <circle cx="15" cy="15" r="5.5" fill="${C.sun}"/>
        <line x1="15" y1="2"  x2="15" y2="5.5"  stroke="${C.sun}" stroke-width="2"   stroke-linecap="round"/>
        <line x1="15" y1="24.5" x2="15" y2="28" stroke="${C.sun}" stroke-width="2"   stroke-linecap="round"/>
        <line x1="2"  y1="15" x2="5.5" y2="15"  stroke="${C.sun}" stroke-width="2"   stroke-linecap="round"/>
        <line x1="24.5" y1="15" x2="28" y2="15" stroke="${C.sun}" stroke-width="2"   stroke-linecap="round"/>
        <line x1="5.5"  y1="5.5"  x2="7.7" y2="7.7"   stroke="${C.sun}" stroke-width="1.6" stroke-linecap="round"/>
        <line x1="22.3" y1="22.3" x2="24.5" y2="24.5" stroke="${C.sun}" stroke-width="1.6" stroke-linecap="round"/>
        <line x1="24.5" y1="5.5"  x2="22.3" y2="7.7"  stroke="${C.sun}" stroke-width="1.6" stroke-linecap="round"/>
        <line x1="7.7"  y1="22.3" x2="5.5"  y2="24.5" stroke="${C.sun}" stroke-width="1.6" stroke-linecap="round"/>
        <circle cx="15" cy="15" r="8" stroke="${C.sun}" stroke-width="0.8" stroke-dasharray="2 2" opacity="0.35"/>
      </svg>`,

        'clear-night': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M24 18C19.6 18 16 14.4 16 10C16 7.5 17.2 5.3 19 4C14 3.2 9.5 7.5 9.5 13C9.5 18.8 14.2 23.5 20 23.5C23.5 23.5 26.5 21.7 28.3 19C27 18.7 25.5 18 24 18Z" fill="${C.night}" opacity="0.9"/>
        <circle cx="24" cy="8"  r="1.2" fill="${C.star}" opacity="0.7"/>
        <circle cx="27" cy="5"  r="0.8" fill="${C.star}" opacity="0.5"/>
        <circle cx="22" cy="4"  r="0.7" fill="${C.star}" opacity="0.6"/>
        <circle cx="26" cy="13" r="0.8" fill="${C.star}" opacity="0.45"/>
        <circle cx="5"  cy="7"  r="0.7" fill="${C.star}" opacity="0.35"/>
        <circle cx="8"  cy="4"  r="0.6" fill="${C.star}" opacity="0.3"/>
      </svg>`,

        'partly-cloudy-day': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <circle cx="11" cy="9.5" r="4.5" fill="${C.sun}" opacity="0.92"/>
        <line x1="11" y1="2"   x2="11" y2="4"   stroke="${C.sun}" stroke-width="1.6" stroke-linecap="round"/>
        <line x1="4"  y1="9.5" x2="6"  y2="9.5" stroke="${C.sun}" stroke-width="1.6" stroke-linecap="round"/>
        <line x1="5.5" y1="5"  x2="6.9" y2="6.4" stroke="${C.sun}" stroke-width="1.4" stroke-linecap="round"/>
        <line x1="16.5" y1="5" x2="15.1" y2="6.4" stroke="${C.sun}" stroke-width="1.4" stroke-linecap="round"/>
        <path d="M8 23.5 C8 23.5 6.8 23.5 5.8 22.9 C3.8 21.8 3.8 19 5.8 17.8 C6.6 17.3 7.8 17.1 8.9 17.6 C9.5 15.7 11.1 14.5 13.5 15 C15.5 15.4 17 17 17 19 C17.6 19 18.5 19.3 19 19.9 C20.1 21.2 19.5 23.3 17.6 24 C17 24.3 16 24.3 16 24.3 L8 23.5Z" fill="${C.cloudLight}" opacity="0.9"/>
      </svg>`,

        'partly-cloudy-night': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M20 10.5C17 10.5 14.5 8 14.5 5C14.5 3.5 15.2 2.2 16.2 1.4C13 0.9 10 3.6 10 7C10 10.6 13 13.5 16.5 13.5C18.2 13.5 19.7 12.8 20.8 11.7C20.5 11.3 20.2 10.9 20 10.5Z" fill="${C.night}" opacity="0.8"/>
        <path d="M8 24 C8 24 6.8 24 5.8 23.4 C3.8 22.3 3.8 19.5 5.8 18.3 C6.6 17.8 7.8 17.6 8.9 18.1 C9.5 16.2 11.1 15 13.5 15.5 C15.5 15.9 17 17.5 17 19.5 C17.6 19.5 18.5 19.8 19 20.4 C20.1 21.7 19.5 23.8 17.6 24.5 C17 24.8 16 24.8 16 24.8 L8 24Z" fill="${C.cloudLight}" opacity="0.85"/>
      </svg>`,

        'cloudy': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M6 10.5 C6 10.5 4 10 3 9 C1 7.5 1.5 4.8 3.5 3.7 C4.5 3.1 6 3 7.2 3.7 C8 1.8 9.8 0.8 12.2 1.3 C14.2 1.7 15.5 3.2 15.5 5 C16.2 4.9 17.2 5.2 17.8 5.9 C19 7.2 18.3 9.3 16.6 10 C15.9 10.3 15 10.3 15 10.3 L6 10.5Z" fill="${C.cloudLight}" opacity="0.6"/>
        <path d="M8 22 C8 22 6.5 22 5.3 21.3 C3 20 3 17 5.2 15.7 C6.2 15.1 7.7 14.9 9 15.5 C9.7 13.4 11.5 12.1 14.2 12.7 C16.4 13.1 18 14.9 18 17.1 C18.8 17 19.8 17.3 20.4 18.1 C21.7 19.6 21 21.9 19 22.8 C18.2 23.2 17.2 23.2 17.2 23.2 L8 22Z" fill="${C.cloudLight}" opacity="0.9"/>
      </svg>`,

        'overcast': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M5 21 C5 21 3.5 21 2.5 20.2 C0.5 18.8 1 16 3 14.8 C4 14.2 5.5 14 7 14.7 C7.8 12.6 9.8 11.3 12.5 11.9 C14.7 12.3 16.3 14.1 16.3 16.1 C17.1 16 18.2 16.4 18.8 17.2 C20 18.7 19.3 21 17.3 21.9 C16.5 22.3 15.5 22.3 15.5 22.3 L5 21Z" fill="${C.cloudDark}" opacity="0.7"/>
        <path d="M11 20.5 C11 20.5 9.5 20.5 8.5 19.7 C6.5 18.3 7 15.5 9 14.3 C10 13.7 11.5 13.5 13 14.2 C13.8 12.1 15.8 10.8 18.5 11.4 C20.7 11.8 22.3 13.6 22.3 15.6 C23.1 15.5 24.2 15.9 24.8 16.7 C26 18.2 25.3 20.5 23.3 21.4 C22.5 21.8 21.5 21.8 21.5 21.8 L11 20.5Z" fill="${C.cloudDark}" opacity="0.9"/>
      </svg>`,

        'drizzle': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M7 19 C7 19 5.8 19 4.8 18.4 C2.8 17.3 2.8 14.5 4.8 13.3 C5.6 12.7 7 12.6 8.1 13.2 C8.8 11.3 10.4 10.1 12.8 10.6 C14.8 11 16.3 12.6 16.3 14.6 C16.9 14.6 17.8 14.9 18.3 15.5 C19.4 16.8 18.8 18.9 17 19.7 C16.3 20 15.5 20 15.5 20 L7 19Z" fill="${C.cloudLight}" opacity="0.85"/>
        <line x1="8"  y1="22" x2="7.2"  y2="25.5" stroke="${C.rainBlue}" stroke-width="1.4" stroke-linecap="round"/>
        <line x1="12" y1="22" x2="11.2" y2="25.5" stroke="${C.rainBlue}" stroke-width="1.4" stroke-linecap="round"/>
        <line x1="16" y1="22" x2="15.2" y2="25.5" stroke="${C.rainBlue}" stroke-width="1.4" stroke-linecap="round"/>
      </svg>`,

        'rain': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M7 18 C7 18 5.5 18 4.5 17.2 C2.3 15.9 2.5 13 4.5 11.7 C5.5 11.1 7 10.9 8.3 11.6 C9 9.5 10.8 8.2 13.5 8.8 C15.7 9.2 17.2 11 17.2 13 C18 13 19 13.3 19.5 14.1 C20.7 15.6 20 18 18 18.8 C17.2 19.2 16.2 19.2 16.2 19.2 L7 18Z" fill="${C.cloudLight}" opacity="0.85"/>
        <line x1="7"  y1="21" x2="5.5"  y2="26" stroke="${C.rainBlue}" stroke-width="1.8" stroke-linecap="round"/>
        <line x1="11" y1="21" x2="9.5"  y2="26" stroke="${C.rainBlue}" stroke-width="1.8" stroke-linecap="round"/>
        <line x1="15" y1="21" x2="13.5" y2="26" stroke="${C.rainBlue}" stroke-width="1.8" stroke-linecap="round"/>
        <circle cx="5.5"  cy="27" r="1.2" fill="${C.rainBlue}" opacity="0.5"/>
        <circle cx="9.5"  cy="27" r="1.2" fill="${C.rainBlue}" opacity="0.5"/>
        <circle cx="13.5" cy="27" r="1.2" fill="${C.rainBlue}" opacity="0.5"/>
      </svg>`,

        'heavy-rain': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M6 17 C6 17 4.5 17 3.5 16.2 C1.3 14.9 1.5 12 3.5 10.7 C4.5 10.1 6 9.9 7.3 10.6 C8 8.5 9.8 7.2 12.5 7.8 C14.7 8.2 16.2 10 16.2 12 C17 12 18 12.3 18.5 13.1 C19.7 14.6 19 17 17 17.8 C16.2 18.2 15.2 18.2 15.2 18.2 L6 17Z" fill="${C.cloudDark}" opacity="0.9"/>
        <line x1="5"  y1="20" x2="3"   y2="27" stroke="${C.rainBlue}" stroke-width="2"   stroke-linecap="round"/>
        <line x1="9"  y1="20" x2="7"   y2="27" stroke="${C.rainBlue}" stroke-width="2"   stroke-linecap="round"/>
        <line x1="13" y1="20" x2="11"  y2="27" stroke="${C.rainBlue}" stroke-width="2"   stroke-linecap="round"/>
        <line x1="17" y1="20" x2="15"  y2="27" stroke="${C.rainBlue}" stroke-width="2"   stroke-linecap="round"/>
        <circle cx="3"  cy="28" r="1.3" fill="${C.rainBlue}" opacity="0.55"/>
        <circle cx="7"  cy="28" r="1.3" fill="${C.rainBlue}" opacity="0.55"/>
        <circle cx="11" cy="28" r="1.3" fill="${C.rainBlue}" opacity="0.55"/>
        <circle cx="15" cy="28" r="1.3" fill="${C.rainBlue}" opacity="0.55"/>
      </svg>`,

        'thunderstorm': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M5 17 C5 17 3.5 17 2.5 16.2 C0.3 14.9 0.5 12 2.5 10.7 C3.5 10.1 5 9.9 6.3 10.6 C7 8.5 8.8 7.2 11.5 7.8 C13.7 8.2 15.2 10 15.2 12 C16 12 17 12.3 17.5 13.1 C18.7 14.6 18 17 16 17.8 C15.2 18.2 14.2 18.2 14.2 18.2 L5 17Z" fill="${C.cloudDark}" opacity="0.9"/>
        <line x1="5" y1="20" x2="4" y2="23" stroke="${C.rainBlue}" stroke-width="1.5" stroke-linecap="round"/>
        <line x1="16" y1="20" x2="15" y2="24" stroke="${C.rainBlue}" stroke-width="1.5" stroke-linecap="round"/>
        <polygon points="12,19 8,27 11.5,27 9,34 17,22.5 13.5,22.5" fill="${C.lightning}" opacity="0.95"/>
      </svg>`,

        'snow': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M7 18 C7 18 5.5 18 4.5 17.2 C2.3 15.9 2.5 13 4.5 11.7 C5.5 11.1 7 10.9 8.3 11.6 C9 9.5 10.8 8.2 13.5 8.8 C15.7 9.2 17.2 11 17.2 13 C18 13 19 13.3 19.5 14.1 C20.7 15.6 20 18 18 18.8 C17.2 19.2 16.2 19.2 16.2 19.2 L7 18Z" fill="${C.cloudLight}" opacity="0.8"/>
        <line x1="8"  y1="21" x2="8"  y2="27" stroke="${C.snowWhite}" stroke-width="1.5" stroke-linecap="round" stroke-dasharray="1.5 2"/>
        <line x1="13" y1="21" x2="13" y2="27" stroke="${C.snowWhite}" stroke-width="1.5" stroke-linecap="round" stroke-dasharray="1.5 2"/>
        <line x1="18" y1="21" x2="18" y2="27" stroke="${C.snowWhite}" stroke-width="1.5" stroke-linecap="round" stroke-dasharray="1.5 2"/>
        <circle cx="8"  cy="28" r="1.5" fill="${C.snowWhite}" opacity="0.75"/>
        <circle cx="13" cy="28" r="1.5" fill="${C.snowWhite}" opacity="0.75"/>
        <circle cx="18" cy="28" r="1.5" fill="${C.snowWhite}" opacity="0.75"/>
      </svg>`,

        'sleet': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M7 17.5 C7 17.5 5.5 17.5 4.5 16.7 C2.3 15.4 2.5 12.5 4.5 11.2 C5.5 10.6 7 10.4 8.3 11.1 C9 9 10.8 7.7 13.5 8.3 C15.7 8.7 17.2 10.5 17.2 12.5 C18 12.5 19 12.8 19.5 13.6 C20.7 15.1 20 17.5 18 18.3 C17.2 18.7 16.2 18.7 16.2 18.7 L7 17.5Z" fill="${C.cloudLight}" opacity="0.85"/>
        <line x1="7"  y1="21" x2="6"   y2="25" stroke="${C.rainBlue}"  stroke-width="1.6" stroke-linecap="round"/>
        <line x1="16" y1="21" x2="15"  y2="25" stroke="${C.rainBlue}"  stroke-width="1.6" stroke-linecap="round"/>
        <circle cx="11" cy="23" r="2" fill="${C.snowWhite}" opacity="0.8"/>
        <circle cx="11" cy="27" r="2" fill="${C.snowWhite}" opacity="0.65"/>
      </svg>`,

        'fog': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <line x1="3"  y1="10" x2="27" y2="10" stroke="${C.fog}" stroke-width="2"   stroke-linecap="round"/>
        <line x1="5"  y1="15" x2="25" y2="15" stroke="${C.fog}" stroke-width="2"   stroke-linecap="round" opacity="0.8"/>
        <line x1="3"  y1="20" x2="27" y2="20" stroke="${C.fog}" stroke-width="2"   stroke-linecap="round" opacity="0.6"/>
        <line x1="8"  y1="25" x2="22" y2="25" stroke="${C.fog}" stroke-width="1.8" stroke-linecap="round" opacity="0.35"/>
      </svg>`,

        'haze-day': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <circle cx="15" cy="11" r="5" fill="${C.sun}" opacity="0.85"/>
        <line x1="15" y1="2"  x2="15" y2="5"  stroke="${C.sun}" stroke-width="1.8" stroke-linecap="round"/>
        <line x1="8"  y1="4.5" x2="9.5" y2="6" stroke="${C.sun}" stroke-width="1.5" stroke-linecap="round"/>
        <line x1="22" y1="4.5" x2="20.5" y2="6" stroke="${C.sun}" stroke-width="1.5" stroke-linecap="round"/>
        <line x1="4"  y1="11" x2="7"  y2="11" stroke="${C.sun}" stroke-width="1.8" stroke-linecap="round"/>
        <line x1="23" y1="11" x2="26" y2="11" stroke="${C.sun}" stroke-width="1.8" stroke-linecap="round"/>
        <line x1="3"  y1="19" x2="27" y2="19" stroke="${C.fog}" stroke-width="2"   stroke-linecap="round" opacity="0.7"/>
        <line x1="5"  y1="23" x2="25" y2="23" stroke="${C.fog}" stroke-width="1.8" stroke-linecap="round" opacity="0.55"/>
        <line x1="8"  y1="27" x2="22" y2="27" stroke="${C.fog}" stroke-width="1.6" stroke-linecap="round" opacity="0.35"/>
      </svg>`,

        'wind': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M3 10 Q8 8 12 10 Q16 12 20 10 Q23 8.5 24 7" stroke="${C.fog}" stroke-width="2" fill="none" stroke-linecap="round"/>
        <path d="M3 16 Q9 13 14 16 Q19 19 24 16" stroke="${C.fog}" stroke-width="2" fill="none" stroke-linecap="round" opacity="0.8"/>
        <path d="M3 22 Q7 20 11 22 Q15 24 19 22" stroke="${C.fog}" stroke-width="1.8" fill="none" stroke-linecap="round" opacity="0.6"/>
        <circle cx="24.5" cy="7" r="2.5" stroke="${C.sun}" stroke-width="1.5" fill="none"/>
        <circle cx="25" cy="16" r="2" stroke="${C.cloudLight}" stroke-width="1.4" fill="none"/>
      </svg>`,

        'frost': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <line x1="15" y1="2"  x2="15" y2="28" stroke="${C.snowWhite}" stroke-width="1.8" stroke-linecap="round"/>
        <line x1="2"  y1="15" x2="28" y2="15" stroke="${C.snowWhite}" stroke-width="1.8" stroke-linecap="round"/>
        <line x1="5.5" y1="5.5" x2="24.5" y2="24.5" stroke="${C.snowWhite}" stroke-width="1.8" stroke-linecap="round"/>
        <line x1="24.5" y1="5.5" x2="5.5" y2="24.5" stroke="${C.snowWhite}" stroke-width="1.8" stroke-linecap="round"/>
        <circle cx="15" cy="15" r="3" fill="${C.snowWhite}" opacity="0.9"/>
        <line x1="15" y1="4" x2="13" y2="7" stroke="${C.snowWhite}" stroke-width="1.2" stroke-linecap="round"/>
        <line x1="15" y1="4" x2="17" y2="7" stroke="${C.snowWhite}" stroke-width="1.2" stroke-linecap="round"/>
        <line x1="15" y1="26" x2="13" y2="23" stroke="${C.snowWhite}" stroke-width="1.2" stroke-linecap="round"/>
        <line x1="15" y1="26" x2="17" y2="23" stroke="${C.snowWhite}" stroke-width="1.2" stroke-linecap="round"/>
        <line x1="4" y1="15" x2="7"  y2="13"  stroke="${C.snowWhite}" stroke-width="1.2" stroke-linecap="round"/>
        <line x1="4" y1="15" x2="7"  y2="17"  stroke="${C.snowWhite}" stroke-width="1.2" stroke-linecap="round"/>
        <line x1="26" y1="15" x2="23" y2="13" stroke="${C.snowWhite}" stroke-width="1.2" stroke-linecap="round"/>
        <line x1="26" y1="15" x2="23" y2="17" stroke="${C.snowWhite}" stroke-width="1.2" stroke-linecap="round"/>
      </svg>`,

        'sunrise': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <line x1="3"  y1="23" x2="27" y2="23" stroke="${C.fog}" stroke-width="1.5" stroke-linecap="round" opacity="0.5"/>
        <semicircle/>
        <path d="M5 23 A10 10 0 0 1 25 23" stroke="${C.sun}" stroke-width="2" fill="none" stroke-linecap="round"/>
        <circle cx="15" cy="23" r="3.5" fill="${C.sun}" opacity="0.9"/>
        <line x1="15" y1="8"  x2="15" y2="11" stroke="${C.sun}" stroke-width="1.8" stroke-linecap="round"/>
        <line x1="7"  y1="12" x2="9.5" y2="14" stroke="${C.sun}" stroke-width="1.5" stroke-linecap="round"/>
        <line x1="23" y1="12" x2="20.5" y2="14" stroke="${C.sun}" stroke-width="1.5" stroke-linecap="round"/>
        <line x1="4"  y1="19" x2="7"  y2="19" stroke="${C.sun}" stroke-width="1.5" stroke-linecap="round"/>
        <line x1="23" y1="19" x2="26" y2="19" stroke="${C.sun}" stroke-width="1.5" stroke-linecap="round"/>
        <line x1="15" y1="27" x2="15" y2="25" stroke="${C.fog}" stroke-width="1.5" stroke-linecap="round" opacity="0.4"/>
      </svg>`,

        'sunset': (s) => `
      <svg width="${s}" height="${s}" viewBox="0 0 30 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <line x1="3"  y1="20" x2="27" y2="20" stroke="${C.fog}" stroke-width="1.5" stroke-linecap="round" opacity="0.5"/>
        <path d="M5 20 A10 10 0 0 1 25 20" stroke="${C.sun}" stroke-width="2" fill="none" stroke-linecap="round" opacity="0.85"/>
        <circle cx="15" cy="20" r="3.5" fill="${C.sun}" opacity="0.85"/>
        <line x1="15" y1="5"  x2="15" y2="8"  stroke="${C.sun}" stroke-width="1.8" stroke-linecap="round" opacity="0.75"/>
        <line x1="7"  y1="9"  x2="9.5" y2="11" stroke="${C.sun}" stroke-width="1.5" stroke-linecap="round" opacity="0.65"/>
        <line x1="23" y1="9"  x2="20.5" y2="11" stroke="${C.sun}" stroke-width="1.5" stroke-linecap="round" opacity="0.65"/>
        <line x1="3"  y1="24" x2="27" y2="24" stroke="#f97316" stroke-width="1.5" stroke-linecap="round" opacity="0.4"/>
        <line x1="6"  y1="28" x2="24" y2="28" stroke="#f97316" stroke-width="1.2" stroke-linecap="round" opacity="0.25"/>
      </svg>`,
    };

    // ── Public API ───────────────────────────────────────────────

    /**
     * Get SVG string for an icon.
     * @param {string} key - Icon key (e.g. 'clear-day', 'rain')
     * @param {number} [size=28] - Width and height in px
     * @returns {string} SVG HTML string (empty string if key not found)
     */
    function get(key, size = 28) {
        if (!icons[key]) {
            console.warn(`WeatherIcons: unknown key "${key}". Available: ${Object.keys(icons).join(', ')}`);
            return '';
        }
        return icons[key](size);
    }

    /**
     * Auto-render all elements with data-weather-icon attribute.
     * Call once after DOM is ready.
     * @example <span data-weather-icon="rain" data-size="32"></span>
     */
    function renderAll() {
        document.querySelectorAll('[data-weather-icon]').forEach(el => {
            const key = el.getAttribute('data-weather-icon');
            const size = parseInt(el.getAttribute('data-size') || '28', 10);
            el.innerHTML = get(key, size);
        });
    }

    /** List all available icon keys */
    function list() {
        return Object.keys(icons);
    }

    return { get, renderAll, list };
})();

// Auto-init on DOM ready
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', () => WeatherIcons.renderAll());
} else {
    WeatherIcons.renderAll();
}

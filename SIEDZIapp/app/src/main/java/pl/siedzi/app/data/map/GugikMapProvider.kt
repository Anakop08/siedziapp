package pl.siedzi.app.data.map

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourceFactory

/**
 * Dostawca źródeł map GUGiK (Geoportal.gov.pl).
 * 
 * WMTS endpoints (GetCapabilities):
 * - TOPO (mapa topograficzna): https://mapy.geoportal.gov.pl/wss/service/WMTS/guest/wmts/TOPO
 * - ORTO (ortofotomapa):       https://mapy.geoportal.gov.pl/wss/service/PZGIK/ORTO/WMTS/StandardResolution
 * - ORTO HD:                   https://mapy.geoportal.gov.pl/wss/service/PZGIK/ORTO/WMTS/HighResolution
 * 
 * WMTS używa EPSG:4326 / EPSG:2180 – konwersja z Web Mercator (OSM) wymaga transformacji.
 * Aktualnie używamy OSM (MAPNIK) jako podstawowe źródło; GUGiK WMTS w przyszłej iteracji.
 */
object GugikMapProvider {

    /** Domyślne źródło kafelków – OSM Mapnik (kompatybilne z OsmDroid). */
    val defaultTileSource get() = TileSourceFactory.MAPNIK

    /** Bazowy URL WMTS GUGiK TOPO (mapa topograficzna). */
    const val WMTS_TOPO_BASE = "https://mapy.geoportal.gov.pl/wss/service/WMTS/guest/wmts/TOPO"

    /** Bazowy URL WMTS GUGiK ORTO (ortofotomapa). */
    const val WMTS_ORTO_BASE = "https://mapy.geoportal.gov.pl/wss/service/PZGIK/ORTO/WMTS/StandardResolution"

    /**
     * Buduje URL kafelka WMTS w formacie KVP.
     * Parametry: LAYER, TileMatrixSet=EPSG:4326, TileMatrix, TileRow, TileCol.
     * Uwaga: mapowanie (zoom,x,y) → (TileMatrix,TileRow,TileCol) wymaga konwersji układów.
     */
    fun buildWmtsKvpUrl(
        baseUrl: String,
        layer: String,
        tileMatrixSet: String = "EPSG:4326",
        zoom: Int,
        tileX: Int,
        tileY: Int
    ): String {
        val tileMatrix = "$tileMatrixSet:${zoom.coerceIn(0, 12)}"
        return "$baseUrl?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0" +
            "&LAYER=${java.net.URLEncoder.encode(layer, "UTF-8")}" +
            "&STYLE=default" +
            "&TILEMATRIXSET=$tileMatrixSet" +
            "&TILEMATRIX=$tileMatrix" +
            "&TILEROW=$tileY" +
            "&TILECOL=$tileX" +
            "&FORMAT=image/jpeg"
    }
}

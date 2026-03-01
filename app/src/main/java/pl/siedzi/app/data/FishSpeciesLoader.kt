package pl.siedzi.app.data

import android.content.Context
import org.json.JSONArray
import pl.siedzi.app.data.entity.FishSpecies

object FishSpeciesLoader {

    fun loadFromAssets(context: Context): List<FishSpecies> {
        val json = context.assets.open("fish_species.json").bufferedReader().use { it.readText() }
        val array = JSONArray(json)
        return (0 until array.length()).map { i ->
            val obj = array.getJSONObject(i)
            FishSpecies(
                id = obj.getString("id"),
                name = obj.getString("name"),
                nameLatin = obj.optString("nameLatin").takeIf { it.isNotEmpty() },
                description = obj.optString("description").takeIf { it.isNotEmpty() },
                minSize = if (obj.has("minSize") && !obj.isNull("minSize")) obj.getInt("minSize") else null,
                closedSeason = obj.optString("closedSeason").takeIf { it.isNotEmpty() },
                imageUri = obj.optString("imageUri").takeIf { it.isNotEmpty() }
            )
        }
    }
}

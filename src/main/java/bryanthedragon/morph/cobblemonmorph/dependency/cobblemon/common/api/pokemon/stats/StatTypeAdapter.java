package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats

import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer

public interface StatTypeAdapter : JsonDeserializer<Stat>, JsonSerializer<Stat>

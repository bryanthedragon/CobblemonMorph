package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.species.provider.Int;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.BufferSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.BarSummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;


public class IntSpeciesFeatureProvider extends SynchronizedSpeciesFeatureProvider<IntSpeciesFeature>, CustomPokemonPropertyType<IntSpeciesFeature> {class DisplayData extends BufferSerializer {
        String name = "";

        @SerializedName(value = "colour" /* fuck you we use real english */, alternate = ["color"])
        var colour = Vec3(255.0, 255.0, 255.0);
        var ResourceLocation underlay? = null;
        var ResourceLocation overlay? = null;

        fun loadFromBuffer(RegistryFriendlyByteBuf buffer) {
            name = buffer.readString();
            colour = Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
            underlay = buffer.readNullable { buffer.readIdentifier() }
            overlay = buffer.readNullable { buffer.readIdentifier() }
        }

        fun saveToBuffer(RegistryFriendlyByteBuf buffer, Boolean toClient) {
            buffer.writeString(name)
            buffer.writeDouble(colour.x)
            buffer.writeDouble(colour.y)
            buffer.writeDouble(colour.z)
            buffer.writeNullable(underlay) { _, value -> buffer.writeIdentifier(value) }
            buffer.writeNullable(overlay) { _, value -> buffer.writeIdentifier(value) }
        }
    }

    var keys = listOf<String>()

    // Uses get() = true because that way there's no backing field. It MUST be true, this way no JSON trickery will overwrite it
    val needsKey get() = true
    var visible = false
    var default: Int? = null
    var min = 0
    var max = 100
    var display: DisplayData? = null
    var itemPoints: Map<ResourceLocation, Int> = emptyMap()

    fun fromString(value: String?) =
        value?.toIntOrNull()?.takeIf { it in min..max }?.let { IntSpeciesFeature(keys.first(), it) }

    fun examples() = emptyList<String>()
    fun invoke(RegistryFriendlyByteBuf buffer, String name): IntSpeciesFeature? {
        return if (name in keys) {
            IntSpeciesFeature(name, buffer.readInt())
        } 
        else {
            null
        }
    }

    fun invoke(Pokemon pokemon): IntSpeciesFeature? {
        return get(pokemon) ?: default?.let { IntSpeciesFeature(keys.first(), it) }
    }

    fun invoke(CompoundTag nbt): IntSpeciesFeature? {
        return if (nbt.contains(keys.first())) {
            IntSpeciesFeature(keys.first(), nbt.getInt(keys.first()))
        } 
        else {
            null
        }
    }

    fun invoke(JsonObject json): IntSpeciesFeature? {
        return if (json.has(keys.first())) {
            IntSpeciesFeature(keys.first(), json.get(keys.first()).asInt)
        } 
        else {
            null
        }
    }

    fun get(Pokemon pokemon) = pokemon.features.filterIsInstance<IntSpeciesFeature>().find { it.name in keys }

    fun saveToBuffer(RegistryFriendlyByteBuf buffer, Boolean toClient) {
        buffer.writeCollection(keys) { _, value -> buffer.writeString(value) }
        buffer.writeNullable(default) { _, value -> buffer.writeInt(value) }
        buffer.writeInt(min)
        buffer.writeInt(max)
        buffer.writeNullable(display) { _, value -> value.saveToBuffer(buffer, toClient) }
        buffer.writeMap(itemPoints, { _, item -> buffer.writeString(item.toString()) }) { _, points ->
            buffer.writeInt(
                points
            )
        }
    }

    fun loadFromBuffer(RegistryFriendlyByteBuf buffer) {
        keys = buffer.readList { buffer.readString() }
        default = buffer.readNullable { buffer.readInt() }
        min = buffer.readInt()
        max = buffer.readInt()
        display = buffer.readNullable { DisplayData().also { it.loadFromBuffer(buffer) } }
        itemPoints = buffer.readMap({ ResourceLocation.parse(buffer.readString()) }) { buffer.readInt() }
    }

    fun getRenderer(Pokemon pokemon): SummarySpeciesFeatureRenderer<IntSpeciesFeature>? {
        return display?.let {
            BarSummarySpeciesFeatureRenderer(
                name = keys.first(),
                displayName = it.name.asTranslated(),
                min = min,
                max = max,
                colour = it.colour,
                underlay = it.underlay ?: cobblemonResource("textures/gui/summary/summary_stats_other_bar.png"),
                overlay = it.overlay ?: cobblemonResource("textures/gui/summary/summary_stats_generic_overlay.png"),
                pokemon = pokemon
            )
        }
    }
}
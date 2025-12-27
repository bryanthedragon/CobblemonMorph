package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf

public open class FlagSpeciesFeature(name: String) : SynchronizedSpeciesFeature, CustomPokemonProperty {
   public final var enabled: Boolean
   public open val name: String

   init {
      this.name = name;
   }

   public constructor(name: String, enabled: Boolean) : this(name) {
      this.enabled = enabled;
   }

   public override fun saveToNBT(pokemonNBT: CompoundTag): CompoundTag {
      pokemonNBT.m_128379_(this.getName(), this.enabled);
      return pokemonNBT;
   }

   public override fun loadFromNBT(pokemonNBT: CompoundTag): SpeciesFeature {
      this.enabled = if (pokemonNBT.m_128441_(this.getName())) pokemonNBT.m_128471_(this.getName()) else this.enabled;
      return this;
   }

   public override fun saveToJSON(pokemonJSON: JsonObject): JsonObject {
      pokemonJSON.addProperty(this.getName(), this.enabled);
      return pokemonJSON;
   }

   public override fun loadFromJSON(pokemonJSON: JsonObject): SpeciesFeature {
      val var10000: JsonElement = pokemonJSON.get(this.getName());
      val isEnabled: java.lang.Boolean = if (var10000 != null) var10000.getAsBoolean() else null;
      this.enabled = isEnabled ?: this.enabled;
      return this;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.enabled);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      this.enabled = buffer.readBoolean();
   }

   public override fun asString(): String {
      return "${this.getName()}=${this.enabled}";
   }

   public override fun apply(pokemon: Pokemon) {
      val var10000: SpeciesFeatureProvider = SpeciesFeatures.INSTANCE.getFeature(this.getName());
      if (var10000 != null) {
         if (SpeciesFeatures.INSTANCE.getFeaturesFor(pokemon.getSpecies()).contains(var10000)) {
            val existingFeature: FlagSpeciesFeature = pokemon.getFeature(this.getName());
            if (existingFeature != null) {
               existingFeature.enabled = this.enabled;
            } else {
               pokemon.getFeatures().add(new FlagSpeciesFeature(this.getName(), this.enabled));
            }

            pokemon.updateAspects();
         }
      }
   }

   public override fun matches(pokemon: Pokemon): Boolean {
      val var10000: FlagSpeciesFeature = pokemon.getFeature(this.getName());
      return var10000 != null && var10000.enabled == this.enabled;
   }

   override fun apply(pokemonEntity: PokemonEntity) {
      CustomPokemonProperty.DefaultImpls.apply(this, pokemonEntity);
   }

   override fun matches(pokemonEntity: PokemonEntity): Boolean {
      return CustomPokemonProperty.DefaultImpls.matches(this, pokemonEntity);
   }
}

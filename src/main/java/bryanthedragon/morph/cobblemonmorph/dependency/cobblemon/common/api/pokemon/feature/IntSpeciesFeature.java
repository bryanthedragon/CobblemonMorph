package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonObject
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf

public class IntSpeciesFeature(name: String) : SynchronizedSpeciesFeature, CustomPokemonProperty {
   public open var name: String
   public final var value: Int

   init {
      this.name = name;
   }

   public constructor() : this("")
   public constructor(name: String, value: Int) : this(name) {
      this.value = value;
   }

   public override fun saveToNBT(pokemonNBT: CompoundTag): CompoundTag {
      pokemonNBT.m_128405_(this.getName(), this.value);
      return pokemonNBT;
   }

   public open fun loadFromNBT(pokemonNBT: CompoundTag): SynchronizedSpeciesFeature {
      this.value = pokemonNBT.m_128451_(this.getName());
      return this;
   }

   public override fun saveToJSON(pokemonJSON: JsonObject): JsonObject {
      pokemonJSON.addProperty(this.getName(), this.value);
      return pokemonJSON;
   }

   public override fun loadFromJSON(pokemonJSON: JsonObject): SpeciesFeature {
      this.value = pokemonJSON.get(this.getName()).getAsInt();
      return this;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeInt(this.value);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      this.value = buffer.readInt();
   }

   public override fun asString(): String {
      return "${this.getName()}=${this.value}";
   }

   public override fun apply(pokemon: Pokemon) {
      val var10000: SpeciesFeatureProvider = SpeciesFeatures.INSTANCE.getFeature(this.getName());
      if (var10000 != null) {
         if (SpeciesFeatures.INSTANCE.getFeaturesFor(pokemon.getSpecies()).contains(var10000)) {
            val existingFeature: IntSpeciesFeature = pokemon.getFeature(this.getName());
            if (existingFeature != null) {
               existingFeature.value = this.value;
            } else {
               pokemon.getFeatures().add(new IntSpeciesFeature(this.getName(), this.value));
            }

            pokemon.updateAspects();
         }
      }
   }

   public override fun matches(pokemon: Pokemon): Boolean {
      val var10000: IntSpeciesFeature = pokemon.getFeature(this.getName());
      return var10000 != null && var10000.value == this.value;
   }

   override fun apply(pokemonEntity: PokemonEntity) {
      CustomPokemonProperty.DefaultImpls.apply(this, pokemonEntity);
   }

   override fun matches(pokemonEntity: PokemonEntity): Boolean {
      return CustomPokemonProperty.DefaultImpls.matches(this, pokemonEntity);
   }
}

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.Locale
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nStringSpeciesFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringSpeciesFeature.kt\ncom/cobblemon/mod/common/api/pokemon/feature/StringSpeciesFeature\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,71:1\n1#2:72\n*E\n"])
public class StringSpeciesFeature(name: String, value: String) : SynchronizedSpeciesFeature, CustomPokemonProperty {
   public open val name: String
   public final var value: String

   init {
      this.name = name;
      this.value = value;
   }

   public override fun saveToNBT(pokemonNBT: CompoundTag): CompoundTag {
      pokemonNBT.m_128359_(this.getName(), this.value);
      return pokemonNBT;
   }

   public override fun loadFromNBT(pokemonNBT: CompoundTag): SpeciesFeature {
      var var10001: java.lang.String = pokemonNBT.m_128461_(this.getName());
      if (var10001 != null) {
         val var6: Boolean = !StringsKt.isBlank(var10001);
         var10001 = if (var6) var10001 else null;
         if ((if (var6) var10001 else null) != null) {
            var10001 = var10001.toLowerCase(Locale.ROOT);
            if (var10001 != null) {
               this.value = var10001;
               return this;
            }
         }
      }

      return this;
   }

   public override fun saveToJSON(pokemonJSON: JsonObject): JsonObject {
      pokemonJSON.addProperty(this.getName(), this.value);
      return pokemonJSON;
   }

   public override fun loadFromJSON(pokemonJSON: JsonObject): SpeciesFeature {
      val var10001: JsonElement = pokemonJSON.get(this.getName());
      if (var10001 != null) {
         val var2: java.lang.String = var10001.getAsString();
         if (var2 != null) {
            val var3: java.lang.String = var2.toLowerCase(Locale.ROOT);
            if (var3 != null) {
               this.value = var3;
               return this;
            }
         }
      }

      return this;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.value);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      val var10001: java.lang.String = buffer.m_130277_();
      this.value = var10001;
   }

   public override fun asString(): String {
      return "${this.getName()}=${this.value}";
   }

   public override fun apply(pokemon: Pokemon) {
      val var10000: SpeciesFeatureProvider = SpeciesFeatures.INSTANCE.getFeature(this.getName());
      if (var10000 != null) {
         if (SpeciesFeatures.INSTANCE.getFeaturesFor(pokemon.getSpecies()).contains(var10000)) {
            val existingFeature: StringSpeciesFeature = pokemon.getFeature(this.getName());
            if (existingFeature != null) {
               existingFeature.value = this.value;
            } else {
               pokemon.getFeatures().add(new StringSpeciesFeature(this.getName(), this.value));
            }

            pokemon.updateAspects();
         }
      }
   }

   public override fun matches(pokemon: Pokemon): Boolean {
      val var10000: StringSpeciesFeature = pokemon.getFeature(this.getName());
      return (if (var10000 != null) var10000.value else null) == this.value;
   }

   override fun apply(pokemonEntity: PokemonEntity) {
      CustomPokemonProperty.DefaultImpls.apply(this, pokemonEntity);
   }

   override fun matches(pokemonEntity: PokemonEntity): Boolean {
      return CustomPokemonProperty.DefaultImpls.matches(this, pokemonEntity);
   }
}

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nFlagProperty.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlagProperty.kt\ncom/cobblemon/mod/common/pokemon/properties/FlagProperty\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,33:1\n1747#2,3:34\n*S KotlinDebug\n*F\n+ 1 FlagProperty.kt\ncom/cobblemon/mod/common/pokemon/properties/FlagProperty\n*L\n32#1:34,3\n*E\n"])
public class FlagProperty(key: String, remove: Boolean = false) : CustomPokemonProperty {
   public final val key: String
   public final val remove: Boolean

   init {
      this.key = key;
      this.remove = remove;
   }

   public override fun asString(): String {
      return this.key;
   }

   public override fun apply(pokemon: Pokemon) {
      if (this.remove) {
         pokemon.getCustomProperties().removeIf(FlagProperty::apply$lambda$0);
      } else {
         pokemon.getCustomProperties().add(this);
      }
   }

   public override fun matches(pokemon: Pokemon): Boolean {
      val `$this$any$iv`: java.lang.Iterable = pokemon.getCustomProperties();
      var var10000: Boolean;
      if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
         var10000 = false;
      } else {
         val var4: java.util.Iterator = `$this$any$iv`.iterator();

         while (true) {
            if (!var4.hasNext()) {
               var10000 = false;
               break;
            }

            val it: CustomPokemonProperty = var4.next() as CustomPokemonProperty;
            if (it is FlagProperty && (it as FlagProperty).key == this.key) {
               var10000 = true;
               break;
            }
         }
      }

      return var10000;
   }

   override fun apply(pokemonEntity: PokemonEntity) {
      CustomPokemonProperty.DefaultImpls.apply(this, pokemonEntity);
   }

   override fun matches(pokemonEntity: PokemonEntity): Boolean {
      return CustomPokemonProperty.DefaultImpls.matches(this, pokemonEntity);
   }

   @JvmStatic
   fun `apply$lambda$0`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}

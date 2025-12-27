package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nStarterCategory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StarterCategory.kt\ncom/cobblemon/mod/common/config/starter/StarterCategory\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,30:1\n1549#2:31\n1620#2,3:32\n*S KotlinDebug\n*F\n+ 1 StarterCategory.kt\ncom/cobblemon/mod/common/config/starter/StarterCategory\n*L\n20#1:31\n20#1:32,3\n*E\n"])
public data StarterCategory(name: String, displayName: String, pokemon: List<PokemonProperties>) {
   public final val displayName: String
   public final val name: String
   public final val pokemon: List<PokemonProperties>

   init {
      this.name = name;
      this.displayName = displayName;
      this.pokemon = pokemon;
   }

   public fun asRenderableStarterCategory(): RenderableStarterCategory {
      val `$this$map$iv`: java.lang.Iterable = this.pokemon;
      val var11: java.lang.String = this.displayName;
      val var10: java.lang.String = this.name;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add((`item$iv$iv` as PokemonProperties).asRenderablePokemon());
      }

      return new RenderableStarterCategory(var10, var11, `destination$iv$iv` as MutableList<RenderablePokemon>);
   }

   public operator fun component1(): String {
      return this.name;
   }

   public operator fun component2(): String {
      return this.displayName;
   }

   public operator fun component3(): List<PokemonProperties> {
      return this.pokemon;
   }

   public fun copy(name: String = this.name, displayName: String = this.displayName, pokemon: List<PokemonProperties> = this.pokemon): StarterCategory {
      return new StarterCategory(name, displayName, pokemon);
   }

   public override fun toString(): String {
      return "StarterCategory(name=${this.name}, displayName=${this.displayName}, pokemon=${this.pokemon})";
   }

   public override fun hashCode(): Int {
      return (this.name.hashCode() * 31 + this.displayName.hashCode()) * 31 + this.pokemon.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is StarterCategory) {
         return false;
      } else {
         val var2: StarterCategory = other as StarterCategory;
         if (!(this.name == (other as StarterCategory).name)) {
            return false;
         } else if (!(this.displayName == var2.displayName)) {
            return false;
         } else {
            return this.pokemon == var2.pokemon;
         }
      }
   }
}

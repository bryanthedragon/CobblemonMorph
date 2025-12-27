package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.network.chat.MutableComponent

public data RenderableStarterCategory(name: String, displayName: String, pokemon: List<RenderablePokemon>) {
   public final val displayName: String
   public final val displayNameText: MutableComponent
   public final val name: String
   public final val pokemon: List<RenderablePokemon>

   init {
      this.name = name;
      this.displayName = displayName;
      this.pokemon = pokemon;
      this.displayNameText = MiscUtilsKt.asTranslated(this.displayName);
   }

   public operator fun component1(): String {
      return this.name;
   }

   public operator fun component2(): String {
      return this.displayName;
   }

   public operator fun component3(): List<RenderablePokemon> {
      return this.pokemon;
   }

   public fun copy(name: String = this.name, displayName: String = this.displayName, pokemon: List<RenderablePokemon> = this.pokemon): RenderableStarterCategory {
      return new RenderableStarterCategory(name, displayName, pokemon);
   }

   public override fun toString(): String {
      return "RenderableStarterCategory(name=${this.name}, displayName=${this.displayName}, pokemon=${this.pokemon})";
   }

   public override fun hashCode(): Int {
      return (this.name.hashCode() * 31 + this.displayName.hashCode()) * 31 + this.pokemon.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is RenderableStarterCategory) {
         return false;
      } else {
         val var2: RenderableStarterCategory = other as RenderableStarterCategory;
         if (!(this.name == (other as RenderableStarterCategory).name)) {
            return false;
         } else if (!(this.displayName == var2.displayName)) {
            return false;
         } else {
            return this.pokemon == var2.pokemon;
         }
      }
   }
}

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.lighthing

public data LightingData(lightLevel: Int, liquidGlowMode: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.lighthing.LightingData.LiquidGlowMode) {
   public final val lightLevel: Int
   public final val liquidGlowMode: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.lighthing.LightingData.LiquidGlowMode

   init {
      this.lightLevel = lightLevel;
      this.liquidGlowMode = liquidGlowMode;
   }

   public operator fun component1(): Int {
      return this.lightLevel;
   }

   public operator fun component2(): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.lighthing.LightingData.LiquidGlowMode {
      return this.liquidGlowMode;
   }

   public fun copy(
      lightLevel: Int = this.lightLevel,
      liquidGlowMode: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.lighthing.LightingData.LiquidGlowMode = this.liquidGlowMode
   ): LightingData {
      return new LightingData(lightLevel, liquidGlowMode);
   }

   public override fun toString(): String {
      return "LightingData(lightLevel=${this.lightLevel}, liquidGlowMode=${this.liquidGlowMode})";
   }

   public override fun hashCode(): Int {
      return Integer.hashCode(this.lightLevel) * 31 + this.liquidGlowMode.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is LightingData) {
         return false;
      } else {
         val var2: LightingData = other as LightingData;
         if (this.lightLevel != (other as LightingData).lightLevel) {
            return false;
         } else {
            return this.liquidGlowMode === var2.liquidGlowMode;
         }
      }
   }

   public enum LiquidGlowMode(glowsInLand: Boolean, glowsUnderwater: Boolean) {
      LAND(true, false),
      UNDERWATER(false, true),
      BOTH(true, true)
      public final val glowsInLand: Boolean
      public final val glowsUnderwater: Boolean

      init {
         this.glowsInLand = glowsInLand;
         this.glowsUnderwater = glowsUnderwater;
      }
   }
}

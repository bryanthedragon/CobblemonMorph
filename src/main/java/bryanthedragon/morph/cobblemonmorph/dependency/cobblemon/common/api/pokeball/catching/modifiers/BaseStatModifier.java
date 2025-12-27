package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier.Behavior
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.world.entity.LivingEntity

public class BaseStatModifier(stat: Stat, comparator: (Int) -> Boolean, multiplier: Float) : CatchRateModifier {
   public final val comparator: (Int) -> Boolean
   public final val multiplier: Float
   public final val stat: Stat

   init {
      this.stat = stat;
      this.comparator = comparator;
      this.multiplier = multiplier;
      if (this.stat.getType() != Stat.Type.PERMANENT) {
         throw new IllegalArgumentException("${this.stat.getIdentifier()} is not of type PERMANENT");
      }
   }

   public override fun isGuaranteed(): Boolean {
      return false;
   }

   public override fun value(thrower: LivingEntity, pokemon: Pokemon): Float {
      return this.multiplier;
   }

   public override fun behavior(thrower: LivingEntity, pokemon: Pokemon): Behavior {
      return CatchRateModifier.Behavior.MULTIPLY;
   }

   public override fun isValid(thrower: LivingEntity, pokemon: Pokemon): Boolean {
      return pokemon.getForm().getBaseStats().get(this.stat) != null;
   }

   public override fun modifyCatchRate(currentCatchRate: Float, thrower: LivingEntity, pokemon: Pokemon): Float {
      return (this.behavior(thrower, pokemon).getMutator().invoke(currentCatchRate, this.value(thrower, pokemon)) as java.lang.Number).floatValue();
   }
}

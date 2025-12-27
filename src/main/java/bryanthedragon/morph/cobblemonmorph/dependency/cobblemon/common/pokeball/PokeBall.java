package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike

public open class PokeBall(name: ResourceLocation,
   catchRateModifier: CatchRateModifier = CatchRateModifier.Companion.getDUMMY$common(),
   effects: List<CaptureEffect> = CollectionsKt.emptyList(),
   waterDragValue: Float,
   model2d: ResourceLocation,
   model3d: ResourceLocation,
   throwPower: Float,
   ancient: Boolean
) {
   public final val ancient: Boolean
   public final val catchRateModifier: CatchRateModifier
   public final val effects: List<CaptureEffect>
   internal final lateinit var item: PokeBallItem
   public final val model2d: ResourceLocation
   public final val model3d: ResourceLocation
   public final val name: ResourceLocation
   public final val throwPower: Float
   public final val waterDragValue: Float

   init {
      this.name = name;
      this.catchRateModifier = catchRateModifier;
      this.effects = effects;
      this.waterDragValue = waterDragValue;
      this.model2d = model2d;
      this.model3d = model3d;
      this.throwPower = throwPower;
      this.ancient = ancient;
   }

   public fun item(): PokeBallItem {
      return this.getItem$common();
   }

   public fun stack(count: Int = 1): ItemStack {
      return new ItemStack(this.item() as ItemLike, count);
   }

   @Deprecated(message = "This is a temporary solution for the safari ball dilemma", replaceWith = @ReplaceWith(expression = "target.currentHealth", imports = []))
   internal fun hpForCalculation(target: Pokemon): Int {
      return if (this.name == PokeBalls.INSTANCE.getSAFARI_BALL().name) target.getHp() else target.getCurrentHealth();
   }
}

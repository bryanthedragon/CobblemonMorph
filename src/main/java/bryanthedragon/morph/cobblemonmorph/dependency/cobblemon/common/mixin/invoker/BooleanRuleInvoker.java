package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker;

import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Type;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BooleanValue.class)
public interface BooleanRuleInvoker {
   @Invoker("create")
   static Type<BooleanValue> cobblemon$create(boolean initialValue) {
      throw new UnsupportedOperationException();
   }
}

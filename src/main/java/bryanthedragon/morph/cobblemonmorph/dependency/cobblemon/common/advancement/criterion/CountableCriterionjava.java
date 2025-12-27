package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import net.minecraft.server.level.ServerPlayer

public fun SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition>.trigger(player: ServerPlayer, times: Int) {
   `$this$trigger`.trigger(player, new CountableContext(times));
}

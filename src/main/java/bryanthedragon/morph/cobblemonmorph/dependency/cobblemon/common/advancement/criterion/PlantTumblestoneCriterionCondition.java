package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.TumblestoneBlock
import com.google.gson.JsonObject
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level

public class PlantTumblestoneCriterionCondition(id: ResourceLocation, predicate: ContextAwarePredicate) : SimpleCriterionCondition(id, predicate) {
   public override fun toJson(json: JsonObject) {
   }

   public override fun fromJson(json: JsonObject) {
   }

   public open fun matches(player: ServerPlayer, context: PlantTumblestoneContext): Boolean {
      val var10000: TumblestoneBlock = context.getTumbleStoneBlock();
      val var10001: BlockPos = context.getPos();
      val var10002: Level = player.m_9236_();
      return var10000.canGrow(var10001, var10002 as BlockGetter);
   }
}

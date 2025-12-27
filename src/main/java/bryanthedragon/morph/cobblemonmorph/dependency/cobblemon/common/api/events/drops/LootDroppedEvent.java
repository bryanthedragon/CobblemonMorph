package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.drops

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity

public class LootDroppedEvent(table: DropTable, player: ServerPlayer?, entity: LivingEntity?, drops: MutableList<DropEntry>) : Cancelable {
   public final val drops: MutableList<DropEntry>
   public final val entity: LivingEntity?
   public final val player: ServerPlayer?
   public final val table: DropTable

   init {
      this.table = table;
      this.player = player;
      this.entity = entity;
      this.drops = drops;
   }
}

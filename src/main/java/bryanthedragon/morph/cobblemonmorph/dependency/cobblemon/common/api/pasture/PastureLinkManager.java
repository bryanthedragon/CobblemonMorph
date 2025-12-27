package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.ClosePasturePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MapExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.LinkedHashMap
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.functions.Function1
import net.minecraft.core.BlockPos
import net.minecraft.core.Position
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull

public object PastureLinkManager {
   public final val links: MutableMap<UUID, PastureLink> = (new LinkedHashMap()) as java.util.Map

   public fun getLinkByPlayerId(playerId: UUID): PastureLink? {
      return links.get(playerId);
   }

   public fun createLink(playerId: UUID, link: PastureLink) {
      links.put(playerId, link);
   }

   public fun getLinkByPlayer(player: ServerPlayer): PastureLink? {
      val var10001: UUID = player.m_20148_();
      val link: PastureLink = this.getLinkByPlayerId(var10001);
      if (link == null || link.getDimension() == player.m_9236_().m_220362_().m_135782_() && link.getPos().m_203195_(player.m_20182_() as Position, 10.0)) {
         return link;
      } else {
         links.remove(player.m_20148_());
         return null;
      }
   }

   public fun removeAt(world: ServerLevel, pos: BlockPos) {
      MapExtensionsKt.removeIf(links, (new Function1<Entry<? extends UUID, ? extends PastureLink>, java.lang.Boolean>(world, pos) {
         {
            super(1);
            this.$world = `$world`;
            this.$pos = `$pos`;
         }

         @NotNull
         public final java.lang.Boolean invoke(@NotNull Entry<UUID, PastureLink> var1) {
            val uuid: UUID = var1.getKey() as UUID;
            val pastureLink: PastureLink = var1.getValue() as PastureLink;
            val shouldRemove: Boolean = pastureLink.getDimension() == this.$world.m_220362_().m_135782_() && pastureLink.getPos() == this.$pos;
            val var10000: ServerPlayer = PlayerExtensionsKt.getPlayer(uuid);
            if (var10000 != null) {
               CobblemonNetwork.INSTANCE.sendPacket(var10000, new ClosePasturePacket());
            }

            return shouldRemove;
         }
      }) as (MutableMap.MutableEntry<UUID, PastureLink>?) -> java.lang.Boolean);
   }
}

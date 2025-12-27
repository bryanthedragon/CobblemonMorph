package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import java.util.LinkedHashMap
import java.util.UUID
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nPCLinkManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PCLinkManager.kt\ncom/cobblemon/mod/common/api/storage/pc/link/PCLinkManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,42:1\n1#2:43\n*E\n"])
public object PCLinkManager {
   private final val links: MutableMap<UUID, PCLink> = (new LinkedHashMap()) as java.util.Map

   public fun getLink(playerID: UUID): PCLink? {
      return links.get(playerID);
   }

   public fun addLink(pcLink: PCLink) {
      links.put(pcLink.getPlayerID(), pcLink);
   }

   public fun addLink(playerID: UUID, pcStore: PCStore, condition: (ServerPlayer) -> Boolean = <unrepresentable>.INSTANCE as Function1) {
      links.put(playerID, new PCLink(pcStore, playerID, condition) {
         {
            super(`$pcStore`, `$playerID`);
            this.$condition = `$condition`;
         }

         @Override
         public boolean isPermitted(@NotNull ServerPlayer player) {
            return this.$condition.invoke(player) as java.lang.Boolean;
         }
      });
   }

   public fun removeLink(playerID: UUID) {
      links.remove(playerID);
   }

   public fun getPC(player: ServerPlayer): PCStore? {
      val var10001: UUID = player.m_20148_();
      var var10000: PCLink = this.getLink(var10001);
      if (var10000 != null) {
         var10000 = if (var10000.isPermitted(player)) var10000 else null;
         if (var10000 != null) {
            return var10000.getPc();
         }
      }

      return null;
   }
}

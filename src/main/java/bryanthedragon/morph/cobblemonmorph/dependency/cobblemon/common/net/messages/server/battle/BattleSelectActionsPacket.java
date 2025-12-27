package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

@SourceDebugExtension(["SMAP\nBattleSelectActionsPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleSelectActionsPacket.kt\ncom/cobblemon/mod/common/net/messages/server/battle/BattleSelectActionsPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,42:1\n1855#2,2:43\n*S KotlinDebug\n*F\n+ 1 BattleSelectActionsPacket.kt\ncom/cobblemon/mod/common/net/messages/server/battle/BattleSelectActionsPacket\n*L\n27#1:43,2\n*E\n"])
public class BattleSelectActionsPacket(battleId: UUID, showdownActionResponses: List<ShowdownActionResponse>) : NetworkPacket<BattleSelectActionsPacket> {
   public final val battleId: UUID
   public open val id: ResourceLocation
   public final val showdownActionResponses: List<ShowdownActionResponse>

   init {
      this.battleId = battleId;
      this.showdownActionResponses = showdownActionResponses;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.battleId);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.showdownActionResponses.size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as ShowdownActionResponse).saveToBuffer(buffer);
      }
   }

   override fun sendToPlayer(player: ServerPlayer) {
      NetworkPacket.DefaultImpls.sendToPlayer(this, player);
   }

   override fun sendToPlayers(players: MutableIterable<ServerPlayer>) {
      NetworkPacket.DefaultImpls.sendToPlayers(this, players);
   }

   override fun sendToAllPlayers() {
      NetworkPacket.DefaultImpls.sendToAllPlayers(this);
   }

   override fun sendToServer() {
      NetworkPacket.DefaultImpls.sendToServer(this);
   }

   override fun sendToPlayersAround(
      x: Double, y: Double, z: Double, distance: Double, worldKey: ResourceKey<Level>, exclusionCondition: (ServerPlayer?) -> java.lang.Boolean
   ) {
      NetworkPacket.DefaultImpls.sendToPlayersAround(this, x, y, z, distance, worldKey, exclusionCondition);
   }

   override fun toBuffer(): FriendlyByteBuf {
      return NetworkPacket.DefaultImpls.toBuffer(this);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BattleSelectActionsPacket {
         val battleId: UUID = buffer.m_130259_();
         val responses: java.util.List = new ArrayList();
         val var4: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

         for (int var5 = 0; var5 < var4; var5++) {
            responses.add(ShowdownActionResponse.Companion.loadFromBuffer(buffer));
         }

         return new BattleSelectActionsPacket(battleId, responses);
      }
   }
}

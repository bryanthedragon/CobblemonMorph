package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.ClientPlayNetworkHandlerInvoker
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.PacketListener
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.PacketUtils
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.thread.BlockableEventLoop
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

public abstract class SpawnExtraDataEntityPacket<T extends NetworkPacket<T>, E extends Entity> : NetworkPacket<T> {
   private final val vanillaSpawnPacket: ClientboundAddEntityPacket

   open fun SpawnExtraDataEntityPacket(vanillaSpawnPacket: ClientboundAddEntityPacket) {
      this.vanillaSpawnPacket = vanillaSpawnPacket;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      this.encodeEntityData(buffer);
      this.vanillaSpawnPacket.m_5779_(buffer);
   }

   public abstract fun encodeEntityData(buffer: FriendlyByteBuf) {
   }

   public abstract fun applyData(entity: Any) {
   }

   public abstract fun checkType(entity: Entity): Boolean {
   }

   public fun spawnAndApply(client: Minecraft) {
      client.execute(SpawnExtraDataEntityPacket::spawnAndApply$lambda$0);
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

   @JvmStatic
   fun `spawnAndApply$lambda$0`(`$client`: Minecraft, `this$0`: SpawnExtraDataEntityPacket) {
      if (`$client`.f_91074_ != null) {
         val player: LocalPlayer = `$client`.f_91074_;
         val entity: Level = `$client`.f_91074_.m_9236_();
         val var10000: ClientLevel = entity as? ClientLevel;
         if ((entity as? ClientLevel) != null) {
            PacketUtils.m_131363_(`this$0`.vanillaSpawnPacket as Packet, player.f_108617_ as PacketListener, `$client` as BlockableEventLoop);
            val var7: Entity = `this$0`.vanillaSpawnPacket.m_131508_().m_20615_(var10000 as Level);
            if (var7 != null) {
               var7.m_141965_(`this$0`.vanillaSpawnPacket);
               var7.m_20256_(
                  new Vec3(`this$0`.vanillaSpawnPacket.m_131503_(), `this$0`.vanillaSpawnPacket.m_131504_(), `this$0`.vanillaSpawnPacket.m_131505_())
               );
               if (`this$0`.checkType(var7)) {
                  `this$0`.applyData(var7);
               }

               var10000.m_104627_(`this$0`.vanillaSpawnPacket.m_131496_(), var7);
               val var8: ClientPacketListener = player.f_108617_;
               (var8 as ClientPlayNetworkHandlerInvoker).callPlaySpawnSound(var7);
            }
         }
      }
   }

   public companion object {
      public fun decodeVanillaPacket(buffer: FriendlyByteBuf): ClientboundAddEntityPacket {
         return new ClientboundAddEntityPacket(buffer);
      }
   }
}

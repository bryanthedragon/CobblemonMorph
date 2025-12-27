package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt
import io.netty.buffer.Unpooled
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level

public interface NetworkPacket<T extends NetworkPacket<T>> : Encodable {
   public val id: ResourceLocation

   public open fun sendToPlayer(player: ServerPlayer) {
   }

   public open fun sendToPlayers(players: Iterable<ServerPlayer>) {
   }

   public open fun sendToAllPlayers() {
   }

   public open fun sendToServer() {
   }

   public open fun sendToPlayersAround(
      x: Double,
      y: Double,
      z: Double,
      distance: Double,
      worldKey: ResourceKey<Level>,
      exclusionCondition: (ServerPlayer) -> Boolean = ...
   ) {
   }

   public open fun toBuffer(): FriendlyByteBuf {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nNetworkPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NetworkPacket.kt\ncom/cobblemon/mod/common/api/net/NetworkPacket$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,99:1\n766#2:100\n857#2,2:101\n1855#2,2:103\n*S KotlinDebug\n*F\n+ 1 NetworkPacket.kt\ncom/cobblemon/mod/common/api/net/NetworkPacket$DefaultImpls\n*L\n77#1:100\n77#1:101,2\n85#1:103,2\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends NetworkPacket<T>> sendToPlayer(`$this`: NetworkPacket<T>, player: ServerPlayer) {
         CobblemonNetwork.INSTANCE.sendPacketToPlayer(player, `$this`);
      }

      @JvmStatic
      fun <T extends NetworkPacket<T>> sendToPlayers(`$this`: NetworkPacket<T>, players: MutableIterable<ServerPlayer>) {
         if (CollectionsKt.any(players)) {
            CobblemonNetwork.INSTANCE.sendPacketToPlayers(players, `$this`);
         }
      }

      @JvmStatic
      fun <T extends NetworkPacket<T>> sendToAllPlayers(`$this`: NetworkPacket<T>) {
         CobblemonNetwork.INSTANCE.sendToAllPlayers(`$this`);
      }

      @JvmStatic
      fun <T extends NetworkPacket<T>> sendToServer(`$this`: NetworkPacket<T>) {
         CobblemonNetwork.INSTANCE.sendPacketToServer(`$this`);
      }

      @JvmStatic
      fun <T extends NetworkPacket<T>> sendToPlayersAround(
         `$this`: NetworkPacket<T>,
         x: Double,
         y: Double,
         z: Double,
         distance: Double,
         worldKey: ResourceKey<Level>,
         exclusionCondition: (ServerPlayer?) -> java.lang.Boolean
      ) {
         val var10000: MinecraftServer = DistributionUtilsKt.server();
         if (var10000 != null) {
            val var32: java.util.List = var10000.m_6846_().m_11314_();
            val `$this$forEach$iv`: java.lang.Iterable = var32;
            val `element$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$filter$iv) {
               val player: ServerPlayer = `element$iv$iv` as ServerPlayer;
               val var33: Boolean;
               if (exclusionCondition.invoke(player) as java.lang.Boolean) {
                  var33 = false;
               } else {
                  val xDiff: Double = x - player.m_20185_();
                  val yDiff: Double = y - player.m_20186_();
                  var33 = xDiff * xDiff + yDiff * yDiff + (z - player.m_20189_()) < distance * distance;
               }

               if (var33) {
                  `element$iv`.add(`element$iv$iv`);
               }
            }

            for (Object element$ivx : $this$filter$iv) {
               val playerx: ServerPlayer = `element$ivx` as ServerPlayer;
               val var34: CobblemonNetwork = CobblemonNetwork.INSTANCE;
               var34.sendPacketToPlayer(playerx, `$this`);
            }
         }
      }

      @JvmStatic
      fun <T extends NetworkPacket<T>> toBuffer(`$this`: NetworkPacket<T>): FriendlyByteBuf {
         val buffer: FriendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
         `$this`.encode(buffer);
         return buffer;
      }
   }
}

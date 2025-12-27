package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.net

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.NetworkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.function.Supplier
import kotlin.reflect.KClass
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.PacketDistributor
import net.minecraftforge.network.NetworkEvent.Context
import net.minecraftforge.network.simple.SimpleChannel

public object CobblemonForgeNetworkManager : NetworkManager {
   private const val PROTOCOL_VERSION: String = "1"
   private final val channel: SimpleChannel =
      NetworkRegistry.newSimpleChannel(MiscUtilsKt.cobblemonResource("main"), CobblemonForgeNetworkManager::channel$lambda$0, "1"::equals, "1"::equals)
      private final var id: Int

   public override fun registerClientBound() {
      CobblemonNetwork.INSTANCE.registerClientBound();
   }

   public override fun registerServerBound() {
      CobblemonNetwork.INSTANCE.registerServerBound();
   }

   public override fun <T : NetworkPacket<Any>> createClientBound(
      identifier: ResourceLocation,
      kClass: KClass<Any>,
      encoder: (Any, FriendlyByteBuf) -> Unit,
      decoder: (FriendlyByteBuf) -> Any,
      handler: ClientNetworkPacketHandler<Any>
   ) {
      channel.registerMessage(
         id++,
         JvmClassMappingKt.getJavaClass(kClass),
         (p0$p1, p1$p2) -> `$tmp_proxy_0`.invoke(`p0$p1`, `p1$p2`),
         decoder::invoke,
         CobblemonForgeNetworkManager::createClientBound$lambda$1
      );
   }

   public override fun <T : NetworkPacket<Any>> createServerBound(
      identifier: ResourceLocation,
      kClass: KClass<Any>,
      encoder: (Any, FriendlyByteBuf) -> Unit,
      decoder: (FriendlyByteBuf) -> Any,
      handler: ServerNetworkPacketHandler<Any>
   ) {
      channel.registerMessage(
         id++,
         JvmClassMappingKt.getJavaClass(kClass),
         (p0$p1, p1$p2) -> `$tmp_proxy_0`.invoke(`p0$p1`, `p1$p2`),
         decoder::invoke,
         CobblemonForgeNetworkManager::createServerBound$lambda$3
      );
   }

   public override fun sendPacketToPlayer(player: ServerPlayer, packet: NetworkPacket<*>) {
      channel.send(PacketDistributor.PLAYER.with(CobblemonForgeNetworkManager::sendPacketToPlayer$lambda$4), packet);
   }

   public override fun sendPacketToServer(packet: NetworkPacket<*>) {
      channel.sendToServer(packet);
   }

   public override fun <T : NetworkPacket<*>> asVanillaClientBound(packet: Any): Packet<ClientGamePacketListener> {
      val var10000: Packet = channel.toVanillaPacket(packet, NetworkDirection.PLAY_TO_CLIENT);
      return var10000;
   }

   @JvmStatic
   fun `channel$lambda$0`(): java.lang.String {
      return "1";
   }

   @JvmStatic
   fun `createClientBound$lambda$1`(`$handler`: ClientNetworkPacketHandler, msg: NetworkPacket, ctx: Supplier) {
      val context: Context = ctx.get() as Context;
      `$handler`.handleOnNettyThread(msg);
      context.setPacketHandled(true);
   }

   @JvmStatic
   fun `createServerBound$lambda$3`(`$handler`: ServerNetworkPacketHandler, msg: NetworkPacket, ctx: Supplier) {
      val context: Context = ctx.get() as Context;
      val var10002: ServerPlayer = context.getSender();
      val var4: MinecraftServer = var10002.f_8924_;
      val var10003: ServerPlayer = context.getSender();
      `$handler`.handleOnNettyThread(msg, var4, var10003);
      context.setPacketHandled(true);
   }

   @JvmStatic
   fun `sendPacketToPlayer$lambda$4`(`$player`: ServerPlayer): ServerPlayer {
      return `$player`;
   }
}

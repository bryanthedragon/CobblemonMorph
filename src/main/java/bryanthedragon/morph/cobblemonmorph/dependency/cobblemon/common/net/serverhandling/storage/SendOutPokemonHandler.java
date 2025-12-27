package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.SendOutPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ActivePokemonState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.ClipContext.Fluid
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nSendOutPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SendOutPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/SendOutPokemonHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,48:1\n1#2:49\n*E\n"])
public object SendOutPokemonHandler : ServerNetworkPacketHandler<SendOutPokemonPacket> {
   public const val SEND_OUT_DURATION: Float = 1.5F

   public open fun handle(packet: SendOutPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      val pokemon: Int = packet.getSlot();
      val state: Int = pokemon.intValue();
      val var10000: Int = if (state >= 0) pokemon else null;
      if ((if (state >= 0) pokemon else null) != null) {
         val var13: Pokemon = Cobblemon.INSTANCE.getStorage().getParty(player).get(var10000);
         if (var13 != null) {
            if (!var13.isFainted()) {
               val var10: PokemonState = var13.getState();
               if (var10 !is ShoulderedState && var10 is ActivePokemonState) {
                  val var12: PokemonEntity = (var10 as ActivePokemonState).getEntity();
                  if (var12 != null) {
                     var12.recallWithAnimation();
                  } else {
                     var13.recall();
                  }
               } else {
                  val var11: Vec3 = PlayerExtensionsKt.raycastSafeSendout(player, var13, 12.0, 5.0, Fluid.ANY);
                  if (var11 != null) {
                     val var10001: LivingEntity = player as LivingEntity;
                     val var10002: ServerLevel = player.m_284548_();
                     Pokemon.sendOutWithAnimation$default(var13, var10001, var10002, var11, null, false, null, null, 120, null);
                  }
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: SendOutPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}

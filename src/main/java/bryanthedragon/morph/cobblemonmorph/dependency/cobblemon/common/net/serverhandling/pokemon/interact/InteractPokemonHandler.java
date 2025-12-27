package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.interact

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.interact.InteractPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

@SourceDebugExtension(["SMAP\nInteractPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InteractPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pokemon/interact/InteractPokemonHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,32:1\n2624#2,3:33\n*S KotlinDebug\n*F\n+ 1 InteractPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pokemon/interact/InteractPokemonHandler\n*L\n23#1:33,3\n*E\n"])
public object InteractPokemonHandler : ServerNetworkPacketHandler<InteractPokemonPacket> {
   public open fun handle(packet: InteractPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      val pokemonEntity: Entity = player.m_284548_().m_8791_(packet.getPokemonID());
      if (pokemonEntity is PokemonEntity) {
         if (packet.getMountShoulder()) {
            if (!(pokemonEntity as PokemonEntity).m_29897_()) {
               return;
            }

            val `$this$none$iv`: java.lang.Iterable = PlayerExtensionsKt.party(player);
            var var10000: Boolean;
            if (`$this$none$iv` is java.util.Collection && (`$this$none$iv` as java.util.Collection).isEmpty()) {
               var10000 = true;
            } else {
               val var7: java.util.Iterator = `$this$none$iv`.iterator();

               while (true) {
                  if (var7.hasNext()) {
                     if (!(var7.next() as Pokemon == (pokemonEntity as PokemonEntity).getPokemon())) {
                        continue;
                     }

                     var10000 = false;
                     break;
                  }

                  var10000 = true;
                  break;
               }
            }

            if (var10000) {
               return;
            }

            (pokemonEntity as PokemonEntity).tryMountingShoulder(player);
         } else {
            val var11: PokemonEntity = pokemonEntity as PokemonEntity;
            val var10001: Player = player as Player;
            val var10002: ItemStack = player.m_21205_();
            var11.offerHeldItem(var10001, var10002);
         }
      }
   }

   fun handleOnNettyThread(packet: InteractPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}

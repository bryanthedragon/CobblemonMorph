package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.ReleasePokemonEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.ReleasePCPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Arrays
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nReleasePCPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReleasePCPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/party/ReleasePCPokemonHandler\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,39:1\n40#2:40\n41#2,4:44\n46#2:57\n47#2:60\n17#3,2:41\n14#3,5:48\n19#3:56\n19#3:59\n13579#4:43\n13579#4:53\n13580#4:55\n13580#4:58\n14#5:54\n*S KotlinDebug\n*F\n+ 1 ReleasePCPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/party/ReleasePCPokemonHandler\n*L\n28#1:40\n28#1:44,4\n28#1:57\n28#1:60\n28#1:41,2\n32#1:48,5\n32#1:56\n28#1:59\n28#1:43\n32#1:53\n32#1:55\n28#1:58\n32#1:54\n*E\n"])
public object ReleasePCPokemonHandler : ServerNetworkPacketHandler<ReleasePCPokemonPacket> {
   public open fun handle(packet: ReleasePCPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: PCStore = PCLinkManager.INSTANCE.getPC(player);
      if (var10000 != null) {
         val pc: PCStore = var10000;
         val var34: Pokemon = var10000.get(packet.getPosition());
         if (var34 != null) {
            val pokemon: Pokemon = var34;
            if (var34.getUuid() == packet.getPokemonID()) {
               val var6: CancelableObservable = CobblemonEvents.POKEMON_RELEASED_EVENT_PRE;
               val var7: ReleasePokemonEvent.Pre = new ReleasePokemonEvent.Pre(player, var34, var10000);
               val `this_$iv$iv`: EventObservable = var6;
               val `events$iv$iv`: Array<Cancelable> = new Cancelable[]{var7};
               `this_$iv$iv`.emit(Arrays.copyOf(`events$iv$iv`, `events$iv$iv`.length));

               for (Object element$iv$iv$iv : events$iv$iv) {
                  if (((Cancelable)`element$iv$iv$iv`).isCanceled()) {
                     val var32: ReleasePokemonEvent.Pre = `element$iv$iv$iv` as ReleasePokemonEvent.Pre;
                     pc.set(packet.getPosition(), pokemon);
                  } else {
                     val preEvent: ReleasePokemonEvent.Pre = `element$iv$iv$iv` as ReleasePokemonEvent.Pre;
                     pc.remove(packet.getPosition());
                     val `$this$iv`: EventObservable = CobblemonEvents.POKEMON_RELEASED_EVENT_POST;
                     val `events$iv`: Array<ReleasePokemonEvent.Post> = new ReleasePokemonEvent.Post[]{new ReleasePokemonEvent.Post(player, pokemon, pc)};
                     `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

                     for (Object element$iv$iv : events$iv) {
                        ;
                     }
                  }
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: ReleasePCPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}

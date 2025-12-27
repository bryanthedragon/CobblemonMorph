package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.ReleasePokemonEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings.ServerSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.party.ReleasePartyPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.Arrays
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nReleasePartyPokemonHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReleasePartyPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/pc/ReleasePartyPokemonHandler\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,41:1\n40#2:42\n41#2,4:46\n46#2:59\n47#2:62\n17#3,2:43\n14#3,5:50\n19#3:58\n19#3:61\n13579#4:45\n13579#4:55\n13580#4:57\n13580#4:60\n14#5:56\n*S KotlinDebug\n*F\n+ 1 ReleasePartyPokemonHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/storage/pc/ReleasePartyPokemonHandler\n*L\n28#1:42\n28#1:46,4\n28#1:59\n28#1:62\n28#1:43,2\n34#1:50,5\n34#1:58\n28#1:61\n28#1:45\n34#1:55\n34#1:57\n28#1:60\n34#1:56\n*E\n"])
public object ReleasePartyPokemonHandler : ServerNetworkPacketHandler<ReleasePartyPokemonPacket> {
   public open fun handle(packet: ReleasePartyPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      val party: PlayerPartyStore = PlayerExtensionsKt.party(player);
      val var10000: Pokemon = party.get(packet.getPosition());
      if (var10000 != null) {
         val pokemon: Pokemon = var10000;
         if (var10000.getUuid() == packet.getPokemonID()) {
            val var6: CancelableObservable = CobblemonEvents.POKEMON_RELEASED_EVENT_PRE;
            val var7: ReleasePokemonEvent.Pre = new ReleasePokemonEvent.Pre(player, var10000, party);
            val `this_$iv$iv`: EventObservable = var6;
            val `events$iv$iv`: Array<Cancelable> = new Cancelable[]{var7};
            `this_$iv$iv`.emit(Arrays.copyOf(`events$iv$iv`, `events$iv$iv`.length));

            for (Object element$iv$iv$iv : events$iv$iv) {
               if (((Cancelable)`element$iv$iv$iv`).isCanceled()) {
                  val var32: ReleasePokemonEvent.Pre = `element$iv$iv$iv` as ReleasePokemonEvent.Pre;
                  party.set(packet.getPosition(), pokemon);
               } else {
                  val preEvent: ReleasePokemonEvent.Pre = `element$iv$iv$iv` as ReleasePokemonEvent.Pre;
                  if (ServerSettings.INSTANCE.getPreventCompletePartyDeposit() && CollectionsKt.filterNotNull(party).size() <= 1) {
                     return;
                  }

                  party.remove(pokemon);
                  val `$this$iv`: EventObservable = CobblemonEvents.POKEMON_RELEASED_EVENT_POST;
                  val `events$iv`: Array<ReleasePokemonEvent.Post> = new ReleasePokemonEvent.Post[]{new ReleasePokemonEvent.Post(player, pokemon, party)};
                  `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

                  for (Object element$iv$iv : events$iv) {
                     ;
                  }
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: ReleasePartyPokemonPacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}

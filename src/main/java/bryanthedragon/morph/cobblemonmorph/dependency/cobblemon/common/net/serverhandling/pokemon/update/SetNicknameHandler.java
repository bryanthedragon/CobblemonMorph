package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonNicknamedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.NicknameUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.SetNicknamePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.Arrays
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nSetNicknameHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SetNicknameHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pokemon/update/SetNicknameHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,49:1\n1#2:50\n40#3:51\n41#3,6:55\n47#3:63\n17#4,2:52\n19#4:62\n13579#5:54\n13580#5:61\n*S KotlinDebug\n*F\n+ 1 SetNicknameHandler.kt\ncom/cobblemon/mod/common/net/serverhandling/pokemon/update/SetNicknameHandler\n*L\n35#1:51\n35#1:55,6\n35#1:63\n35#1:52,2\n35#1:62\n35#1:54\n35#1:61\n*E\n"])
public object SetNicknameHandler : ServerNetworkPacketHandler<SetNicknamePacket> {
   public open fun handle(packet: SetNicknamePacket, server: MinecraftServer, player: ServerPlayer) {
      val var10000: PokemonStore;
      if (packet.isParty()) {
         var10000 = PlayerExtensionsKt.party(player);
      } else {
         val var33: PCStore = PCLinkManager.INSTANCE.getPC(player);
         if (var33 == null) {
            val var28: SetNicknameHandler = this;
            new ClosePCPacket(null).sendToPlayer(player);
            return;
         }

         var10000 = var33;
      }

      val var34: Pokemon = var10000.get(packet.getPokemonUUID());
      if (var34 != null) {
         val pokemon: Pokemon = var34;
         val var6: CancelableObservable = CobblemonEvents.POKEMON_NICKNAMED;
         var var35: ServerPlayer = player;
         var var10001: Pokemon = var34;
         val var10002: java.lang.String = packet.getNickname();
         val var36: MutableComponent;
         if (var10002 != null) {
            val var24: MutableComponent = Component.m_237113_(var10002);
            var35 = player;
            var10001 = var34;
            var36 = var24;
         } else {
            var36 = null;
         }

         val `$this$handle_u24lambda_u240`: PokemonNicknamedEvent = new PokemonNicknamedEvent(var35, var10001, var36);
         val var29: EventObservable = var6;
         val var30: Array<Cancelable> = new Cancelable[]{`$this$handle_u24lambda_u240`};
         var29.emit(Arrays.copyOf(var30, var30.length));

         for (Object element$iv$iv$iv : var30) {
            if (((Cancelable)`element$iv$iv$iv`).isCanceled()) {
               val it: PokemonNicknamedEvent = `element$iv$iv$iv` as PokemonNicknamedEvent;
               CobblemonNetwork.INSTANCE.sendPacket(player, new NicknameUpdatePacket((new Function0<Pokemon>(pokemon) {
                  {
                     super(0);
                     this.$pokemon = `$pokemon`;
                  }

                  @NotNull
                  public final Pokemon invoke() {
                     return this.$pokemon;
                  }
               }) as () -> Pokemon, pokemon.getNickname()));
            } else {
               pokemon.setNickname((`element$iv$iv$iv` as PokemonNicknamedEvent).getNickname());
            }
         }
      }
   }

   fun handleOnNettyThread(packet: SetNicknamePacket, server: MinecraftServer, player: ServerPlayer) {
      ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet, server, player);
   }
}

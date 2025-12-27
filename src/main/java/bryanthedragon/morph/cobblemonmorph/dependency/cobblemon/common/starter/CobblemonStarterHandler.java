package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.starter.StarterChosenEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.starter.StarterHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterCategory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.OpenStarterUIPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules.CobblemonGameRules
import java.util.Arrays
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player

@SourceDebugExtension(["SMAP\nCobbledStarterHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobbledStarterHandler.kt\ncom/cobblemon/mod/common/starter/CobblemonStarterHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 6 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n*L\n1#1,73:1\n1#2:74\n39#3,2:75\n41#3,2:80\n44#3,3:83\n47#3:88\n17#4,2:77\n19#4:87\n13579#5:79\n13580#5:86\n39#6:82\n*S KotlinDebug\n*F\n+ 1 CobbledStarterHandler.kt\ncom/cobblemon/mod/common/starter/CobblemonStarterHandler\n*L\n59#1:75,2\n59#1:80,2\n59#1:83,3\n59#1:88\n59#1:77,2\n59#1:87\n59#1:79\n59#1:86\n59#1:82\n*E\n"])
public open class CobblemonStarterHandler : StarterHandler {
   public override fun getStarterList(player: ServerPlayer): MutableList<StarterCategory> {
      return Cobblemon.INSTANCE.getStarterConfig().getStarters();
   }

   public override fun handleJoin(player: ServerPlayer) {
   }

   public override fun requestStarterChoice(player: ServerPlayer) {
      val playerData: PlayerData = Cobblemon.INSTANCE.getPlayerData().get(player as Player);
      if (playerData.getStarterSelected()) {
         playerData.sendToPlayer(player);
         val var10001: MutableComponent = LocalizationUtilsKt.lang("ui.starter.alreadyselected");
         player.m_5661_(TextKt.red(var10001) as Component, true);
      } else if (playerData.getStarterLocked()) {
         val var3: MutableComponent = LocalizationUtilsKt.lang("ui.starter.cannotchoose");
         player.m_5661_(TextKt.red(var3) as Component, true);
      } else {
         new OpenStarterUIPacket(this.getStarterList(player)).sendToPlayer(player);
         playerData.setStarterPrompted(true);
         Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
      }
   }

   public override fun chooseStarter(player: ServerPlayer, categoryName: String, index: Int) {
      val playerData: PlayerData = Cobblemon.INSTANCE.getPlayerData().get(player as Player);
      if (playerData.getStarterSelected()) {
         val var35: MutableComponent = LocalizationUtilsKt.lang("ui.starter.alreadyselected");
         player.m_5661_(TextKt.red(var35) as Component, true);
      } else if (playerData.getStarterLocked()) {
         val var10001: MutableComponent = LocalizationUtilsKt.lang("ui.starter.cannotchoose");
         player.m_5661_(TextKt.red(var10001) as Component, true);
      } else {
         val `event$iv`: java.util.Iterator = this.getStarterList(player).iterator();

         var var10000: Any;
         while (true) {
            if (`event$iv`.hasNext()) {
               val var10: Any = `event$iv`.next();
               if (!((var10 as StarterCategory).getName() == categoryName)) {
                  continue;
               }

               var10000 = (PlayerPartyStore)var10;
               break;
            }

            var10000 = null;
            break;
         }

         var10000 = var10000 as StarterCategory;
         if (var10000 as StarterCategory != null) {
            if (index <= ((StarterCategory)var10000).getPokemon().size()) {
               val properties: PokemonProperties = ((StarterCategory)var10000).getPokemon().get(index);
               val pokemon: Pokemon = properties.create();
               val var28: CancelableObservable = CobblemonEvents.STARTER_CHOSEN;
               val var29: Cancelable = new StarterChosenEvent(player, properties, pokemon);
               val var31: EventObservable = var28;
               val `events$iv$iv`: Array<Cancelable> = new Cancelable[]{var29};
               var31.emit(Arrays.copyOf(`events$iv$iv`, `events$iv$iv`.length));

               for (Object element$iv$iv$iv : events$iv$iv) {
                  if (!((Cancelable)`element$iv$iv$iv`).isCanceled()) {
                     val it: StarterChosenEvent = `element$iv$iv$iv` as StarterChosenEvent;
                     var10000 = Cobblemon.INSTANCE.getStorage().getParty(player);
                     val var24: Pokemon = it.getPokemon();
                     playerData.setStarterSelected(true);
                     playerData.setStarterUUID(var24.getUuid());
                     if (player.m_9236_().m_46469_().m_46207_(CobblemonGameRules.SHINY_STARTERS)) {
                        pokemon.setShiny(true);
                     }

                     var10000.add(var24);
                     CobblemonCriteria.INSTANCE.getPICK_STARTER().trigger(player, pokemon);
                     Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
                     playerData.sendToPlayer(player);
                  }
               }
            }
         }
      }
   }
}

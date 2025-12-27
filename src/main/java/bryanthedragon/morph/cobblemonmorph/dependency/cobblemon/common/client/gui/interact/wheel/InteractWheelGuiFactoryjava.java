@file:SourceDebugExtension(["SMAP\nInteractWheelGuiFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InteractWheelGuiFactory.kt\ncom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelGuiFactoryKt\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,111:1\n14#2,5:112\n19#2:120\n13579#3:117\n13580#3:119\n14#4:118\n1549#5:121\n1620#5,3:122\n*S KotlinDebug\n*F\n+ 1 InteractWheelGuiFactory.kt\ncom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelGuiFactoryKt\n*L\n53#1:112,5\n53#1:120\n53#1:117\n53#1:119\n53#1:118\n95#1:121\n95#1:122,3\n*E\n"])

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.PokemonInteractionGUICreationEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleChallenge
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTradeOffer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PlayerInteractOptionsPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BattleChallengePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.SpectateBattlePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.interact.InteractPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.AcceptTradeRequestPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.OfferTradePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import java.util.ArrayList;
import java.util.Arrays
import java.util.UUID
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.Nullable
import org.joml.Vector3f

public fun createPokemonInteractGui(pokemonID: UUID, canMountShoulder: Boolean): InteractWheelGUI {
   val mountShoulder: InteractWheelOption = new InteractWheelOption(
      MiscUtilsKt.cobblemonResource("textures/gui/interact/icon_shoulder.png"),
      "cobblemon.ui.interact.mount.shoulder",
      null,
      (new Function0<Unit>(canMountShoulder, pokemonID) {
         {
            super(0);
            this.$canMountShoulder = `$canMountShoulder`;
            this.$pokemonID = `$pokemonID`;
         }

         public final void invoke() {
            if (this.$canMountShoulder) {
               new InteractPokemonPacket(this.$pokemonID, true).sendToServer();
               InteractWheelGuiFactoryKt.access$closeGUI();
            }
         }
      }) as Function0,
      4,
      null
   );
   val giveItem: InteractWheelOption = new InteractWheelOption(
      MiscUtilsKt.cobblemonResource("textures/gui/interact/icon_held_item.png"), "cobblemon.ui.interact.give.item", null, (new Function0<Unit>(pokemonID) {
         {
            super(0);
            this.$pokemonID = `$pokemonID`;
         }

         public final void invoke() {
            new InteractPokemonPacket(this.$pokemonID, false).sendToServer();
            InteractWheelGuiFactoryKt.access$closeGUI();
         }
      }) as Function0, 4, null
   );
   val var10000: ArrayListMultimap = ArrayListMultimap.create();
   val options: Multimap = var10000 as Multimap;
   (var10000 as Multimap).put(Orientation.TOP_RIGHT, giveItem);
   if (canMountShoulder) {
      options.put(Orientation.TOP_LEFT, mountShoulder);
   }

   val `$this$iv`: EventObservable = CobblemonEvents.POKEMON_INTERACTION_GUI_CREATION;
   val `events$iv`: Array<PokemonInteractionGUICreationEvent> = new PokemonInteractionGUICreationEvent[]{
      new PokemonInteractionGUICreationEvent(pokemonID, canMountShoulder, options)
   };
   `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

   for (Object element$iv$iv : events$iv) {
      ;
   }

   val var10003: MutableComponent = Component.m_237115_("cobblemon.ui.interact.pokemon");
   return new InteractWheelGUI(options, var10003 as Component);
}

public fun createPlayerInteractGui(optionsPacket: PlayerInteractOptionsPacket): InteractWheelGUI {
   val trade: InteractWheelOption = new InteractWheelOption(
      MiscUtilsKt.cobblemonResource("textures/gui/interact/icon_trade.png"), "cobblemon.ui.interact.trade", (new Function0<Vector3f>(optionsPacket) {
         {
            super(0);
            this.$optionsPacket = `$optionsPacket`;
         }

         @Nullable
         public final Vector3f invoke() {
            val `$this$any$iv`: java.lang.Iterable = CobblemonClient.INSTANCE.getRequests().getTradeOffers();
            val var2: PlayerInteractOptionsPacket = this.$optionsPacket;
            var var10000: Boolean;
            if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
               var10000 = false;
            } else {
               val var4: java.util.Iterator = `$this$any$iv`.iterator();

               while (true) {
                  if (!var4.hasNext()) {
                     var10000 = false;
                     break;
                  }

                  if ((var4.next() as ClientTradeOffer).getTraderId() == var2.getTargetId()) {
                     var10000 = true;
                     break;
                  }
               }
            }

            return if (var10000) new Vector3f(0.0F, 0.6F, 0.0F) else null;
         }
      }) as () -> Vector3f, (new Function0<Unit>(optionsPacket) {
         {
            super(0);
            this.$optionsPacket = `$optionsPacket`;
         }

         public final void invoke() {
            val var2: java.lang.Iterable = CobblemonClient.INSTANCE.getRequests().getTradeOffers();
            val var3: PlayerInteractOptionsPacket = this.$optionsPacket;
            val var5: java.util.Iterator = var2.iterator();

            var var10000: Any;
            while (true) {
               if (var5.hasNext()) {
                  val var6: Any = var5.next();
                  if (!((var6 as ClientTradeOffer).getTraderId() == var3.getTargetId())) {
                     continue;
                  }

                  var10000 = var6;
                  break;
               }

               var10000 = null;
               break;
            }

            val tradeOffer: ClientTradeOffer = var10000 as ClientTradeOffer;
            if (var10000 as ClientTradeOffer == null) {
               CobblemonNetwork.INSTANCE.sendToServer(new OfferTradePacket(this.$optionsPacket.getTargetId()));
            } else {
               CobblemonClient.INSTANCE.getRequests().getTradeOffers().remove(tradeOffer);
               CobblemonNetwork.INSTANCE.sendToServer(new AcceptTradeRequestPacket(tradeOffer.getTradeOfferId()));
            }

            InteractWheelGuiFactoryKt.access$closeGUI();
         }
      }) as () -> Unit
   );
   val var15: InteractWheelOption = new InteractWheelOption(
      MiscUtilsKt.cobblemonResource("textures/gui/interact/icon_battle.png"), "cobblemon.ui.interact.battle", (new Function0<Vector3f>(optionsPacket) {
         {
            super(0);
            this.$optionsPacket = `$optionsPacket`;
         }

         @Nullable
         public final Vector3f invoke() {
            val `$this$any$iv`: java.lang.Iterable = CobblemonClient.INSTANCE.getRequests().getBattleChallenges();
            val var2: PlayerInteractOptionsPacket = this.$optionsPacket;
            var var10000: Boolean;
            if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
               var10000 = false;
            } else {
               val var4: java.util.Iterator = `$this$any$iv`.iterator();

               while (true) {
                  if (!var4.hasNext()) {
                     var10000 = false;
                     break;
                  }

                  if ((var4.next() as ClientBattleChallenge).getChallengerId() == var2.getTargetId()) {
                     var10000 = true;
                     break;
                  }
               }
            }

            return if (var10000) new Vector3f(0.0F, 0.6F, 0.0F) else null;
         }
      }) as () -> Vector3f, (new Function0<Unit>(optionsPacket) {
         {
            super(0);
            this.$optionsPacket = `$optionsPacket`;
         }

         public final void invoke() {
            val var2: java.lang.Iterable = CobblemonClient.INSTANCE.getRequests().getBattleChallenges();
            val var3: PlayerInteractOptionsPacket = this.$optionsPacket;
            val var5: java.util.Iterator = var2.iterator();

            var var10000: Any;
            while (true) {
               if (var5.hasNext()) {
                  val var6: Any = var5.next();
                  if (!((var6 as ClientBattleChallenge).getChallengerId() == var3.getTargetId())) {
                     continue;
                  }

                  var10000 = var6;
                  break;
               }

               var10000 = null;
               break;
            }

            val battleRequest: ClientBattleChallenge = var10000 as ClientBattleChallenge;
            new BattleChallengePacket(this.$optionsPacket.getNumericTargetId(), this.$optionsPacket.getSelectedPokemonId()).sendToServer();
            InteractWheelGuiFactoryKt.access$closeGUI();
         }
      }) as () -> Unit
   );
   val var16: InteractWheelOption = new InteractWheelOption(
      MiscUtilsKt.cobblemonResource("textures/gui/interact/icon_spectate_battle.png"),
      "cobblemon.ui.interact.spectate",
      (new Function0<Vector3f>(optionsPacket) {
         {
            super(0);
            this.$optionsPacket = `$optionsPacket`;
         }

         @Nullable
         public final Vector3f invoke() {
            val `$this$any$iv`: java.lang.Iterable = CobblemonClient.INSTANCE.getRequests().getBattleChallenges();
            val var2: PlayerInteractOptionsPacket = this.$optionsPacket;
            var var10000: Boolean;
            if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
               var10000 = false;
            } else {
               val var4: java.util.Iterator = `$this$any$iv`.iterator();

               while (true) {
                  if (!var4.hasNext()) {
                     var10000 = false;
                     break;
                  }

                  if ((var4.next() as ClientBattleChallenge).getChallengerId() == var2.getTargetId()) {
                     var10000 = true;
                     break;
                  }
               }
            }

            return if (var10000) new Vector3f(0.0F, 0.6F, 0.0F) else null;
         }
      }) as () -> Vector3f,
      (new Function0<Unit>(optionsPacket) {
         {
            super(0);
            this.$optionsPacket = `$optionsPacket`;
         }

         public final void invoke() {
            new SpectateBattlePacket(this.$optionsPacket.getTargetId()).sendToServer();
            InteractWheelGuiFactoryKt.access$closeGUI();
         }
      }) as () -> Unit
   );
   val var10000: ArrayListMultimap = ArrayListMultimap.create();
   val var17: Multimap = var10000 as Multimap;
   val `$this$map$iv`: java.lang.Iterable = optionsPacket.getOptions();
   val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

   for (Object item$iv$iv : $this$map$iv) {
      val it: PlayerInteractOptionsPacket.Options = `item$iv$iv` as PlayerInteractOptionsPacket.Options;
      if ((`item$iv$iv` as PlayerInteractOptionsPacket.Options).equals(PlayerInteractOptionsPacket.Options.TRADE)) {
         var17.put(Orientation.TOP_LEFT, trade);
      }

      if (it.equals(PlayerInteractOptionsPacket.Options.BATTLE)) {
         var17.put(Orientation.TOP_RIGHT, var15);
      }

      if (it.equals(PlayerInteractOptionsPacket.Options.SPECTATE_BATTLE)) {
         var17.put(Orientation.TOP_RIGHT, var16);
      }

      `destination$iv$iv`.add(Unit.INSTANCE);
   }

   val var10003: MutableComponent = Component.m_237115_("cobblemon.ui.interact.player");
   return new InteractWheelGUI(var17, var10003 as Component);
}

private fun closeGUI() {
   Minecraft.m_91087_().m_91152_(null);
}

@JvmSynthetic
fun `access$closeGUI`() {
   closeGUI();
}

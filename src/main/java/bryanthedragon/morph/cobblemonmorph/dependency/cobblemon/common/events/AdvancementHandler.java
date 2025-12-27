package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.BattleCountableContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountableCriterionKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountablePokemonTypeContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.EvolvePokemonContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.LevelUpContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PlantTumblestoneContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.TradePokemonContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleVictoryEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.LevelUpEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonCapturedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.TradeCompletedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerAdvancementData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.TumblestoneBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.TumblestoneItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent.RightClickBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import java.util.ArrayList;
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

@SourceDebugExtension(["SMAP\nAdvancementHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AdvancementHandler.kt\ncom/cobblemon/mod/common/events/AdvancementHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,159:1\n1855#2,2:160\n1360#2:162\n1446#2,2:163\n1603#2,9:165\n1855#2:174\n1856#2:176\n1612#2:177\n1448#2,3:178\n1855#2:181\n1855#2:182\n1855#2,2:183\n1856#2:185\n1856#2:186\n1360#2:187\n1446#2,2:188\n1603#2,9:190\n1855#2:199\n1856#2:201\n1612#2:202\n1448#2,3:203\n1855#2,2:206\n1#3:175\n1#3:200\n1#3:208\n*S KotlinDebug\n*F\n+ 1 AdvancementHandler.kt\ncom/cobblemon/mod/common/events/AdvancementHandler\n*L\n35#1:160,2\n83#1:162\n83#1:163,2\n83#1:165,9\n83#1:174\n83#1:176\n83#1:177\n83#1:178,3\n84#1:181\n87#1:182\n89#1:183,2\n87#1:185\n84#1:186\n100#1:187\n100#1:188,2\n100#1:190,9\n100#1:199\n100#1:201\n100#1:202\n100#1:203,3\n101#1:206,2\n83#1:175\n100#1:200\n*E\n"])
public object AdvancementHandler {
   public fun onCapture(event: PokemonCapturedEvent) {
      val playerData: PlayerData = Cobblemon.INSTANCE.getPlayerData().get(event.getPlayer() as Player);
      val advancementData: PlayerAdvancementData = playerData.getAdvancementData();
      advancementData.updateTotalCaptureCount();
      advancementData.updateAspectsCollected(event.getPlayer(), event.getPokemon());
      CobblemonCriteria.INSTANCE.getCATCH_POKEMON().trigger(event.getPlayer(), new CountablePokemonTypeContext(advancementData.getTotalCaptureCount(), "any"));

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val it: ElementalType = `element$iv` as ElementalType;
         advancementData.updateTotalTypeCaptureCount(`element$iv` as ElementalType);
         CobblemonCriteria.INSTANCE
            .getCATCH_POKEMON()
            .trigger(event.getPlayer(), new CountablePokemonTypeContext(advancementData.getTotalTypeCaptureCount(it), it.getName()));
      }

      if (event.getPokemon().getShiny()) {
         advancementData.updateTotalShinyCaptureCount();
         CountableCriterionKt.trigger(CobblemonCriteria.INSTANCE.getCATCH_SHINY_POKEMON(), event.getPlayer(), advancementData.getTotalShinyCaptureCount());
      }

      CobblemonCriteria.INSTANCE.getCOLLECT_ASPECT().trigger(event.getPlayer(), advancementData.getAspectsCollected());
      Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
   }

   public fun onEvolve(event: EvolutionCompleteEvent) {
      val player: ServerPlayer = event.getPokemon().getOwnerPlayer();
      if (player != null) {
         if (event.getPokemon().getPreEvolution() != null) {
            val playerData: PlayerData = Cobblemon.INSTANCE.getPlayerData().get(player as Player);
            val advancementData: PlayerAdvancementData = playerData.getAdvancementData();
            advancementData.updateTotalEvolvedCount();
            advancementData.updateAspectsCollected(player, event.getPokemon());
            Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
            val var10000: SimpleCriterionTrigger = CobblemonCriteria.INSTANCE.getEVOLVE_POKEMON();
            val var10004: PreEvolution = event.getPokemon().getPreEvolution();
            var10000.trigger(
               player,
               new EvolvePokemonContext(
                  var10004.getSpecies().getResourceIdentifier(),
                  event.getPokemon().getSpecies().getResourceIdentifier(),
                  advancementData.getTotalEvolvedCount()
               )
            );
            CobblemonCriteria.INSTANCE.getCOLLECT_ASPECT().trigger(player, advancementData.getAspectsCollected());
         } else {
            Cobblemon.INSTANCE
               .getLOGGER()
               .warn(
                  "Evolution triggered by ${player.m_5446_()} has missing evolution data for ${event.getPokemon().getSpecies().getResourceIdentifier()}. Incomplete evolution data: ${event.getEvolution()
                     .getId()}, please report to the datapack creator!"
               );
         }
      }
   }

   public fun onWinBattle(event: BattleVictoryEvent) {
      if (!event.getWasWildCapture() && event.getBattle().isPvW()) {
         val `$this$forEach$iv`: java.lang.Iterable = event.getWinners();
         val `element$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$forEach$iv) {
            val `$this$mapNotNull$iv`: java.lang.Iterable = (playerData as BattleActor).getPlayerUUIDs();
            val `destination$iv$ivx`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
               val var10000: ServerPlayer = PlayerExtensionsKt.getPlayer(`element$iv$iv$iv` as UUID);
               if (var10000 != null) {
                  `destination$iv$ivx`.add(var10000);
               }
            }

            CollectionsKt.addAll(`element$iv`, `destination$iv$ivx` as java.util.List);
         }

         for (Object element$ivx : $this$forEach$iv) {
            val var36: ServerPlayer = `element$ivx` as ServerPlayer;
            val var42: PlayerData = Cobblemon.INSTANCE.getPlayerData().get((`element$ivx` as ServerPlayer) as Player);
            val var46: PlayerAdvancementData = var42.getAdvancementData();

            val var50: java.lang.Iterable;
            for (Object element$ivxx : var50) {
               val var56: BattleActor = `element$ivxx` as BattleActor;
               if (!event.getWinners().contains(`element$ivxx` as BattleActor) && (`element$ivxx` as BattleActor).getType() === ActorType.WILD) {
                  val `$this$forEach$iv$iv$iv`: java.lang.Iterable;
                  for (Object element$ivxxx : $this$forEach$iv$iv$iv) {
                     var46.updateTotalDefeatedCount((`element$ivxxx` as BattlePokemon).getOriginalPokemon());
                  }
               }
            }

            Cobblemon.INSTANCE.getPlayerData().saveSingle(var42);
            CountableCriterionKt.trigger(CobblemonCriteria.INSTANCE.getDEFEAT_POKEMON(), var36, var46.getTotalBattleVictoryCount());
         }
      }

      val var27: java.lang.Iterable = event.getWinners();
      val var34: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : var27) {
         val var53: java.lang.Iterable = (var43 as BattleActor).getPlayerUUIDs();
         val `destination$iv$ivx`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv$ivx : $this$mapNotNull$iv) {
            val var72: ServerPlayer = PlayerExtensionsKt.getPlayer(`element$iv$iv$ivx` as UUID);
            if (var72 != null) {
               `destination$iv$ivx`.add(var72);
            }
         }

         CollectionsKt.addAll(var34, `destination$iv$ivx` as java.util.List);
      }

      for (Object element$iv : var27) {
         val var38: ServerPlayer = var35 as ServerPlayer;
         val var44: PlayerData = Cobblemon.INSTANCE.getPlayerData().get((var35 as ServerPlayer) as Player);
         val var49: PlayerAdvancementData = var44.getAdvancementData();
         var49.updateTotalBattleVictoryCount();
         if (event.getBattle().isPvW()) {
            var49.updateTotalPvWBattleVictoryCount();
         }

         if (event.getBattle().isPvP()) {
            var49.updateTotalPvPBattleVictoryCount();
         }

         if (event.getBattle().isPvN()) {
            var49.updateTotalPvNBattleVictoryCount();
         }

         Cobblemon.INSTANCE.getPlayerData().saveSingle(var44);
         CobblemonCriteria.INSTANCE.getWIN_BATTLE().trigger(var38, new BattleCountableContext(var49.getTotalBattleVictoryCount(), event.getBattle()));
      }
   }

   public fun onLevelUp(event: LevelUpEvent) {
      val var10000: ServerPlayer = event.getPokemon().getOwnerPlayer();
      if (var10000 != null) {
         CobblemonCriteria.INSTANCE.getLEVEL_UP().trigger(var10000, new LevelUpContext(event.getNewLevel(), event.getPokemon()));
      }
   }

   public fun onTradeCompleted(event: TradeCompletedEvent) {
      val player1: ServerPlayer = event.getTradeParticipant1Pokemon().getOwnerPlayer();
      val player2: ServerPlayer = event.getTradeParticipant2Pokemon().getOwnerPlayer();
      if (player1 != null) {
         CobblemonCriteria.INSTANCE
            .getTRADE_POKEMON()
            .trigger(player1, new TradePokemonContext(event.getTradeParticipant1Pokemon(), event.getTradeParticipant2Pokemon()));
         val playerData: PlayerData = Cobblemon.INSTANCE.getPlayerData().get(player1 as Player);
         val advancementData: PlayerAdvancementData = playerData.getAdvancementData();
         advancementData.updateTotalTradedCount();
         advancementData.updateAspectsCollected(player1, event.getTradeParticipant2Pokemon());
         CobblemonCriteria.INSTANCE.getCOLLECT_ASPECT().trigger(player1, advancementData.getAspectsCollected());
         Cobblemon.INSTANCE.getPlayerData().saveSingle(playerData);
      }

      if (player2 != null) {
         CobblemonCriteria.INSTANCE
            .getTRADE_POKEMON()
            .trigger(player2, new TradePokemonContext(event.getTradeParticipant2Pokemon(), event.getTradeParticipant1Pokemon()));
         val var6: PlayerData = Cobblemon.INSTANCE.getPlayerData().get(player2 as Player);
         val var7: PlayerAdvancementData = var6.getAdvancementData();
         var7.updateTotalTradedCount();
         var7.updateAspectsCollected(player2, event.getTradeParticipant1Pokemon());
         CobblemonCriteria.INSTANCE.getCOLLECT_ASPECT().trigger(player2, var7.getAspectsCollected());
         Cobblemon.INSTANCE.getPlayerData().saveSingle(var6);
      }
   }

   public fun onTumbleStonePlaced(event: RightClickBlock) {
      if (event.getPlayer().m_21120_(event.getHand()).m_41720_() == CobblemonItems.TUMBLESTONE.m_5456_()) {
         val var10000: Item = event.getPlayer().m_21120_(event.getHand()).m_41720_();
         val var3: Block = (var10000 as TumblestoneItem).getBlock();
         CobblemonCriteria.INSTANCE.getPLANT_TUMBLESTONE().trigger(event.getPlayer(), new PlantTumblestoneContext(event.getPos(), var3 as TumblestoneBlock));
      }
   }
}

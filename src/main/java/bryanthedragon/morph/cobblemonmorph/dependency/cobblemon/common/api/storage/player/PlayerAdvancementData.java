package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.AspectCriterionCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.LinkedHashSet

import net.minecraft.advancements.Advancement
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public class PlayerAdvancementData {
   public final var aspectsCollected: MutableMap<ResourceLocation, MutableSet<String>> = (new LinkedHashMap()) as java.util.Map
      private set

   public final var totalBattleVictoryCount: Int
      private set

   public final var totalCaptureCount: Int
      private set

   private final var totalDefeatedCounts: MutableMap<ResourceLocation, Int> = (new LinkedHashMap()) as java.util.Map

   public final var totalEggsHatched: Int
      private set

   public final var totalEvolvedCount: Int
      private set

   public final var totalPvNBattleVictoryCount: Int
      private set

   public final var totalPvPBattleVictoryCount: Int
      private set

   public final var totalPvWBattleVictoryCount: Int
      private set

   public final var totalShinyCaptureCount: Int
      private set

   public final var totalTradedCount: Int
      private set

   private final var totalTypeCaptureCounts: MutableMap<String, Int> = (new LinkedHashMap()) as java.util.Map

   public fun updateTotalCaptureCount() {
      val var1: Int = this.totalCaptureCount++;
   }

   public fun updateTotalEggsHatched() {
      val var1: Int = this.totalEggsHatched++;
   }

   public fun updateTotalEvolvedCount() {
      val var1: Int = this.totalEvolvedCount++;
   }

   public fun updateTotalBattleVictoryCount() {
      val var1: Int = this.totalBattleVictoryCount++;
   }

   public fun updateTotalPvPBattleVictoryCount() {
      val var1: Int = this.totalPvPBattleVictoryCount++;
   }

   public fun updateTotalPvWBattleVictoryCount() {
      val var1: Int = this.totalPvWBattleVictoryCount++;
   }

   public fun updateTotalPvNBattleVictoryCount() {
      val var1: Int = this.totalPvNBattleVictoryCount++;
   }

   public fun updateTotalShinyCaptureCount() {
      val var1: Int = this.totalShinyCaptureCount++;
   }

   public fun updateTotalTradedCount() {
      val var1: Int = this.totalTradedCount++;
   }

   public fun getTotalTypeCaptureCount(type: ElementalType): Int {
      if (!this.totalTypeCaptureCounts.containsKey(type.getName())) {
         this.totalTypeCaptureCounts.put(type.getName(), 0);
      }

      val var10000: Int = this.totalTypeCaptureCounts.get(type.getName());
      return var10000 ?: 0;
   }

   public fun updateTotalTypeCaptureCount(type: ElementalType) {
      val var10000: Int = this.totalTypeCaptureCounts.get(type.getName());
      val count: Int = (int)(var10000 ?: 0);
      if (count == 0) {
         this.totalTypeCaptureCounts.put(type.getName(), 1);
      } else {
         this.totalTypeCaptureCounts.replace(type.getName(), count + 1);
      }
   }

   public fun updateTotalDefeatedCount(pokemon: Pokemon) {
      val var10000: Int = this.totalDefeatedCounts.get(pokemon.getSpecies().getResourceIdentifier());
      val count: Int = (int)(var10000 ?: 0);
      if (count == 0) {
         this.totalDefeatedCounts.put(pokemon.getSpecies().getResourceIdentifier(), 1);
      } else {
         this.totalDefeatedCounts.replace(pokemon.getSpecies().getResourceIdentifier(), count + 1);
      }
   }

   public fun updateAspectsCollected(player: ServerPlayer, pokemon: Pokemon) {
      var trackedAspects: java.lang.Iterable = player.m_8960_().f_263740_.keySet();
      var `$i$f$forEach`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : trackedAspects) {
         CollectionsKt.addAll(`$i$f$forEach`, (p0 as Advancement).m_138325_().values());
      }

      trackedAspects = `$i$f$forEach` as java.util.List;
      `$i$f$forEach` = new ArrayList();

      for (Object element$iv$iv$iv : trackedAspects) {
         val var10000: CriterionTriggerInstance = (var62 as Criterion).m_11416_();
         if (var10000 != null) {
            `$i$f$forEach`.add(var10000);
         }
      }

      trackedAspects = `$i$f$forEach` as java.util.List;
      `$i$f$forEach` = new ArrayList();

      for (Object element$iv$iv : trackedAspects) {
         if (var49 is AspectCriterionCondition) {
            `$i$f$forEach`.add(var49);
         }
      }

      var var24: java.lang.Iterable = `$i$f$forEach` as java.util.List;
      var `destination$iv$ivx`: java.util.Collection = new ArrayList();

      for (Object element$iv$ivx : var24) {
         if ((`element$iv$ivx` as AspectCriterionCondition).getPokemon() == pokemon.getSpecies().getResourceIdentifier()) {
            `destination$iv$ivx`.add(`element$iv$ivx`);
         }
      }

      var24 = `destination$iv$ivx` as java.util.List;
      `destination$iv$ivx` = new ArrayList();

      for (Object element$iv$ivxx : var24) {
         CollectionsKt.addAll(`destination$iv$ivx`, (`element$iv$ivxx` as AspectCriterionCondition).getAspects());
      }

      val var21: java.util.List = `destination$iv$ivx` as java.util.List;
      if (!(`destination$iv$ivx` as java.util.List).isEmpty()) {
         val var28: java.util.Map = this.aspectsCollected;
         `$i$f$forEach` = pokemon.getSpecies().getResourceIdentifier();
         var var45: Any = var28.get(`$i$f$forEach`);
         val var70: Any;
         if (var45 == null) {
            val var53: Any = new LinkedHashSet();
            var28.put(`$i$f$forEach`, var53);
            var70 = var53;
         } else {
            var70 = var45;
         }

         val var26: java.util.Set = var70 as java.util.Set;
         val var29: java.lang.Iterable = pokemon.getAspects();
         var45 = new ArrayList();

         for (Object element$iv$ivxx : $this$filter$iv) {
            if (var21.contains(`element$iv$ivxx` as java.lang.String)) {
               var45.add(`element$iv$ivxx`);
            }
         }

         for (Object element$iv : var30) {
            var26.add(var47 as java.lang.String);
         }
      }
   }
}

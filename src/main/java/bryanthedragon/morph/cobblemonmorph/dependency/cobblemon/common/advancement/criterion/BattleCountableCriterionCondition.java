package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerAdvancementData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.JsonExtensionsKt;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player

public class BattleCountableCriterionCondition(id: ResourceLocation, predicate: ContextAwarePredicate) : CountableCriterionCondition(id, predicate) {
   private final var battleTypes: MutableList<String> = CollectionsKt.mutableListOf(new java.lang.String[]{"any"})

   public override fun fromJson(json: JsonObject) {
      super.fromJson(json);
      if (!json.get("battle_types").isJsonNull()) {
         this.battleTypes.clear();

         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            val it: JsonElement = `element$iv` as JsonElement;
            val var8: java.util.List = this.battleTypes;
            val var10001: java.lang.String = it.getAsString();
            var8.add(var10001);
         }
      }
   }

   public override fun toJson(json: JsonObject) {
      super.toJson(json);
      json.add("battle_types", JsonExtensionsKt.toJsonArrayString(this.battleTypes) as JsonElement);
   }

   public open fun matches(player: ServerPlayer, context: BattleCountableContext): Boolean {
      var typeCheck: Boolean = false;
      val advancementData: PlayerAdvancementData = Cobblemon.INSTANCE.getPlayerData().get(player as Player).getAdvancementData();
      if (this.battleTypes.isEmpty() || this.battleTypes.contains("any")) {
         typeCheck = true;
      }

      if (this.battleTypes.contains("pvp")) {
         typeCheck = context.getBattle().isPvP();
         context.setTimes(advancementData.getTotalPvPBattleVictoryCount());
      }

      if (this.battleTypes.contains("pvw")) {
         typeCheck = context.getBattle().isPvW();
         context.setTimes(advancementData.getTotalPvWBattleVictoryCount());
      }

      if (this.battleTypes.contains("pvn")) {
         typeCheck = context.getBattle().isPvN();
         context.setTimes(advancementData.getTotalPvWBattleVictoryCount());
      }

      if (this.battleTypes.size() > 1) {
         context.setTimes(advancementData.getTotalBattleVictoryCount());
      }

      return context.getTimes() >= this.getCount() && typeCheck;
   }
}

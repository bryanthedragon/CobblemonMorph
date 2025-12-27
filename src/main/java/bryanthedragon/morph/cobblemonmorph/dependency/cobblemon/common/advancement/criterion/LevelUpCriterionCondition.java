package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public class LevelUpCriterionCondition(id: ResourceLocation, entity: ContextAwarePredicate) : SimpleCriterionCondition(id, entity) {
   public final var evolved: Boolean = true
   public final var level: Int

   public override fun toJson(json: JsonObject) {
      json.addProperty("level", this.level);
      json.addProperty("has_evolved", this.evolved);
   }

   public override fun fromJson(json: JsonObject) {
      var var10001: JsonElement = json.get("level");
      this.level = if (var10001 != null) var10001.getAsInt() else 0;
      var10001 = json.get("has_evolved");
      this.evolved = var10001 == null || var10001.getAsBoolean();
   }

   public open fun matches(player: ServerPlayer, context: LevelUpContext): Boolean {
      val preEvo: Boolean = context.getPokemon().getPreEvolution() == null;
      val hasEvolution: Boolean = !CollectionsKt.none(context.getPokemon().getEvolutions());
      var evolutionCheck: Boolean = true;
      if (preEvo || hasEvolution) {
         evolutionCheck = preEvo != hasEvolution;
      }

      return this.level == context.getLevel() && evolutionCheck == this.evolved;
   }
}

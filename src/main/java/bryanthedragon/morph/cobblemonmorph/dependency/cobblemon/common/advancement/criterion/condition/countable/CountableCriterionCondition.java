package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.condition.countable.simple;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.context.countable.CountableContext;
import net.minecraft.server.level.ServerPlayer;

import javax.json.JsonObject;

public abstract class CountableCriterionCondition<T : CountableContext>(Identifier id, LootContextPredicate predicate) : SimpleCriterionCondition<T>(id, predicate) {
    var count = 0;
    fun fromJson(JsonObject json) {
        count = json.get("count")?.asInt ?: 0;
    }

    fun toJson(JsonObject json) {
        json.addProperty("count", count);
    }

    fun matches(ServerPlayer player,T context) = context.times >= count;
    }
}

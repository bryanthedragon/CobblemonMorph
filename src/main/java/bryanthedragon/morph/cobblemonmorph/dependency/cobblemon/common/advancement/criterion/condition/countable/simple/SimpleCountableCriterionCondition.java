package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.condition.countable;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.context.countable.CountableContext;

import net.minecraft.server.level.ServerPlayer;

import org.checkerframework.checker.signature.qual.Identifier;

/**
* A concrete subclass of [CountableCriterionCondition] so that you can have advancements that really just need the
* count and nothing else.
*
* This is just a quirk of using generics like this, don't worry about it. The criterion conditions that get used must
* not be generic typed.
*
* @author Hiroku
* @since November 4th, 2022
*/
public class SimpleCountableCriterionCondition(Identifier id, LootContextPredicate predicate) : CountableCriterionCondition<CountableContext>(id, predicate) {
    fun SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition>.trigger(ServerPlayer player, int times) = trigger(player, CountableContext(times));
}

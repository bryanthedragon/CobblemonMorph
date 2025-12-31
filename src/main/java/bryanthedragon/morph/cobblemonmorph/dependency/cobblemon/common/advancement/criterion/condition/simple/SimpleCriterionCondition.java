package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.condition.simple;

import com.mojang.serialization.Codec;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.*;


public abstract class SimpleCriterionCondition<T>(Optional<ContextAwarePredicate> playerCtx) : SimpleCriterionTrigger.SimpleInstance {
    override fun player() = playerCtx

    abstract boolean matches(ServerPlayer player, T context);
}

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.trigger;

import org.checkerframework.checker.units.qual.C;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.condition.simple.;

import com.mojang.serialization.Codec;

import net.minecraft.server.level.ServerPlayer;

public class SimpleCriterionTrigger<T, C extends SimpleCriterionCondition<T>>(Codec<C> codec) extends SimpleCriterionTrigger<C>() {
    override fun codec() = codec

    fun trigger(ServerPlayer player, T context) {
        return this.trigger(player) {
            it.matches(player, context);
        }
    }
}
}

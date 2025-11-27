/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import kotlin.jvm.internal.Intrinsics;

import net.minecraft.server.level.ServerPlayer;

import org.jetbrains.annotations.NotNull;

public final class CountableCriterionKt {
    public static final void trigger(@NotNull SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> $this$trigger, @NotNull ServerPlayer player, int times2) {
        Intrinsics.checkNotNullParameter($this$trigger, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        $this$trigger.trigger(player, new CountableContext(times2));
    }
}


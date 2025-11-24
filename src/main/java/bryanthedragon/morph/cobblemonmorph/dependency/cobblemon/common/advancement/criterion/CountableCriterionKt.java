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

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a-\u0010\b\u001a\u00020\u0007*\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionTrigger;", "Lcom/cobblemon/mod/common/advancement/criterion/CountableContext;", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCountableCriterionCondition;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "times", "", "trigger", "(Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionTrigger;Lnet/minecraft/server/level/ServerPlayer;I)V", "common"})
public final class CountableCriterionKt {
    public static final void trigger(@NotNull SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> $this$trigger, @NotNull ServerPlayer player, int times2) {
        Intrinsics.checkNotNullParameter($this$trigger, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        $this$trigger.trigger(player, new CountableContext(times2));
    }
}


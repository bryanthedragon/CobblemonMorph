/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.BattleCountableContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.BattleCountableCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/BattleCountableCriterionTrigger;", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionTrigger;", "Lcom/cobblemon/mod/common/advancement/criterion/BattleCountableContext;", "Lcom/cobblemon/mod/common/advancement/criterion/BattleCountableCriterionCondition;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Ljava/lang/Class;", "criterionClass", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/Class;)V", "common"})
public class BattleCountableCriterionTrigger
extends SimpleCriterionTrigger<BattleCountableContext, BattleCountableCriterionCondition> {
    public BattleCountableCriterionTrigger(@NotNull ResourceLocation identifier, @NotNull Class<BattleCountableCriterionCondition> criterionClass) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter(criterionClass, (String)"criterionClass");
        super(identifier, criterionClass);
    }
}


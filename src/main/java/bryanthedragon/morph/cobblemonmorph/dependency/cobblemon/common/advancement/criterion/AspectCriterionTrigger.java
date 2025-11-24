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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.AspectCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger;

import java.util.Map;
import java.util.Set;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002 \u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0002\u0012\u0004\u0012\u00020\u00060\u0001B\u001d\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/AspectCriterionTrigger;", "Lcom/cobblemon/mod/common/advancement/criterion/SimpleCriterionTrigger;", "", "Lnet/minecraft/resources/ResourceLocation;", "", "", "Lcom/cobblemon/mod/common/advancement/criterion/AspectCriterionCondition;", "identifier", "Ljava/lang/Class;", "criterionClass", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/Class;)V", "common"})
public class AspectCriterionTrigger
extends SimpleCriterionTrigger<Map<ResourceLocation, Set<String>>, AspectCriterionCondition> {
    public AspectCriterionTrigger(@NotNull ResourceLocation identifier, @NotNull Class<AspectCriterionCondition> criterionClass) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter(criterionClass, (String)"criterionClass");
        super(identifier, criterionClass);
    }
}


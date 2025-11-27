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

import java.util.Map;
import java.util.Set;

import kotlin.jvm.internal.Intrinsics;

import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

public class AspectCriterionTrigger extends SimpleCriterionTrigger<Map<ResourceLocation, Set<String>>, AspectCriterionCondition> {
    public AspectCriterionTrigger(@NotNull ResourceLocation identifier, @NotNull Class<AspectCriterionCondition> criterionClass) {
        super(identifier, criterionClass);
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter(criterionClass, (String)"criterionClass");
    }
}


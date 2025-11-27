/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;

import java.util.Set;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

public class CommonAbility
implements PotentialAbility {
    @NotNull
    private final AbilityTemplate template;
    @NotNull
    private final Priority priority;
    @NotNull
    private final CommonAbilityType type;

    public CommonAbility(@NotNull AbilityTemplate template) {
        Intrinsics.checkNotNullParameter((Object)template, (String)"template");
        this.template = template;
        this.priority = Priority.LOWEST;
        this.type = CommonAbilityType.INSTANCE;
    }

    @Override
    @NotNull
    public AbilityTemplate getTemplate() {
        return this.template;
    }

    @Override
    @NotNull
    public Priority getPriority() {
        return this.priority;
    }

    @NotNull
    public CommonAbilityType getType() {
        return this.type;
    }

    @Override
    public boolean isSatisfiedBy(@NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        return true;
    }
}


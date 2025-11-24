/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.abilities.HiddenAbilityType;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001d\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\t\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/pokemon/abilities/HiddenAbility;", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbility;", "", "", "aspects", "", "isSatisfiedBy", "(Ljava/util/Set;)Z", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "Lcom/cobblemon/mod/common/api/Priority;", "getPriority", "()Lcom/cobblemon/mod/common/api/Priority;", "Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "template", "Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "getTemplate", "()Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "Lcom/cobblemon/mod/common/pokemon/abilities/HiddenAbilityType;", "type", "Lcom/cobblemon/mod/common/pokemon/abilities/HiddenAbilityType;", "getType", "()Lcom/cobblemon/mod/common/pokemon/abilities/HiddenAbilityType;", "<init>", "(Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;)V", "common"})
public final class HiddenAbility
implements PotentialAbility {
    @NotNull
    private final AbilityTemplate template;
    @NotNull
    private final Priority priority;
    @NotNull
    private final HiddenAbilityType type;

    public HiddenAbility(@NotNull AbilityTemplate template) {
        Intrinsics.checkNotNullParameter((Object)template, (String)"template");
        this.template = template;
        this.priority = Priority.LOW;
        this.type = HiddenAbilityType.INSTANCE;
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
    public HiddenAbilityType getType() {
        return this.type;
    }

    @Override
    public boolean isSatisfiedBy(@NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        return false;
    }
}


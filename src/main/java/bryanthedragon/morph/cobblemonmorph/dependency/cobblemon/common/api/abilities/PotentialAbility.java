/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbilityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014J\u001d\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H&\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0014\u0010\u000b\u001a\u00020\b8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000f\u001a\u00020\f8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00108&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/abilities/PotentialAbility;", "", "", "", "aspects", "", "isSatisfiedBy", "(Ljava/util/Set;)Z", "Lcom/cobblemon/mod/common/api/Priority;", "getPriority", "()Lcom/cobblemon/mod/common/api/Priority;", "priority", "Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "getTemplate", "()Lcom/cobblemon/mod/common/api/abilities/AbilityTemplate;", "template", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "getType", "()Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "type", "Companion", "common"})
public interface PotentialAbility {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility$Companion.$$INSTANCE;

    @NotNull
    public AbilityTemplate getTemplate();

    @NotNull
    public Priority getPriority();

    @NotNull
    public PotentialAbilityType<?> getType();

    public boolean isSatisfiedBy(@NotNull Set<String> var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR!\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/abilities/PotentialAbility$Companion;", "", "", "Lcom/cobblemon/mod/common/api/abilities/PotentialAbilityType;", "types", "Ljava/util/List;", "getTypes", "()Ljava/util/List;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final List<PotentialAbilityType<?>> types;

        private Companion() {
        }

        @NotNull
        public final List<PotentialAbilityType<?>> getTypes() {
            return types;
        }

        static {
            $$INSTANCE = new Companion();
            types = new ArrayList();
        }
    }
}


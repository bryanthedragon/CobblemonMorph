/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import org.jetbrains.annotations.NotNull;

public interface PotentialAbility {

    @NotNull
    @SuppressWarnings("static-access")    
    public static final Companion Companion = PotentialAbility.Companion.INSTANCE;

    @NotNull
    public AbilityTemplate getTemplate();

    @NotNull
    public Priority getPriority();

    @NotNull
    public PotentialAbilityType<?> getType();

    public boolean isSatisfiedBy(@NotNull Set<String> var1);

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final class Companion {
        static final /* synthetic */ Companion INSTANCE;
        @NotNull
        private static final List<PotentialAbilityType<?>> types;

        private Companion() {
        }

        @NotNull
        public final List<PotentialAbilityType<?>> getTypes() {
            return types;
        }

        static {
            INSTANCE = new Companion();
            types = new ArrayList();
        }
    }
}


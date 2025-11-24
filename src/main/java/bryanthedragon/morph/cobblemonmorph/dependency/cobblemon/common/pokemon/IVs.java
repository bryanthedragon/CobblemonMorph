/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.ranges.IntRange
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.PokemonStats;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0007\u00a2\u0006\u0004\b\f\u0010\rR\u001a\u0010\u0003\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/pokemon/IVs;", "Lcom/cobblemon/mod/common/pokemon/PokemonStats;", "Lkotlin/ranges/IntRange;", "acceptableRange", "Lkotlin/ranges/IntRange;", "getAcceptableRange", "()Lkotlin/ranges/IntRange;", "", "defaultValue", "I", "getDefaultValue", "()I", "<init>", "()V", "Companion", "common"})
public final class IVs
extends PokemonStats {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final IntRange acceptableRange = new IntRange(0, 31);
    private final int defaultValue;
    public static final int MAX_VALUE = 31;

    @Override
    @NotNull
    public IntRange getAcceptableRange() {
        return this.acceptableRange;
    }

    @Override
    public int getDefaultValue() {
        return this.defaultValue;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/pokemon/IVs$Companion;", "", "", "minPerfectIVs", "Lcom/cobblemon/mod/common/pokemon/IVs;", "createRandomIVs", "(I)Lcom/cobblemon/mod/common/pokemon/IVs;", "MAX_VALUE", "I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final IVs createRandomIVs(int minPerfectIVs) {
            return Cobblemon.INSTANCE.getStatProvider().createEmptyIVs(minPerfectIVs);
        }

        public static /* synthetic */ IVs createRandomIVs$default(Companion companion, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                n = 0;
            }
            return companion.createRandomIVs(n);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}


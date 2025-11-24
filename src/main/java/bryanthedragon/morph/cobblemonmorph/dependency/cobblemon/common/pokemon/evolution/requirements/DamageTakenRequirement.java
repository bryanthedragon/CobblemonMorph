/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DamageTakenEvolutionProgress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\t\b\u0016\u00a2\u0006\u0004\b\f\u0010\rB\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\u000eJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/DamageTakenRequirement;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "check", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "amount", "I", "getAmount", "()I", "<init>", "()V", "(I)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nDamageTakenRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DamageTakenRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/DamageTakenRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,42:1\n800#2,11:43\n1747#2,3:54\n*S KotlinDebug\n*F\n+ 1 DamageTakenRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/DamageTakenRequirement\n*L\n35#1:43,11\n36#1:54,3\n*E\n"})
public final class DamageTakenRequirement
implements EvolutionRequirement {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int amount;
    @NotNull
    public static final String ADAPTER_VARIANT = "damage_taken";

    public DamageTakenRequirement(int amount) {
        this.amount = amount;
    }

    public DamageTakenRequirement() {
        this(0);
    }

    public final int getAmount() {
        return this.amount;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean check(@NotNull Pokemon pokemon) {
        boolean bl;
        block4: {
            void $this$filterIsInstanceTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Iterable $this$filterIsInstance$iv = pokemon.getEvolutionProxy().current().progress();
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof DamageTakenEvolutionProgress)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$any$iv = (List)destination$iv$iv;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    DamageTakenEvolutionProgress progress2 = (DamageTakenEvolutionProgress)element$iv;
                    boolean bl2 = false;
                    if (!(progress2.currentProgress().getAmount() >= this.amount)) continue;
                    bl = true;
                    break block4;
                }
                bl = false;
            }
        }
        return bl;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/DamageTakenRequirement$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}


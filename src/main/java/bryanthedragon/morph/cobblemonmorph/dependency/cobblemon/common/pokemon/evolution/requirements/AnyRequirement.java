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
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\b\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0015\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001d\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/AnyRequirement;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "check", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "possibilities", "Ljava/util/Collection;", "getPossibilities", "()Ljava/util/Collection;", "<init>", "(Ljava/util/Collection;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nAnyRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AnyRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/AnyRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,24:1\n1747#2,3:25\n*S KotlinDebug\n*F\n+ 1 AnyRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/AnyRequirement\n*L\n20#1:25,3\n*E\n"})
public final class AnyRequirement
implements EvolutionRequirement {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Collection<EvolutionRequirement> possibilities;
    @NotNull
    public static final String ADAPTER_VARIANT = "any";

    public AnyRequirement(@NotNull Collection<? extends EvolutionRequirement> possibilities) {
        Intrinsics.checkNotNullParameter(possibilities, (String)"possibilities");
        this.possibilities = possibilities;
    }

    @NotNull
    public final Collection<EvolutionRequirement> getPossibilities() {
        return this.possibilities;
    }

    @Override
    public boolean check(@NotNull Pokemon pokemon) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Iterable $this$any$iv = this.possibilities;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    EvolutionRequirement it = (EvolutionRequirement)element$iv;
                    boolean bl2 = false;
                    if (!it.check(pokemon)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/AnyRequirement$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}


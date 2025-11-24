/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/MoveSetRequirement;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "check", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "move", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getMove", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nMoveSetRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveSetRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/MoveSetRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,29:1\n1747#2,3:30\n*S KotlinDebug\n*F\n+ 1 MoveSetRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/MoveSetRequirement\n*L\n25#1:30,3\n*E\n"})
public final class MoveSetRequirement
implements EvolutionRequirement {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final MoveTemplate move = Moves.INSTANCE.getByNameOrDummy("tackle");
    @NotNull
    public static final String ADAPTER_VARIANT = "has_move";

    @NotNull
    public final MoveTemplate getMove() {
        return this.move;
    }

    @Override
    public boolean check(@NotNull Pokemon pokemon) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Iterable $this$any$iv = pokemon.getMoveSet().getMoves();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    Move move = (Move)element$iv;
                    boolean bl2 = false;
                    if (!StringsKt.equals((String)move.getName(), (String)this.move.getName(), (boolean)true)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/requirements/MoveSetRequirement$Companion;", "", "", "ADAPTER_VARIANT", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}


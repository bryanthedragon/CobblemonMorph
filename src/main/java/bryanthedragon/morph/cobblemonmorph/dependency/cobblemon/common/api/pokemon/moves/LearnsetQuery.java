/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00e6\u0080\u0001\u0018\u0000 \t2\u00020\u0001:\u0001\tJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/moves/LearnsetQuery;", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "move", "Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;", "learnset", "", "canLearn", "(Lcom/cobblemon/mod/common/api/moves/MoveTemplate;Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;)Z", "Companion", "common"})
public interface LearnsetQuery {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.LearnsetQuery$Companion.$$INSTANCE;

    public boolean canLearn(@NotNull MoveTemplate var1, @NotNull Learnset var2);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0005R\u0017\u0010\u0006\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0007\u001a\u0004\b\u000b\u0010\tR\u0017\u0010\f\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u0007\u001a\u0004\b\r\u0010\tR\u0017\u0010\u000e\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u0007\u001a\u0004\b\u000f\u0010\tR\u0017\u0010\u0010\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0007\u001a\u0004\b\u0011\u0010\tR\u0017\u0010\u0012\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0007\u001a\u0004\b\u0013\u0010\tR\u0017\u0010\u0014\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0007\u001a\u0004\b\u0015\u0010\t\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/moves/LearnsetQuery$Companion;", "", "", "level", "Lcom/cobblemon/mod/common/api/pokemon/moves/LearnsetQuery;", "(I)Lcom/cobblemon/mod/common/api/pokemon/moves/LearnsetQuery;", "ANY", "Lcom/cobblemon/mod/common/api/pokemon/moves/LearnsetQuery;", "getANY", "()Lcom/cobblemon/mod/common/api/pokemon/moves/LearnsetQuery;", "ANY_LEVEL", "getANY_LEVEL", "EGG_MOVE", "getEGG_MOVE", "EVOLUTION", "getEVOLUTION", "FORM_CHANGE", "getFORM_CHANGE", "TM_MOVE", "getTM_MOVE", "TUTOR_MOVES", "getTUTOR_MOVES", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nLearnsetQuery.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LearnsetQuery.kt\ncom/cobblemon/mod/common/api/pokemon/moves/LearnsetQuery$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,51:1\n1747#2,3:52\n1747#2,3:55\n*S KotlinDebug\n*F\n+ 1 LearnsetQuery.kt\ncom/cobblemon/mod/common/api/pokemon/moves/LearnsetQuery$Companion\n*L\n27#1:52,3\n37#1:55,3\n*E\n"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final LearnsetQuery ANY;
        @NotNull
        private static final LearnsetQuery ANY_LEVEL;
        @NotNull
        private static final LearnsetQuery EGG_MOVE;
        @NotNull
        private static final LearnsetQuery TUTOR_MOVES;
        @NotNull
        private static final LearnsetQuery TM_MOVE;
        @NotNull
        private static final LearnsetQuery FORM_CHANGE;
        @NotNull
        private static final LearnsetQuery EVOLUTION;

        private Companion() {
        }

        @NotNull
        public final LearnsetQuery getANY() {
            return ANY;
        }

        @NotNull
        public final LearnsetQuery level(int level) {
            return (arg_0, arg_1) -> Companion.level$lambda$2(level, arg_0, arg_1);
        }

        @NotNull
        public final LearnsetQuery getANY_LEVEL() {
            return ANY_LEVEL;
        }

        @NotNull
        public final LearnsetQuery getEGG_MOVE() {
            return EGG_MOVE;
        }

        @NotNull
        public final LearnsetQuery getTUTOR_MOVES() {
            return TUTOR_MOVES;
        }

        @NotNull
        public final LearnsetQuery getTM_MOVE() {
            return TM_MOVE;
        }

        @NotNull
        public final LearnsetQuery getFORM_CHANGE() {
            return FORM_CHANGE;
        }

        @NotNull
        public final LearnsetQuery getEVOLUTION() {
            return EVOLUTION;
        }

        private static final boolean ANY$lambda$1(MoveTemplate move, Learnset learnset) {
            boolean bl;
            block3: {
                Intrinsics.checkNotNullParameter((Object)move, (String)"move");
                Intrinsics.checkNotNullParameter((Object)learnset, (String)"learnset");
                Iterable $this$any$iv = learnset.getLevelUpMoves().values();
                boolean $i$f$any = false;
                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                    bl = false;
                } else {
                    for (Object element$iv : $this$any$iv) {
                        List it = (List)element$iv;
                        boolean bl2 = false;
                        if (!it.contains(move)) continue;
                        bl = true;
                        break block3;
                    }
                    bl = false;
                }
            }
            return bl || learnset.getEggMoves().contains(move) || learnset.getTutorMoves().contains(move) || learnset.getTmMoves().contains(move) || learnset.getFormChangeMoves().contains(move) || learnset.getEvolutionMoves().contains(move);
        }

        private static final boolean level$lambda$2(int $level, MoveTemplate move, Learnset learnset) {
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            Intrinsics.checkNotNullParameter((Object)learnset, (String)"learnset");
            return learnset.getLevelUpMovesUpTo($level).contains(move);
        }

        private static final boolean ANY_LEVEL$lambda$4(MoveTemplate move, Learnset learnset) {
            boolean bl;
            block3: {
                Intrinsics.checkNotNullParameter((Object)move, (String)"move");
                Intrinsics.checkNotNullParameter((Object)learnset, (String)"learnset");
                Iterable $this$any$iv = learnset.getLevelUpMoves().values();
                boolean $i$f$any = false;
                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                    bl = false;
                } else {
                    for (Object element$iv : $this$any$iv) {
                        List it = (List)element$iv;
                        boolean bl2 = false;
                        if (!it.contains(move)) continue;
                        bl = true;
                        break block3;
                    }
                    bl = false;
                }
            }
            return bl;
        }

        private static final boolean EGG_MOVE$lambda$5(MoveTemplate move, Learnset learnset) {
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            Intrinsics.checkNotNullParameter((Object)learnset, (String)"learnset");
            return learnset.getEggMoves().contains(move);
        }

        private static final boolean TUTOR_MOVES$lambda$6(MoveTemplate move, Learnset learnset) {
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            Intrinsics.checkNotNullParameter((Object)learnset, (String)"learnset");
            return learnset.getTutorMoves().contains(move);
        }

        private static final boolean TM_MOVE$lambda$7(MoveTemplate move, Learnset learnset) {
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            Intrinsics.checkNotNullParameter((Object)learnset, (String)"learnset");
            return learnset.getTmMoves().contains(move);
        }

        private static final boolean FORM_CHANGE$lambda$8(MoveTemplate move, Learnset learnset) {
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            Intrinsics.checkNotNullParameter((Object)learnset, (String)"learnset");
            return learnset.getFormChangeMoves().contains(move);
        }

        private static final boolean EVOLUTION$lambda$9(MoveTemplate move, Learnset learnset) {
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            Intrinsics.checkNotNullParameter((Object)learnset, (String)"learnset");
            return learnset.getEvolutionMoves().contains(move);
        }

        static {
            $$INSTANCE = new Companion();
            ANY = Companion::ANY$lambda$1;
            ANY_LEVEL = Companion::ANY_LEVEL$lambda$4;
            EGG_MOVE = Companion::EGG_MOVE$lambda$5;
            TUTOR_MOVES = Companion::TUTOR_MOVES$lambda$6;
            TM_MOVE = Companion::TM_MOVE$lambda$7;
            FORM_CHANGE = Companion::FORM_CHANGE$lambda$8;
            EVOLUTION = Companion::EVOLUTION$lambda$9;
        }
    }
}


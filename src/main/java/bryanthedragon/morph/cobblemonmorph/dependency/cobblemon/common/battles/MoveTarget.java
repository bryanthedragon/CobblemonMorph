/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.Targetable;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0017\b\u0086\u0001\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB'\b\u0002\u0012\u001c\b\u0002\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00040\u0002\u00a2\u0006\u0004\b\t\u0010\nR+\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001a\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/battles/MoveTarget;", "", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/battles/Targetable;", "", "targetList", "Lkotlin/jvm/functions/Function1;", "getTargetList", "()Lkotlin/jvm/functions/Function1;", "<init>", "(Ljava/lang/String;ILkotlin/jvm/functions/Function1;)V", "Companion", "any", "all", "allAdjacent", "allAdjacentFoes", "self", "normal", "randomNormal", "allies", "allySide", "allyTeam", "adjacentAlly", "adjacentAllyOrSelf", "adjacentFoe", "foeSide", "scripted", "common"})
public final class MoveTarget
extends Enum<MoveTarget> {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final Function1<Targetable, List<Targetable>> targetList;
    @NotNull
    private static final MoveTarget[] VALUES;
    public static final /* enum */ MoveTarget any;
    public static final /* enum */ MoveTarget all;
    public static final /* enum */ MoveTarget allAdjacent;
    public static final /* enum */ MoveTarget allAdjacentFoes;
    public static final /* enum */ MoveTarget self;
    public static final /* enum */ MoveTarget normal;
    public static final /* enum */ MoveTarget randomNormal;
    public static final /* enum */ MoveTarget allies;
    public static final /* enum */ MoveTarget allySide;
    public static final /* enum */ MoveTarget allyTeam;
    public static final /* enum */ MoveTarget adjacentAlly;
    public static final /* enum */ MoveTarget adjacentAllyOrSelf;
    public static final /* enum */ MoveTarget adjacentFoe;
    public static final /* enum */ MoveTarget foeSide;
    public static final /* enum */ MoveTarget scripted;
    private static final /* synthetic */ MoveTarget[] $VALUES;

    private MoveTarget(Function1<? super Targetable, ? extends List<? extends Targetable>> targetList) {
        this.targetList = targetList;
    }

    /* synthetic */ MoveTarget(String string, int n, Function1 function1, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            function1 = 1.INSTANCE;
        }
        this((Function1<? super Targetable, ? extends List<? extends Targetable>>)function1);
    }

    @NotNull
    public final Function1<Targetable, List<Targetable>> getTargetList() {
        return this.targetList;
    }

    public static MoveTarget[] values() {
        return (MoveTarget[])$VALUES.clone();
    }

    public static MoveTarget valueOf(String value2) {
        return Enum.valueOf(MoveTarget.class, value2);
    }

    static {
        any = new MoveTarget((Function1<? super Targetable, ? extends List<? extends Targetable>>)((Function1)2.INSTANCE));
        all = new MoveTarget("all", 1, null, 1, null);
        allAdjacent = new MoveTarget("allAdjacent", 2, null, 1, null);
        allAdjacentFoes = new MoveTarget("allAdjacentFoes", 3, null, 1, null);
        self = new MoveTarget("self", 4, null, 1, null);
        normal = new MoveTarget((Function1<? super Targetable, ? extends List<? extends Targetable>>)((Function1)3.INSTANCE));
        randomNormal = new MoveTarget("randomNormal", 6, null, 1, null);
        allies = new MoveTarget("allies", 7, null, 1, null);
        allySide = new MoveTarget("allySide", 8, null, 1, null);
        allyTeam = new MoveTarget("allyTeam", 9, null, 1, null);
        adjacentAlly = new MoveTarget((Function1<? super Targetable, ? extends List<? extends Targetable>>)((Function1)4.INSTANCE));
        adjacentAllyOrSelf = new MoveTarget((Function1<? super Targetable, ? extends List<? extends Targetable>>)((Function1)5.INSTANCE));
        adjacentFoe = new MoveTarget((Function1<? super Targetable, ? extends List<? extends Targetable>>)((Function1)6.INSTANCE));
        foeSide = new MoveTarget("foeSide", 13, null, 1, null);
        scripted = new MoveTarget("scripted", 14, null, 1, null);
        $VALUES = moveTargetArray = new MoveTarget[]{MoveTarget.any, MoveTarget.all, MoveTarget.allAdjacent, MoveTarget.allAdjacentFoes, MoveTarget.self, MoveTarget.normal, MoveTarget.randomNormal, MoveTarget.allies, MoveTarget.allySide, MoveTarget.allyTeam, MoveTarget.adjacentAlly, MoveTarget.adjacentAllyOrSelf, MoveTarget.adjacentFoe, MoveTarget.foeSide, MoveTarget.scripted};
        Companion = new Companion(null);
        VALUES = MoveTarget.values();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/battles/MoveTarget$Companion;", "", "", "showdownId", "Lcom/cobblemon/mod/common/battles/MoveTarget;", "fromShowdownId", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/battles/MoveTarget;", "", "VALUES", "[Lcom/cobblemon/mod/common/battles/MoveTarget;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nMoveTarget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveTarget.kt\ncom/cobblemon/mod/common/battles/MoveTarget$Companion\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,117:1\n1109#2,2:118\n*S KotlinDebug\n*F\n+ 1 MoveTarget.kt\ncom/cobblemon/mod/common/battles/MoveTarget$Companion\n*L\n113#1:118,2\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final MoveTarget fromShowdownId(@NotNull String showdownId) {
            MoveTarget element$iv;
            block2: {
                Intrinsics.checkNotNullParameter((Object)showdownId, (String)"showdownId");
                MoveTarget[] $this$first$iv = VALUES;
                boolean $i$f$first = false;
                int n = $this$first$iv.length;
                for (int i = 0; i < n; ++i) {
                    MoveTarget target = element$iv = $this$first$iv[i];
                    boolean bl = false;
                    if (!StringsKt.equals((String)target.name(), (String)showdownId, (boolean)true)) {
                        continue;
                    }
                    break block2;
                }
                throw new NoSuchElementException("Array contains no element matching the predicate.");
            }
            return element$iv;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}


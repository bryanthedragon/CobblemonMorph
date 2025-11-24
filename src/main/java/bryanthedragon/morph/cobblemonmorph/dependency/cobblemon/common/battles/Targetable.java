/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\u000b\bf\u0018\u00002\u00020\u0001J\u0015\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002H&\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H&\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00000\bH\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0015\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00000\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\nJ\u0015\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00000\bH\u0016\u00a2\u0006\u0004\b\f\u0010\nJ\u0015\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002H&\u00a2\u0006\u0004\b\r\u0010\u0004J\u0019\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0000H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0017\u001a\u00020\u0016H&\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u001a\u001a\u00020\u0019H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001c\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u0007J\u0015\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002H&\u00a2\u0006\u0004\b\u001d\u0010\u0004J\u0017\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0000H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010 \u001a\u00020\u000eH&\u00a2\u0006\u0004\b \u0010!J\u0017\u0010\"\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0000H&\u00a2\u0006\u0004\b\"\u0010#\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/battles/Targetable;", "", "", "getActorPokemon", "()Ljava/lang/Iterable;", "", "getActorShowdownId", "()Ljava/lang/String;", "", "getAdjacent", "()Ljava/util/List;", "getAdjacentAllies", "getAdjacentOpponents", "getAllActivePokemon", "", "asAlly", "", "getDigit", "(Z)I", "other", "getDigitRelativeTo", "(Lcom/cobblemon/mod/common/battles/Targetable;)I", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "getFormat", "()Lcom/cobblemon/mod/common/battles/BattleFormat;", "", "getLetter", "()C", "getPNX", "getSidePokemon", "getSignedDigitRelativeTo", "(Lcom/cobblemon/mod/common/battles/Targetable;)Ljava/lang/String;", "hasPokemon", "()Z", "isAllied", "(Lcom/cobblemon/mod/common/battles/Targetable;)Z", "common"})
public interface Targetable {
    @NotNull
    public Iterable<Targetable> getAllActivePokemon();

    @NotNull
    public Iterable<Targetable> getActorPokemon();

    @NotNull
    public Iterable<Targetable> getSidePokemon();

    @NotNull
    public BattleFormat getFormat();

    public boolean isAllied(@NotNull Targetable var1);

    public boolean hasPokemon();

    @NotNull
    public String getActorShowdownId();

    @NotNull
    public String getPNX();

    @NotNull
    public List<Targetable> getAdjacent();

    @NotNull
    public List<Targetable> getAdjacentAllies();

    @NotNull
    public List<Targetable> getAdjacentOpponents();

    @NotNull
    public String getSignedDigitRelativeTo(@NotNull Targetable var1);

    public int getDigitRelativeTo(@NotNull Targetable var1);

    public int getDigit(boolean var1);

    public char getLetter();

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nMoveTarget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveTarget.kt\ncom/cobblemon/mod/common/battles/Targetable$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,117:1\n766#2:118\n857#2,2:119\n766#2:121\n857#2,2:122\n819#2:124\n847#2,2:125\n*S KotlinDebug\n*F\n+ 1 MoveTarget.kt\ncom/cobblemon/mod/common/battles/Targetable$DefaultImpls\n*L\n27#1:118\n27#1:119,2\n38#1:121\n38#1:122,2\n39#1:124\n39#1:125,2\n*E\n"})
    public static final class DefaultImpls {
        @NotNull
        public static String getPNX(@NotNull Targetable $this) {
            return $this.getActorShowdownId() + $this.getLetter();
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public static List<Targetable> getAdjacent(@NotNull Targetable $this) {
            void $this$filterTo$iv$iv;
            int digit = DefaultImpls.getDigit$default($this, false, 1, null);
            int sideSize = $this.getFormat().getBattleType().getPokemonPerSide();
            Iterable<Targetable> $this$filter$iv = $this.getAllActivePokemon();
            boolean $i$f$filter = false;
            Iterable<Targetable> iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                Targetable it = (Targetable)element$iv$iv;
                boolean bl = false;
                int sameSideDigit = it.isAllied($this) ? DefaultImpls.getDigit$default(it, false, 1, null) : sideSize - DefaultImpls.getDigit$default(it, false, 1, null) + 1;
                int digitDistance = Math.abs(sameSideDigit - digit);
                if (!(digitDistance <= 1 && !Intrinsics.areEqual((Object)it, (Object)$this))) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            return (List)destination$iv$iv;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public static List<Targetable> getAdjacentAllies(@NotNull Targetable $this) {
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv = $this.getAdjacent();
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                Targetable it = (Targetable)element$iv$iv;
                boolean bl = false;
                if (!it.isAllied($this)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            return (List)destination$iv$iv;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public static List<Targetable> getAdjacentOpponents(@NotNull Targetable $this) {
            void $this$filterNotTo$iv$iv;
            Iterable $this$filterNot$iv = $this.getAdjacent();
            boolean $i$f$filterNot = false;
            Iterable iterable = $this$filterNot$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterNotTo = false;
            for (Object element$iv$iv : $this$filterNotTo$iv$iv) {
                Targetable it = (Targetable)element$iv$iv;
                boolean bl = false;
                if (it.isAllied($this)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            return (List)destination$iv$iv;
        }

        @NotNull
        public static String getSignedDigitRelativeTo(@NotNull Targetable $this, @NotNull Targetable other) {
            Intrinsics.checkNotNullParameter((Object)other, (String)"other");
            int digit = $this.getDigitRelativeTo(other);
            return $this.isAllied(other) ? "-" + digit : "+" + digit;
        }

        public static int getDigitRelativeTo(@NotNull Targetable $this, @NotNull Targetable other) {
            Intrinsics.checkNotNullParameter((Object)other, (String)"other");
            return $this.getDigit($this.isAllied(other));
        }

        public static int getDigit(@NotNull Targetable $this, boolean asAlly) {
            int digit = 1;
            for (Targetable activePokemon : $this.getSidePokemon()) {
                if (Intrinsics.areEqual((Object)activePokemon, (Object)$this)) {
                    return digit;
                }
                ++digit;
            }
            return digit * (asAlly ? 1 : -1);
        }

        public static /* synthetic */ int getDigit$default(Targetable targetable, boolean bl, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getDigit");
            }
            if ((n & 1) != 0) {
                bl = true;
            }
            return targetable.getDigit(bl);
        }

        public static char getLetter(@NotNull Targetable $this) {
            int index = 0;
            for (Targetable activePokemon : $this.getActorPokemon()) {
                if (Intrinsics.areEqual((Object)activePokemon, (Object)$this)) break;
                ++index;
            }
            return switch (index) {
                case 0 -> 'a';
                case 1 -> 'b';
                case 2 -> 'c';
                case 3 -> 'd';
                case 4 -> 'e';
                case 5 -> 'f';
                default -> throw new IllegalStateException("Battle has more than 6 in the active slot, makes no sense.");
            };
        }
    }
}


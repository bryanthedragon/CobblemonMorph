/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.CauserInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b \u0010!J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001b\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u0004\u0018\u00010\u00072\u0006\u0010\r\u001a\u00020\n\u00a2\u0006\u0004\b\u000e\u0010\u000fJ;\u0010\u0014\u001a\u0004\u0018\u00018\u0000\"\u0006\b\u0000\u0010\u0010\u0018\u00012\u0006\u0010\r\u001a\u00020\n2\u0014\b\u0002\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00120\u0011H\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0014\u0010\u0015J;\u0010\u0016\u001a\u0004\u0018\u00018\u0000\"\u0006\b\u0000\u0010\u0010\u0018\u00012\u0006\u0010\r\u001a\u00020\n2\u0014\b\u0002\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00120\u0011H\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0016\u0010\u0015J\u001b\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u0017\u001a\u00020\n\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u001b\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u0017\u001a\u00020\n\u00a2\u0006\u0004\b\u001a\u0010\u0019R\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\u001b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "execute", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/battles/dispatch/CauserInstruction;", "causerInstruction", "", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "findInstructionsCausedBy", "(Lcom/cobblemon/mod/common/battles/dispatch/CauserInstruction;)Ljava/util/List;", "comparedTo", "getMostRecentCauser", "(Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;)Lcom/cobblemon/mod/common/battles/dispatch/CauserInstruction;", "T", "Lkotlin/Function1;", "", "predicate", "getMostRecentInstruction", "(Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getNextInstruction", "instruction", "getPreviousInstructions", "(Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;)Ljava/util/List;", "getSubsequentInstructions", "", "instructions", "Ljava/util/List;", "getInstructions", "()Ljava/util/List;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nInstructionSet.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet$getNextInstruction$1\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet$getMostRecentInstruction$1\n*L\n1#1,55:1\n42#1,6:73\n27#1,3:120\n800#2,11:56\n533#2,6:67\n800#2,11:79\n288#2:90\n289#2:92\n800#2,11:94\n288#2,2:105\n800#2,11:107\n288#2,2:118\n800#2,11:123\n533#2,4:134\n538#2:139\n1855#2,2:140\n42#3:91\n1#4:93\n27#5:138\n*S KotlinDebug\n*F\n+ 1 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet\n*L\n38#1:73,6\n50#1:120,3\n29#1:56,11\n29#1:67,6\n38#1:79,11\n38#1:90\n38#1:92\n47#1:94,11\n47#1:105,2\n47#1:107,11\n47#1:118,2\n50#1:123,11\n50#1:134,4\n50#1:139\n53#1:140,2\n38#1:91\n50#1:138\n*E\n"})
public final class InstructionSet {
    @NotNull
    private final List<InterpreterInstruction> instructions = new ArrayList();

    @NotNull
    public final List<InterpreterInstruction> getInstructions() {
        return this.instructions;
    }

    @NotNull
    public final List<InterpreterInstruction> getSubsequentInstructions(@NotNull InterpreterInstruction instruction) {
        Intrinsics.checkNotNullParameter((Object)instruction, (String)"instruction");
        int index = this.instructions.indexOf(instruction);
        return CollectionsKt.toList((Iterable)this.instructions.subList(index + 1, this.instructions.size()));
    }

    @NotNull
    public final List<InterpreterInstruction> getPreviousInstructions(@NotNull InterpreterInstruction instruction) {
        Intrinsics.checkNotNullParameter((Object)instruction, (String)"instruction");
        int index = this.instructions.indexOf(instruction);
        return CollectionsKt.toList((Iterable)this.instructions.subList(0, index));
    }

    /*
     * WARNING - void declaration
     */
    public final /* synthetic */ <T> T getMostRecentInstruction(InterpreterInstruction comparedTo, Function1<? super T, Boolean> predicate) {
        T t;
        block2: {
            void $this$lastOrNull$iv;
            void $this$filterIsInstanceTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)comparedTo, (String)"comparedTo");
            Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
            boolean $i$f$getMostRecentInstruction = false;
            int index = this.getInstructions().indexOf(comparedTo);
            Iterable $this$filterIsInstance$iv = this.getInstructions().subList(0, index);
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                Intrinsics.reifiedOperationMarker((int)3, (String)"T");
                if (!(element$iv$iv instanceof Object)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filterIsInstance$iv = (List)destination$iv$iv;
            boolean $i$f$lastOrNull = false;
            ListIterator iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
            while (iterator$iv.hasPrevious()) {
                Object element$iv = iterator$iv.previous();
                if (!((Boolean)predicate.invoke(element$iv)).booleanValue()) continue;
                t = (T)element$iv;
                break block2;
            }
            t = null;
        }
        return t;
    }

    /*
     * WARNING - void declaration
     */
    public static /* synthetic */ Object getMostRecentInstruction$default(InstructionSet $this, InterpreterInstruction comparedTo, Function1 predicate, int n, Object object) {
        Object v0;
        block3: {
            void $this$lastOrNull$iv;
            void $this$filterIsInstanceTo$iv$iv;
            if ((n & 2) != 0) {
                Intrinsics.needClassReification();
                predicate = getMostRecentInstruction.1.INSTANCE;
            }
            Intrinsics.checkNotNullParameter((Object)comparedTo, (String)"comparedTo");
            Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
            boolean $i$f$getMostRecentInstruction = false;
            int index = $this.getInstructions().indexOf(comparedTo);
            Iterable $this$filterIsInstance$iv = $this.getInstructions().subList(0, index);
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                Intrinsics.reifiedOperationMarker((int)3, (String)"T");
                if (!(element$iv$iv instanceof Object)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filterIsInstance$iv = (List)destination$iv$iv;
            boolean $i$f$lastOrNull = false;
            ListIterator iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
            while (iterator$iv.hasPrevious()) {
                Object element$iv = iterator$iv.previous();
                if (!((Boolean)predicate.invoke(element$iv)).booleanValue()) continue;
                v0 = element$iv;
                break block3;
            }
            v0 = null;
        }
        return v0;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<InterpreterInstruction> findInstructionsCausedBy(@NotNull CauserInstruction causerInstruction) {
        Integer nextCauseIndex;
        Integer n;
        Object v0;
        int thisCauseIndex;
        block7: {
            Intrinsics.checkNotNullParameter((Object)causerInstruction, (String)"causerInstruction");
            InterpreterInstruction cfr_ignored_0 = (InterpreterInstruction)((Object)causerInstruction);
            thisCauseIndex = this.instructions.indexOf(causerInstruction);
            if (thisCauseIndex == this.instructions.size() - 1) {
                return CollectionsKt.emptyList();
            }
            InterpreterInstruction comparedTo$iv = (InterpreterInstruction)((Object)causerInstruction);
            boolean $i$f$getNextInstruction = false;
            int index$iv = this.getInstructions().indexOf(comparedTo$iv);
            if (Intrinsics.areEqual((Object)CollectionsKt.last(this.getInstructions()), (Object)comparedTo$iv)) {
                v0 = null;
            } else {
                void $this$firstOrNull$iv$iv;
                void $this$filterIsInstanceTo$iv$iv$iv;
                Iterable $this$filterIsInstance$iv$iv = this.getInstructions().subList(index$iv + 1, this.getInstructions().size());
                boolean $i$f$filterIsInstance = false;
                Object object = $this$filterIsInstance$iv$iv;
                Collection destination$iv$iv$iv = new ArrayList();
                boolean $i$f$filterIsInstanceTo = false;
                for (Object element$iv$iv$iv : $this$filterIsInstanceTo$iv$iv$iv) {
                    if (!(element$iv$iv$iv instanceof CauserInstruction)) continue;
                    destination$iv$iv$iv.add(element$iv$iv$iv);
                }
                $this$filterIsInstance$iv$iv = (List)destination$iv$iv$iv;
                boolean $i$f$firstOrNull = false;
                object = $this$firstOrNull$iv$iv.iterator();
                while (object.hasNext()) {
                    Object element$iv$iv;
                    Object e = element$iv$iv = object.next();
                    boolean bl = false;
                    Object it = e;
                    if (!true) continue;
                    v0 = element$iv$iv;
                    break block7;
                }
                v0 = null;
            }
        }
        CauserInstruction causerInstruction2 = v0;
        if (causerInstruction2 != null) {
            CauserInstruction it = causerInstruction2;
            boolean bl = false;
            n = this.instructions.indexOf((InterpreterInstruction)((Object)it));
        } else {
            n = null;
        }
        Integer n2 = nextCauseIndex = n;
        return this.instructions.subList(thisCauseIndex + 1, n2 != null ? n2.intValue() : this.instructions.size());
    }

    /*
     * WARNING - void declaration
     */
    public final /* synthetic */ <T> T getNextInstruction(InterpreterInstruction comparedTo, Function1<? super T, Boolean> predicate) {
        T t;
        block3: {
            void $this$firstOrNull$iv;
            void $this$filterIsInstanceTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)comparedTo, (String)"comparedTo");
            Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
            boolean $i$f$getNextInstruction = false;
            int index = this.getInstructions().indexOf(comparedTo);
            if (Intrinsics.areEqual((Object)CollectionsKt.last(this.getInstructions()), (Object)comparedTo)) {
                return null;
            }
            Iterable $this$filterIsInstance$iv = this.getInstructions().subList(index + 1, this.getInstructions().size());
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                Intrinsics.reifiedOperationMarker((int)3, (String)"T");
                if (!(element$iv$iv instanceof Object)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filterIsInstance$iv = (List)destination$iv$iv;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                if (!((Boolean)predicate.invoke(element$iv)).booleanValue()) continue;
                t = (T)element$iv;
                break block3;
            }
            t = null;
        }
        return t;
    }

    /*
     * WARNING - void declaration
     */
    public static /* synthetic */ Object getNextInstruction$default(InstructionSet $this, InterpreterInstruction comparedTo, Function1 predicate, int n, Object object) {
        Object v0;
        block4: {
            void $this$firstOrNull$iv;
            void $this$filterIsInstanceTo$iv$iv;
            if ((n & 2) != 0) {
                Intrinsics.needClassReification();
                predicate = getNextInstruction.1.INSTANCE;
            }
            Intrinsics.checkNotNullParameter((Object)comparedTo, (String)"comparedTo");
            Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
            boolean $i$f$getNextInstruction = false;
            int index = $this.getInstructions().indexOf(comparedTo);
            if (Intrinsics.areEqual((Object)CollectionsKt.last($this.getInstructions()), (Object)comparedTo)) {
                return null;
            }
            Iterable $this$filterIsInstance$iv = $this.getInstructions().subList(index + 1, $this.getInstructions().size());
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                Intrinsics.reifiedOperationMarker((int)3, (String)"T");
                if (!(element$iv$iv instanceof Object)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filterIsInstance$iv = (List)destination$iv$iv;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                if (!((Boolean)predicate.invoke(element$iv)).booleanValue()) continue;
                v0 = element$iv;
                break block4;
            }
            v0 = null;
        }
        return v0;
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final CauserInstruction getMostRecentCauser(@NotNull InterpreterInstruction comparedTo) {
        Object v0;
        block2: {
            void $this$lastOrNull$iv$iv;
            void $this$filterIsInstanceTo$iv$iv$iv;
            Intrinsics.checkNotNullParameter((Object)comparedTo, (String)"comparedTo");
            boolean $i$f$getMostRecentInstruction = false;
            int index$iv = this.getInstructions().indexOf(comparedTo);
            Iterable $this$filterIsInstance$iv$iv = this.getInstructions().subList(0, index$iv);
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv$iv;
            Collection destination$iv$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv$iv : $this$filterIsInstanceTo$iv$iv$iv) {
                if (!(element$iv$iv$iv instanceof CauserInstruction)) continue;
                destination$iv$iv$iv.add(element$iv$iv$iv);
            }
            $this$filterIsInstance$iv$iv = (List)destination$iv$iv$iv;
            boolean $i$f$lastOrNull = false;
            ListIterator iterator$iv$iv = $this$lastOrNull$iv$iv.listIterator($this$lastOrNull$iv$iv.size());
            while (iterator$iv$iv.hasPrevious()) {
                Object element$iv$iv;
                Object e = element$iv$iv = iterator$iv$iv.previous();
                boolean bl = false;
                Object it = e;
                if (!true) continue;
                v0 = element$iv$iv;
                break block2;
            }
            v0 = null;
        }
        return v0;
    }

    public final void execute(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Iterable $this$forEach$iv = this.instructions;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            InterpreterInstruction it = (InterpreterInstruction)element$iv;
            boolean bl = false;
            it.invoke(battle2);
        }
    }
}


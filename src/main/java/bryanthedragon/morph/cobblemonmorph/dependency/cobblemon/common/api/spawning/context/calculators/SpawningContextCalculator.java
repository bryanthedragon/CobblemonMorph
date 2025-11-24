/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextInput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u0000 \r*\b\b\u0000\u0010\u0002*\u00020\u0001*\b\b\u0001\u0010\u0004*\u00020\u00032\u00020\u0005:\u0001\rJ\u0019\u0010\u0007\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0006\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u0007\u0010\bR\u0014\u0010\f\u001a\u00020\t8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator;", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextInput;", "I", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "O", "", "input", "calculate", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextInput;)Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "", "getName", "()Ljava/lang/String;", "name", "Companion", "common"})
public interface SpawningContextCalculator<I extends SpawningContextInput, O extends SpawningContext> {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator$Companion.$$INSTANCE;

    @NotNull
    public String getName();

    @Nullable
    public O calculate(@NotNull I var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ'\u0010\u0007\u001a\u00020\u00062\u000e\u0010\u0003\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001d\u0010\t\u001a\u00020\u00062\u000e\u0010\u0003\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0004\b\t\u0010\nR\"\u0010\f\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR#\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0011\u0010\u0013R#\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0012\u001a\u0004\b\u0014\u0010\u0013R#\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0012\u001a\u0004\b\u0015\u0010\u0013R#\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0012\u001a\u0004\b\u0016\u0010\u0013R\u001b\u0010\u001b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00180\u00178F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator$Companion;", "", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator;", "calculator", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "", "register", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator;Lcom/cobblemon/mod/common/api/Priority;)V", "unregister", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator;)V", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "calculators", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "Lkotlin/Function1;", "Lnet/minecraft/world/level/block/state/BlockState;", "", "isAirCondition", "Lkotlin/jvm/functions/Function1;", "()Lkotlin/jvm/functions/Function1;", "isLavaCondition", "isSolidCondition", "isWaterCondition", "", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningContextCalculator;", "getPrioritizedAreaCalculators", "()Ljava/util/List;", "prioritizedAreaCalculators", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nSpawningContextCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningContextCalculator.kt\ncom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,68:1\n800#2,11:69\n*S KotlinDebug\n*F\n+ 1 SpawningContextCalculator.kt\ncom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator$Companion\n*L\n40#1:69,11\n*E\n"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final Function1<BlockState, Boolean> isAirCondition;
        @NotNull
        private static final Function1<BlockState, Boolean> isSolidCondition;
        @NotNull
        private static final Function1<BlockState, Boolean> isWaterCondition;
        @NotNull
        private static final Function1<BlockState, Boolean> isLavaCondition;
        @NotNull
        private static final PrioritizedList<SpawningContextCalculator<?, ?>> calculators;

        private Companion() {
        }

        @NotNull
        public final Function1<BlockState, Boolean> isAirCondition() {
            return isAirCondition;
        }

        @NotNull
        public final Function1<BlockState, Boolean> isSolidCondition() {
            return isSolidCondition;
        }

        @NotNull
        public final Function1<BlockState, Boolean> isWaterCondition() {
            return isWaterCondition;
        }

        @NotNull
        public final Function1<BlockState, Boolean> isLavaCondition() {
            return isLavaCondition;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final List<AreaSpawningContextCalculator<?>> getPrioritizedAreaCalculators() {
            void $this$filterIsInstanceTo$iv$iv;
            Iterable $this$filterIsInstance$iv = calculators;
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof AreaSpawningContextCalculator)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            return (List)destination$iv$iv;
        }

        public final void register(@NotNull SpawningContextCalculator<?, ?> calculator, @NotNull Priority priority) {
            Intrinsics.checkNotNullParameter(calculator, (String)"calculator");
            Intrinsics.checkNotNullParameter((Object)((Object)priority), (String)"priority");
            calculators.add(priority, calculator);
        }

        public static /* synthetic */ void register$default(Companion companion, SpawningContextCalculator spawningContextCalculator, Priority priority, int n, Object object) {
            if ((n & 2) != 0) {
                priority = Priority.NORMAL;
            }
            companion.register(spawningContextCalculator, priority);
        }

        public final void unregister(@NotNull SpawningContextCalculator<?, ?> calculator) {
            Intrinsics.checkNotNullParameter(calculator, (String)"calculator");
            calculators.remove(calculator);
        }

        static {
            $$INSTANCE = new Companion();
            isAirCondition = isAirCondition.1.INSTANCE;
            isSolidCondition = isSolidCondition.1.INSTANCE;
            isWaterCondition = isWaterCondition.1.INSTANCE;
            isLavaCondition = isLavaCondition.1.INSTANCE;
            calculators = new PrioritizedList();
        }
    }
}


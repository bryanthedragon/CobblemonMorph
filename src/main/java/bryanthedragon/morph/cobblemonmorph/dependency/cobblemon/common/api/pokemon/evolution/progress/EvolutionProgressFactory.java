/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DamageTakenEvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DefeatEvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.LastBattleCriticalHitsEvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.RecoilEvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.UseMoveEvolutionProgress;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001b\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0010\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0007\u00a2\u0006\u0004\b\n\u0010\u000bRD\u0010\u000e\u001a2\u0012\u0004\u0012\u00020\u0002\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00070\fj\u0018\u0012\u0004\u0012\u00020\u0002\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0007`\r8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgressFactory;", "", "", "variant", "Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "create", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "Lkotlin/Function0;", "factory", "", "registerVariant", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "variants", "Ljava/util/HashMap;", "<init>", "()V", "common"})
public final class EvolutionProgressFactory {
    @NotNull
    public static final EvolutionProgressFactory INSTANCE = new EvolutionProgressFactory();
    @NotNull
    private static final HashMap<String, Function0<EvolutionProgress<?>>> variants = new HashMap();

    private EvolutionProgressFactory() {
    }

    public final void registerVariant(@NotNull String variant, @NotNull Function0<? extends EvolutionProgress<?>> factory) {
        Intrinsics.checkNotNullParameter((Object)variant, (String)"variant");
        Intrinsics.checkNotNullParameter(factory, (String)"factory");
        ((Map)variants).put(variant, factory);
    }

    @Nullable
    public final EvolutionProgress<?> create(@NotNull String variant) {
        Intrinsics.checkNotNullParameter((Object)variant, (String)"variant");
        Function0<EvolutionProgress<?>> function0 = variants.get(variant);
        if (function0 == null) {
            return null;
        }
        Function0<EvolutionProgress<?>> factory = function0;
        return (EvolutionProgress)factory.invoke();
    }

    static {
        String string = DamageTakenEvolutionProgress.Companion.getID().toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"DamageTakenEvolutionProgress.ID.toString()");
        INSTANCE.registerVariant(string, 1.INSTANCE);
        String string2 = DefeatEvolutionProgress.Companion.getID().toString();
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"DefeatEvolutionProgress.ID.toString()");
        INSTANCE.registerVariant(string2, 2.INSTANCE);
        String string3 = LastBattleCriticalHitsEvolutionProgress.Companion.getID().toString();
        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"LastBattleCriticalHitsEv\u2026ionProgress.ID.toString()");
        INSTANCE.registerVariant(string3, 3.INSTANCE);
        String string4 = RecoilEvolutionProgress.Companion.getID().toString();
        Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"RecoilEvolutionProgress.ID.toString()");
        INSTANCE.registerVariant(string4, 4.INSTANCE);
        String string5 = UseMoveEvolutionProgress.Companion.getID().toString();
        Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"UseMoveEvolutionProgress.ID.toString()");
        INSTANCE.registerVariant(string5, 5.INSTANCE);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.CobblemonCaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.DebugCaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen1CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen2CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen3And4CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen5CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen6CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen7CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen8CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen9CaptureCalculator;
import java.util.LinkedHashMap;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\fJ\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\r\u001a\u00020\bH\u0000\u00a2\u0006\u0004\b\u000b\u0010\fR0\u0010\u0010\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00040\u000ej\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0004`\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculators;", "", "", "id", "Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "fromId", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;", "calculator", "", "register", "(Lcom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator;)V", "registerDefaults$common", "()V", "registerDefaults", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "calculators", "Ljava/util/LinkedHashMap;", "<init>", "common"})
public final class CaptureCalculators {
    @NotNull
    public static final CaptureCalculators INSTANCE = new CaptureCalculators();
    @NotNull
    private static final LinkedHashMap<String, CaptureCalculator> calculators = new LinkedHashMap();

    private CaptureCalculators() {
    }

    public final void register(@NotNull CaptureCalculator calculator) {
        Intrinsics.checkNotNullParameter((Object)calculator, (String)"calculator");
        String string = calculator.id().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        String id = string;
        CaptureCalculator existing = calculators.put(id, calculator);
        if (existing != null) {
            Cobblemon.INSTANCE.getLOGGER().debug("The capture calculator {} with ID {} was replaced by {}", (Object)Reflection.getOrCreateKotlinClass(existing.getClass()).getQualifiedName(), (Object)id, (Object)Reflection.getOrCreateKotlinClass(calculator.getClass()).getQualifiedName());
        }
    }

    @Nullable
    public final CaptureCalculator fromId(@NotNull String id) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        String string = id.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        return calculators.get(string);
    }

    public final void registerDefaults$common() {
        this.register(Gen1CaptureCalculator.INSTANCE);
        this.register(new Gen2CaptureCalculator(false));
        this.register(new Gen2CaptureCalculator(true));
        this.register(Gen3And4CaptureCalculator.INSTANCE);
        this.register(Gen5CaptureCalculator.INSTANCE);
        this.register(Gen6CaptureCalculator.INSTANCE);
        this.register(Gen7CaptureCalculator.INSTANCE);
        this.register(Gen8CaptureCalculator.INSTANCE);
        this.register(Gen9CaptureCalculator.INSTANCE);
        this.register(CobblemonCaptureCalculator.INSTANCE);
        this.register(DebugCaptureCalculator.INSTANCE);
    }
}


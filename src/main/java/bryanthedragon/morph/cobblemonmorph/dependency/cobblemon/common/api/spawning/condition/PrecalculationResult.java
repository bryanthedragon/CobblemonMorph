/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningPrecalculation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0001B\u0015\b\u0004\u0012\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\t\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001d\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0004\u001a\u00020\u0003H&\u00a2\u0006\u0004\b\u0007\u0010\bR\u001b\u0010\n\u001a\u0006\u0012\u0002\b\u00030\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u0082\u0001\u0002\u0010\u0011\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/PrecalculationResult;", "", "T", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "retrieve", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Ljava/util/List;", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation;", "calculation", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation;", "getCalculation", "()Lcom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation;", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation;)V", "Lcom/cobblemon/mod/common/api/spawning/condition/FinalPrecalculationResult;", "Lcom/cobblemon/mod/common/api/spawning/condition/NestedPrecalculationResult;", "common"})
public abstract class PrecalculationResult<T> {
    @NotNull
    private final SpawningPrecalculation<?> calculation;

    private PrecalculationResult(SpawningPrecalculation<?> calculation) {
        this.calculation = calculation;
    }

    @NotNull
    public final SpawningPrecalculation<?> getCalculation() {
        return this.calculation;
    }

    @NotNull
    public abstract List<SpawnDetail> retrieve(@NotNull SpawningContext var1);

    public /* synthetic */ PrecalculationResult(SpawningPrecalculation calculation, DefaultConstructorMarker $constructor_marker) {
        this(calculation);
    }
}


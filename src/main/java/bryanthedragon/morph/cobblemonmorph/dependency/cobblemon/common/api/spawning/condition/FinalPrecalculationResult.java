/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.PrecalculationResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningPrecalculation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B-\u0012\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u000f\u0012\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\n\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001d\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\tR)\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/FinalPrecalculationResult;", "", "T", "Lcom/cobblemon/mod/common/api/spawning/condition/PrecalculationResult;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "retrieve", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Ljava/util/List;", "", "mapping", "Ljava/util/Map;", "getMapping", "()Ljava/util/Map;", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation;", "calculation", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation;Ljava/util/Map;)V", "common"})
public final class FinalPrecalculationResult<T>
extends PrecalculationResult<T> {
    @NotNull
    private final Map<T, List<SpawnDetail>> mapping;

    public FinalPrecalculationResult(@NotNull SpawningPrecalculation<?> calculation, @NotNull Map<T, ? extends List<? extends SpawnDetail>> mapping) {
        Intrinsics.checkNotNullParameter(calculation, (String)"calculation");
        Intrinsics.checkNotNullParameter(mapping, (String)"mapping");
        super(calculation, null);
        this.mapping = mapping;
    }

    @NotNull
    public final Map<T, List<SpawnDetail>> getMapping() {
        return this.mapping;
    }

    @Override
    @NotNull
    public List<SpawnDetail> retrieve(@NotNull SpawningContext ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        List list = this.mapping.get(this.getCalculation().select(ctx));
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        return list;
    }
}


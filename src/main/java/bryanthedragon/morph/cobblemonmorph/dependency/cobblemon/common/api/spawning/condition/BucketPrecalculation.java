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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.PrecalculationResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningPrecalculation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0005\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/BucketPrecalculation;", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation;", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "select", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "detail", "", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)Ljava/util/List;", "<init>", "()V", "common"})
public final class BucketPrecalculation
implements SpawningPrecalculation<SpawnBucket> {
    @NotNull
    public static final BucketPrecalculation INSTANCE = new BucketPrecalculation();

    private BucketPrecalculation() {
    }

    @Override
    @NotNull
    public List<SpawnBucket> select(@NotNull SpawnDetail detail) {
        Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
        return CollectionsKt.listOf((Object)detail.getBucket());
    }

    @Override
    @NotNull
    public SpawnBucket select(@NotNull SpawningContext ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return ctx.getCause().getBucket();
    }

    @Override
    @NotNull
    public PrecalculationResult<SpawnBucket> generate(@NotNull List<? extends SpawnDetail> details, @NotNull List<? extends SpawningPrecalculation<?>> next) {
        return SpawningPrecalculation.DefaultImpls.generate(this, details, next);
    }
}


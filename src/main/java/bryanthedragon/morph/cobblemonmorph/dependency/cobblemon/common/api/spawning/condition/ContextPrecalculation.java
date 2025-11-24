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
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J%\u0010\u0005\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0005\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/ContextPrecalculation;", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation;", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "select", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "detail", "", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)Ljava/util/List;", "<init>", "()V", "common"})
public final class ContextPrecalculation
implements SpawningPrecalculation<Class<? extends SpawningContext>> {
    @NotNull
    public static final ContextPrecalculation INSTANCE = new ContextPrecalculation();

    private ContextPrecalculation() {
    }

    @Override
    @NotNull
    public List<Class<? extends SpawningContext>> select(@NotNull SpawnDetail detail) {
        Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
        return CollectionsKt.listOf(detail.getContext().getClazz());
    }

    @Override
    @NotNull
    public Class<? extends SpawningContext> select(@NotNull SpawningContext ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return ctx.getClass();
    }

    @Override
    @NotNull
    public PrecalculationResult<Class<? extends SpawningContext>> generate(@NotNull List<? extends SpawnDetail> details, @NotNull List<? extends SpawningPrecalculation<?>> next) {
        return SpawningPrecalculation.DefaultImpls.generate(this, details, next);
    }
}


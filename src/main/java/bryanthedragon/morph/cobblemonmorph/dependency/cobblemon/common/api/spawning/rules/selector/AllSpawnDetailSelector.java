/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawnDetailSelector;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/spawning/rules/selector/AllSpawnDetailSelector;", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawnDetailSelector;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "spawnDetail", "", "selects", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)Z", "<init>", "()V", "common"})
public final class AllSpawnDetailSelector
implements SpawnDetailSelector {
    @NotNull
    public static final AllSpawnDetailSelector INSTANCE = new AllSpawnDetailSelector();

    private AllSpawnDetailSelector() {
    }

    @Override
    public boolean selects(@NotNull SpawnDetail spawnDetail) {
        Intrinsics.checkNotNullParameter((Object)spawnDetail, (String)"spawnDetail");
        return true;
    }
}


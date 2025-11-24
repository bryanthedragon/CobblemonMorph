/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerLevel
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextInput;", "", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "cause", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "getCause", "()Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/server/level/ServerLevel;", "getWorld", "()Lnet/minecraft/server/level/ServerLevel;", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/SpawnCause;Lnet/minecraft/server/level/ServerLevel;)V", "common"})
public class SpawningContextInput {
    @NotNull
    private final SpawnCause cause;
    @NotNull
    private final ServerLevel world;

    public SpawningContextInput(@NotNull SpawnCause cause, @NotNull ServerLevel world) {
        Intrinsics.checkNotNullParameter((Object)cause, (String)"cause");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        this.cause = cause;
        this.world = world;
    }

    @NotNull
    public final SpawnCause getCause() {
        return this.cause;
    }

    @NotNull
    public final ServerLevel getWorld() {
        return this.world;
    }
}


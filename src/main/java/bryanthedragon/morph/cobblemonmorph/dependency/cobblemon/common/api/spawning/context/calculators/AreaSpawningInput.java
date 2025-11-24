/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u0013\u0010\u0014R\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextInput;", "Lnet/minecraft/core/BlockPos;", "position", "Lnet/minecraft/core/BlockPos;", "getPosition", "()Lnet/minecraft/core/BlockPos;", "setPosition", "(Lnet/minecraft/core/BlockPos;)V", "Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "slice", "Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "getSlice", "()Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "spawner", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "getSpawner", "()Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Lnet/minecraft/core/BlockPos;Lcom/cobblemon/mod/common/api/spawning/WorldSlice;)V", "common"})
public class AreaSpawningInput
extends SpawningContextInput {
    @NotNull
    private final Spawner spawner;
    @NotNull
    private BlockPos position;
    @NotNull
    private final WorldSlice slice;

    public AreaSpawningInput(@NotNull Spawner spawner, @NotNull BlockPos position, @NotNull WorldSlice slice) {
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)slice, (String)"slice");
        super(slice.getCause(), slice.getWorld());
        this.spawner = spawner;
        this.position = position;
        this.slice = slice;
    }

    @NotNull
    public final Spawner getSpawner() {
        return this.spawner;
    }

    @NotNull
    public final BlockPos getPosition() {
        return this.position;
    }

    public final void setPosition(@NotNull BlockPos blockPos2) {
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"<set-?>");
        this.position = blockPos2;
    }

    @NotNull
    public final WorldSlice getSlice() {
        return this.slice;
    }
}


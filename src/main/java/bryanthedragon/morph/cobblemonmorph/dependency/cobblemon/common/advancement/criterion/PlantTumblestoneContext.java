/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.TumblestoneBlock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u0010\u0010\u0011R\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/PlantTumblestoneContext;", "", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/core/BlockPos;", "getPos", "()Lnet/minecraft/core/BlockPos;", "setPos", "(Lnet/minecraft/core/BlockPos;)V", "Lcom/cobblemon/mod/common/block/TumblestoneBlock;", "tumbleStoneBlock", "Lcom/cobblemon/mod/common/block/TumblestoneBlock;", "getTumbleStoneBlock", "()Lcom/cobblemon/mod/common/block/TumblestoneBlock;", "setTumbleStoneBlock", "(Lcom/cobblemon/mod/common/block/TumblestoneBlock;)V", "<init>", "(Lnet/minecraft/core/BlockPos;Lcom/cobblemon/mod/common/block/TumblestoneBlock;)V", "common"})
public class PlantTumblestoneContext {
    @NotNull
    private BlockPos pos;
    @NotNull
    private TumblestoneBlock tumbleStoneBlock;

    public PlantTumblestoneContext(@NotNull BlockPos pos, @NotNull TumblestoneBlock tumbleStoneBlock) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)((Object)tumbleStoneBlock), (String)"tumbleStoneBlock");
        this.pos = pos;
        this.tumbleStoneBlock = tumbleStoneBlock;
    }

    @NotNull
    public final BlockPos getPos() {
        return this.pos;
    }

    public final void setPos(@NotNull BlockPos blockPos2) {
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"<set-?>");
        this.pos = blockPos2;
    }

    @NotNull
    public final TumblestoneBlock getTumbleStoneBlock() {
        return this.tumbleStoneBlock;
    }

    public final void setTumbleStoneBlock(@NotNull TumblestoneBlock tumblestoneBlock) {
        Intrinsics.checkNotNullParameter((Object)((Object)tumblestoneBlock), (String)"<set-?>");
        this.tumbleStoneBlock = tumblestoneBlock;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition.MultiblockCondition;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\t\u001a\u00020\u0001\u0012\u0006\u0010\r\u001a\u00020\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\u00020\u00018\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\u00020\u00018\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\n\u001a\u0004\b\u000e\u0010\f\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/multiblock/condition/AndCondition;", "Lcom/cobblemon/mod/common/api/multiblock/condition/MultiblockCondition;", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "box", "", "test", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/shapes/VoxelShape;)Z", "conditionOne", "Lcom/cobblemon/mod/common/api/multiblock/condition/MultiblockCondition;", "getConditionOne", "()Lcom/cobblemon/mod/common/api/multiblock/condition/MultiblockCondition;", "conditionTwo", "getConditionTwo", "<init>", "(Lcom/cobblemon/mod/common/api/multiblock/condition/MultiblockCondition;Lcom/cobblemon/mod/common/api/multiblock/condition/MultiblockCondition;)V", "common"})
public final class AndCondition
implements MultiblockCondition {
    @NotNull
    private final MultiblockCondition conditionOne;
    @NotNull
    private final MultiblockCondition conditionTwo;

    public AndCondition(@NotNull MultiblockCondition conditionOne, @NotNull MultiblockCondition conditionTwo) {
        Intrinsics.checkNotNullParameter((Object)conditionOne, (String)"conditionOne");
        Intrinsics.checkNotNullParameter((Object)conditionTwo, (String)"conditionTwo");
        this.conditionOne = conditionOne;
        this.conditionTwo = conditionTwo;
    }

    @NotNull
    public final MultiblockCondition getConditionOne() {
        return this.conditionOne;
    }

    @NotNull
    public final MultiblockCondition getConditionTwo() {
        return this.conditionTwo;
    }

    @Override
    public boolean test(@NotNull ServerLevel world, @NotNull VoxelShape box) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)box, (String)"box");
        return this.conditionOne.test(world, box) & this.conditionTwo.test(world, box);
    }
}


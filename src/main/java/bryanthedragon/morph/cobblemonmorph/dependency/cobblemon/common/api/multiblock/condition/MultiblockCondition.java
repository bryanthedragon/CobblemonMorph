/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition;

import kotlin.Metadata;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/multiblock/condition/MultiblockCondition;", "", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "box", "", "test", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/shapes/VoxelShape;)Z", "common"})
public interface MultiblockCondition {
    public boolean test(@NotNull ServerLevel var1, @NotNull VoxelShape var2);
}


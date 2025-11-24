/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.PointedDripstoneBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.Slice
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value={PointedDripstoneBlock.class})
public abstract class PointedDripstoneBlockMixin {
    @Redirect(method={"canGrow(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;)Z"}, at=@At(value="INVOKE", target="Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal=0), slice=@Slice(from=@At(value="FIELD", target="Lnet/minecraft/block/Blocks;DRIPSTONE_BLOCK:Lnet/minecraft/block/Block;")))
    private static boolean cobblemon$validateMoonStoneDripstone(BlockState instance, Block block) {
        return instance.m_204336_(CobblemonBlockTags.DRIPSTONE_GROWABLE);
    }
}


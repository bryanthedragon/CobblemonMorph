/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.flag.FeatureFlag
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.ButtonBlock
 *  net.minecraft.world.level.block.FlowerPotBlock
 *  net.minecraft.world.level.block.LeavesBlock
 *  net.minecraft.world.level.block.RotatedPillarBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.properties.BlockSetType
 *  net.minecraft.world.level.material.MapColor
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker;

import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={Blocks.class})
public interface BlocksInvoker {
    @Invoker(value="createLogBlock")
    public static RotatedPillarBlock createLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        throw new UnsupportedOperationException();
    }

    @Invoker(value="createLeavesBlock")
    public static LeavesBlock createLeavesBlock(SoundType soundGroup) {
        throw new UnsupportedOperationException();
    }

    @Invoker(value="createWoodenButtonBlock")
    public static ButtonBlock createWoodenButtonBlock(BlockSetType blockSetType, FeatureFlag ... requiredFeatures) {
        throw new UnsupportedOperationException();
    }

    @Invoker(value="createFlowerPotBlock")
    public static FlowerPotBlock createFlowerPotBlock(Block flower, FeatureFlag ... requiredFeatures) {
        throw new UnsupportedOperationException();
    }
}


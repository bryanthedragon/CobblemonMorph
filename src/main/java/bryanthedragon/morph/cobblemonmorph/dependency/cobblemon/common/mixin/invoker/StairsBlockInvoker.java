/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={StairBlock.class})
public interface StairsBlockInvoker {
    @Invoker(value="<init>")
    public static StairBlock cobblemon$create(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        throw new UnsupportedOperationException();
    }
}


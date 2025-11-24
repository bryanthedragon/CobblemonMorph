/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.dispenser.ShearsDispenseItemBehavior
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ShearableBlock;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ShearsDispenseItemBehavior.class})
public abstract class ShearsDispenserBehaviorMixin {
    @Inject(method={"tryShearBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private static void cobblemon$tryApricornHarvest(ServerLevel world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = world.m_8055_(pos);
        Block block = state.m_60734_();
        if (block instanceof ShearableBlock) {
            ShearableBlock shearableBlock = (ShearableBlock)block;
            cir.setReturnValue((Object)shearableBlock.attemptShear((Level)world, state, pos, (Function0<Unit>)((Function0)() -> Unit.INSTANCE)));
        }
    }
}


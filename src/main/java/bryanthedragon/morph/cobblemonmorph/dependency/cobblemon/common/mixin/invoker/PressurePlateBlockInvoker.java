/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.PressurePlateBlock
 *  net.minecraft.world.level.block.PressurePlateBlock$Sensitivity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.properties.BlockSetType
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={PressurePlateBlock.class})
public interface PressurePlateBlockInvoker {
    @Invoker(value="<init>")
    public static PressurePlateBlock cobblemon$create(PressurePlateBlock.Sensitivity type, BlockBehaviour.Properties settings, BlockSetType blockSetType) {
        throw new UnsupportedOperationException();
    }
}


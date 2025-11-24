/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.entity.DecoratedPotPatterns
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.sherds.CobblemonSherds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={DecoratedPotPatterns.class})
public abstract class DecoratedPotPatternsMixin {
    @Inject(method={"fromSherd"}, at={@At(value="HEAD")}, cancellable=true)
    private static void cobblemon$getCobblemonSherdTexture(Item sherd, CallbackInfoReturnable<ResourceKey<String>> cir) {
        if (CobblemonSherds.INSTANCE.getSherdToPattern().containsKey(sherd)) {
            cir.setReturnValue(CobblemonSherds.INSTANCE.getSherdToPattern().get(sherd));
            return;
        }
    }
}


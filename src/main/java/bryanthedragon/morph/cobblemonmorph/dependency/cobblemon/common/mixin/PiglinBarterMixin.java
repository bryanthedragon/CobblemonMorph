/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.monster.piglin.PiglinAi
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PiglinAi.class})
public class PiglinBarterMixin {
    @Inject(method={"acceptsForBarter"}, at={@At(value="RETURN")}, cancellable=true)
    private static void cobblemon$acceptsForBarter(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
        if (!((Boolean)ci.getReturnValue()).booleanValue() && stack.m_150930_((Item)CobblemonItems.RELIC_COIN_POUCH)) {
            ci.setReturnValue((Object)true);
        }
    }
}


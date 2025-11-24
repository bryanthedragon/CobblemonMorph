/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.monster.piglin.Piglin
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Piglin.class})
public abstract class PiglinEntityMixin {
    @Inject(method={"equipToOffHand"}, at={@At(value="HEAD")}, cancellable=true)
    public void cobblemon$isValidBarteringItem(ItemStack stack, CallbackInfo ci) {
        Piglin entity2 = (Piglin)this;
        if (stack.m_150930_((Item)CobblemonItems.RELIC_COIN_POUCH)) {
            entity2.m_8061_(EquipmentSlot.OFFHAND, stack);
            entity2.m_21508_(EquipmentSlot.OFFHAND);
            ci.cancel();
        }
    }
}


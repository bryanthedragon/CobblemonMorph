/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.MouseHandler
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.PartySendBinding;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={MouseHandler.class})
public class PartyScrollMixin {
    @Shadow
    private double f_91518_;

    @Inject(method={"onMouseScroll"}, at={@At(value="FIELD", target="Lnet/minecraft/client/Mouse;eventDeltaWheel:D", opcode=181, ordinal=2, shift=At.Shift.BEFORE)}, cancellable=true)
    public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (PartySendBinding.INSTANCE.getWasDown()) {
            int i = (int)this.f_91518_;
            if (i > 0) {
                while (i-- > 0) {
                    CobblemonClient.INSTANCE.getStorage().shiftSelected(false);
                }
                ci.cancel();
                this.f_91518_ = 0.0;
                PartySendBinding.INSTANCE.actioned();
            } else if (i < 0) {
                while (i++ < 0) {
                    CobblemonClient.INSTANCE.getStorage().shiftSelected(true);
                }
                ci.cancel();
                this.f_91518_ = 0.0;
                PartySendBinding.INSTANCE.actioned();
            }
        }
    }
}


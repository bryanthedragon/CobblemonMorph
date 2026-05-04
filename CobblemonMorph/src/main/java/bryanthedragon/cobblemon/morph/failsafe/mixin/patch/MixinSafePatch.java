package bryanthedragon.cobblemon.morph.failsafe.mixin.patch;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bryanthedragon.cobblemon.morph.failsafe.mixin.MixinFailSafe;

import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class MixinSafePatch extends MixinFailSafe {

    @Inject(method = "m_91332_", at = @At("HEAD"), cancellable = true)
    private void ignoreMissingCrashHandler(CallbackInfo ci) {
        ci.cancel();
    }
}
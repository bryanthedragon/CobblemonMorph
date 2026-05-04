package bryanthedragon.cobblemon.morph.failsafe.mixin;

import bryanthedragon.cobblemon.morph.failsafe.mixin.patch.MixinSafePatch;
import bryanthedragon.cobblemon.morph.failsafe.FailSafe;

public class MixinFailSafe extends FailSafe {
    public static final MixinSafePatch SafePatchInstance = new MixinSafePatch();
}

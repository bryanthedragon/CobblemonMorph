package bryanthedragon.morph.cobblemonmorph.failsafe.mixin;

import bryanthedragon.morph.cobblemonmorph.failsafe.mixin.patch.MixinSafePatch;
import bryanthedragon.morph.cobblemonmorph.failsafe.FailSafe;

public class MixinFailSafe extends FailSafe {
    public static final MixinSafePatch SafePatchInstance = new MixinSafePatch();
}

package bryanthedragon.cobblemon.morph.failsafe;

import bryanthedragon.cobblemon.morph.failsafe.mixin.MixinFailSafe;

public class FailSafe {

    public static final MixinFailSafe MIXINInstance = new MixinFailSafe();

    public static void init() {
        System.out.println("Initializing failsafe");
    }

    public static MixinFailSafe getMixinFailSafe() {
        return MIXINInstance;
    }
}
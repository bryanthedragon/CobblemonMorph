
package com.oracle.truffle.js.runtime.objects;

public final class Dead {
    private static final Dead INSTANCE = new Dead();

    private Dead() {
    }

    public static Dead instance() {
        return INSTANCE;
    }
}


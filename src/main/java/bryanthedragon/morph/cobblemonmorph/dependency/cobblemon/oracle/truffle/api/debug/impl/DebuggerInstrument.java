
package com.oracle.truffle.api.debug.impl;

import com.oracle.truffle.api.debug.Debugger;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import java.lang.reflect.Method;

@TruffleInstrument.Registration(name="Debugger", id="debugger", services={Debugger.class})
public final class DebuggerInstrument
extends TruffleInstrument {
    static final String ID = "debugger";
    private static DebuggerFactory factory = DebuggerInstrument.getDefaultFactory();

    private static DebuggerFactory getDefaultFactory() {
        try {
            Method createFactory = Debugger.class.getDeclaredMethod("createFactory", new Class[0]);
            createFactory.setAccessible(true);
            return (DebuggerFactory)createFactory.invoke(null, new Object[0]);
        }
        catch (Exception ex) {
            throw new AssertionError((Object)ex);
        }
    }

    @Override
    protected void onCreate(TruffleInstrument.Env env) {
        env.registerService(factory.create(env));
    }

    public static void setFactory(DebuggerFactory factory) {
        if (factory == null || !factory.getClass().getName().startsWith("com.oracle.truffle.api.debug")) {
            throw new IllegalArgumentException("Wrong factory: " + factory);
        }
        DebuggerInstrument.factory = factory;
    }

    public static interface DebuggerFactory {
        public Debugger create(TruffleInstrument.Env var1);
    }
}


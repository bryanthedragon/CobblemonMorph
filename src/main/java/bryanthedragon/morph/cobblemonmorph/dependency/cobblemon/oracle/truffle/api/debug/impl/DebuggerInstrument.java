package com.oracle.truffle.api.debug.impl;

import com.oracle.truffle.api.debug.Debugger;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import java.lang.reflect.Method;

@TruffleInstrument.Registration(name = "Debugger", id = "debugger", services = Debugger.class)
public final class DebuggerInstrument extends TruffleInstrument {
   static final String ID = "debugger";
   private static DebuggerInstrument.DebuggerFactory factory = getDefaultFactory();

   private static DebuggerInstrument.DebuggerFactory getDefaultFactory() {
      try {
         Method createFactory = Debugger.class.getDeclaredMethod("createFactory");
         createFactory.setAccessible(true);
         return (DebuggerInstrument.DebuggerFactory)createFactory.invoke(null);
      } catch (Exception var1) {
         throw new AssertionError(var1);
      }
   }

   @Override
   protected void onCreate(TruffleInstrument.Env env) {
      env.registerService(factory.create(env));
   }

   public static void setFactory(DebuggerInstrument.DebuggerFactory factory) {
      if (factory != null && factory.getClass().getName().startsWith("com.oracle.truffle.api.debug")) {
         DebuggerInstrument.factory = factory;
      } else {
         throw new IllegalArgumentException("Wrong factory: " + factory);
      }
   }

   public interface DebuggerFactory {
      Debugger create(TruffleInstrument.Env env);
   }
}

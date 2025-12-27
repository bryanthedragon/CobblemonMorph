package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.util.DebugCounter;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles.Lookup;

public final class JSSharedData {
   private final JSContext context;
   private final JSDynamicObject proto;
   private volatile Assumption prototypeAssumption;
   private static final VarHandle PROTOTYPE_ASSUMPTION_VAR_HANDLE;
   private static final DebugCounter prototypeAssumptionsCreated;
   private static final DebugCounter prototypeAssumptionsRemoved;

   public JSSharedData(JSContext context, JSDynamicObject prototype) {
      this.context = context;
      this.proto = prototype;
   }

   JSContext getContext() {
      return this.context;
   }

   public JSDynamicObject getPrototype() {
      return this.proto;
   }

   Assumption getPrototypeAssumption() {
      Assumption assumption = this.prototypeAssumption;
      if (assumption != null) {
         return assumption;
      } else {
         assumption = Assumption.create("stable prototype");
         if (!PROTOTYPE_ASSUMPTION_VAR_HANDLE.compareAndSet((JSSharedData)this, (Assumption)((Assumption)null), (Assumption)assumption)) {
            assumption = this.prototypeAssumption;
         } else {
            prototypeAssumptionsCreated.inc();
         }

         return assumption;
      }
   }

   void invalidatePrototypeAssumption() {
      Assumption assumption = this.prototypeAssumption;
      if (assumption != null && assumption != Assumption.NEVER_VALID) {
         assumption.invalidate();
         if (!PROTOTYPE_ASSUMPTION_VAR_HANDLE.compareAndSet((JSSharedData)this, (Assumption)assumption, (Assumption)Assumption.NEVER_VALID)) {
            assert this.prototypeAssumption == Assumption.NEVER_VALID;
         } else {
            prototypeAssumptionsRemoved.inc();
         }
      }
   }

   static {
      Lookup lookup = MethodHandles.lookup();

      try {
         PROTOTYPE_ASSUMPTION_VAR_HANDLE = lookup.findVarHandle(JSSharedData.class, "prototypeAssumption", Assumption.class);
      } catch (IllegalAccessException | NoSuchFieldException var2) {
         throw Errors.shouldNotReachHere(var2);
      }

      prototypeAssumptionsCreated = DebugCounter.create("Prototype assumptions created");
      prototypeAssumptionsRemoved = DebugCounter.create("Prototype assumptions removed");
   }
}

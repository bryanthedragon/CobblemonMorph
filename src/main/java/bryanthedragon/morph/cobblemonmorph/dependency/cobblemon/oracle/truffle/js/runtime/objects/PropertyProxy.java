package com.oracle.truffle.js.runtime.objects;

public abstract class PropertyProxy {
   public abstract Object get(JSDynamicObject store);

   public boolean set(JSDynamicObject store, Object value) {
      return true;
   }
}

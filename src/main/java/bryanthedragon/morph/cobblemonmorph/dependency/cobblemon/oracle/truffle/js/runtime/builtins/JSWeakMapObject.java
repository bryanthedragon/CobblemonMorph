package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.Map;

public final class JSWeakMapObject extends JSNonProxyObject {
   private final Map<JSObject, Object> weakHashMap;

   protected JSWeakMapObject(Shape shape, Map<JSObject, Object> weakHashMap) {
      super(shape);
      this.weakHashMap = weakHashMap;
   }

   public Map<JSObject, Object> getWeakHashMap() {
      return this.weakHashMap;
   }
}

package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.lang.ref.ReferenceQueue;
import java.util.List;

public final class JSFinalizationRegistryObject extends JSNonProxyObject {
   Object cleanupCallback;
   List<FinalizationRecord> cells;
   ReferenceQueue<Object> referenceQueue;

   protected JSFinalizationRegistryObject(Shape shape, Object cleanupCallback, List<FinalizationRecord> cells, ReferenceQueue<Object> referenceQueue) {
      super(shape);
      this.cleanupCallback = cleanupCallback;
      this.cells = cells;
      this.referenceQueue = referenceQueue;
   }

   public Object getCleanupCallback() {
      return this.cleanupCallback;
   }

   public List<FinalizationRecord> getCells() {
      return this.cells;
   }

   public ReferenceQueue<Object> getReferenceQueue() {
      return this.referenceQueue;
   }
}

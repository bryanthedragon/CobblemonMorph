package com.oracle.truffle.js.runtime.java;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JavaImporterObject extends JSNonProxyObject {
   private final Object[] imports;

   protected JavaImporterObject(Shape shape, Object[] imports) {
      super(shape);
      this.imports = imports;
   }

   public Object[] getImports() {
      return this.imports;
   }
}

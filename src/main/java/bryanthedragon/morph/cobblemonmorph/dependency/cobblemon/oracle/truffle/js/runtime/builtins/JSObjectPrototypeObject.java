package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantEmptyPrototypeArray;

public final class JSObjectPrototypeObject extends JSArrayBase {
   protected JSObjectPrototypeObject(Shape shape) {
      super(shape, ConstantEmptyPrototypeArray.createConstantEmptyPrototypeArray(), ScriptArray.EMPTY_OBJECT_ARRAY, null, 0L, 0, 0, 0, 0);
   }
}

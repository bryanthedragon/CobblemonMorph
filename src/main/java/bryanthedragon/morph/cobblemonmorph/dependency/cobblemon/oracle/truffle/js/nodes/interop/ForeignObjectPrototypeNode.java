package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GenerateUncached
@ImportStatic(JSConfig.class)
public abstract class ForeignObjectPrototypeNode extends JavaScriptBaseNode {
   public abstract JSDynamicObject execute(Object truffleObject);

   @Specialization(limit = "InteropLibraryLimit")
   public JSDynamicObject doTruffleObject(Object truffleObject, @CachedLibrary("truffleObject") InteropLibrary interop) {
      JSRealm realm = this.getRealm();
      if (interop.hasArrayElements(truffleObject)) {
         return realm.getForeignArrayPrototype();
      } else if (interop.isInstant(truffleObject)) {
         return realm.getForeignDatePrototype();
      } else if (interop.hasHashEntries(truffleObject)) {
         return realm.getForeignMapPrototype();
      } else if (interop.hasIterator(truffleObject)) {
         return realm.getForeignIterablePrototype();
      } else if (interop.isString(truffleObject)) {
         return realm.getForeignStringPrototype();
      } else if (interop.isNumber(truffleObject)) {
         return realm.getForeignNumberPrototype();
      } else if (interop.isBoolean(truffleObject)) {
         return realm.getForeignBooleanPrototype();
      } else {
         return !interop.isExecutable(truffleObject) && !interop.isInstantiable(truffleObject)
            ? realm.getForeignObjectPrototype()
            : realm.getForeignFunctionPrototype();
      }
   }

   public static ForeignObjectPrototypeNode create() {
      return ForeignObjectPrototypeNodeGen.create();
   }

   public static ForeignObjectPrototypeNode getUncached() {
      return ForeignObjectPrototypeNodeGen.getUncached();
   }
}

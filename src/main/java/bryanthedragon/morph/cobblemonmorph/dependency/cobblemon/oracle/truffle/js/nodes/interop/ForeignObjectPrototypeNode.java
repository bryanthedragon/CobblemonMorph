
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNodeGen;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GenerateUncached
@ImportStatic(value={JSConfig.class})
public abstract class ForeignObjectPrototypeNode
extends JavaScriptBaseNode {
    public abstract JSDynamicObject execute(Object var1);

    @Specialization(limit="InteropLibraryLimit")
    public JSDynamicObject doTruffleObject(Object truffleObject, @CachedLibrary(value="truffleObject") InteropLibrary interop) {
        JSRealm realm = this.getRealm();
        if (interop.hasArrayElements(truffleObject)) {
            return realm.getForeignArrayPrototype();
        }
        if (interop.isInstant(truffleObject)) {
            return realm.getForeignDatePrototype();
        }
        if (interop.hasHashEntries(truffleObject)) {
            return realm.getForeignMapPrototype();
        }
        if (interop.hasIterator(truffleObject)) {
            return realm.getForeignIterablePrototype();
        }
        if (interop.isString(truffleObject)) {
            return realm.getForeignStringPrototype();
        }
        if (interop.isNumber(truffleObject)) {
            return realm.getForeignNumberPrototype();
        }
        if (interop.isBoolean(truffleObject)) {
            return realm.getForeignBooleanPrototype();
        }
        if (interop.isExecutable(truffleObject) || interop.isInstantiable(truffleObject)) {
            return realm.getForeignFunctionPrototype();
        }
        return realm.getForeignObjectPrototype();
    }

    public static ForeignObjectPrototypeNode create() {
        return ForeignObjectPrototypeNodeGen.create();
    }

    public static ForeignObjectPrototypeNode getUncached() {
        return ForeignObjectPrototypeNodeGen.getUncached();
    }
}


package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@FunctionalInterface
public interface PrototypeSupplier {
   JSDynamicObject getIntrinsicDefaultProto(JSRealm realm);
}

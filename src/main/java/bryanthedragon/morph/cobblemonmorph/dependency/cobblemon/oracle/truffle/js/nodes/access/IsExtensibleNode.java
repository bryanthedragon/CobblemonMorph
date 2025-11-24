
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsExtensibleNodeGen;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSShape;

@GenerateUncached
@ImportStatic(value={JSShape.class})
public abstract class IsExtensibleNode
extends JavaScriptBaseNode {
    protected IsExtensibleNode() {
    }

    public abstract boolean executeBoolean(JSDynamicObject var1);

    @Specialization(guards={"getJSClass(cachedShape).usesOrdinaryIsExtensible()", "cachedShape.check(object)"}, limit="1")
    protected static boolean doCachedShape(JSDynamicObject object, @Cached(value="object.getShape()") Shape cachedShape, @Cached(value="isExtensible(cachedShape)") boolean result) {
        return result;
    }

    @Specialization(guards={"cachedJSClass.usesOrdinaryIsExtensible()", "cachedJSClass.isInstance(object)"}, limit="1", replaces={"doCachedShape"})
    protected static boolean doCachedJSClass(JSDynamicObject object, @Cached(value="getJSClass(object.getShape())") JSClass cachedJSClass, @Cached(value="createBinaryProfile()") @Cached.Shared(value="resultProfile") ConditionProfile resultProfile) {
        return resultProfile.profile(JSShape.isExtensible(object.getShape()));
    }

    @Specialization(replaces={"doCachedJSClass"})
    protected static boolean doUncached(JSDynamicObject object, @Cached(value="createBinaryProfile()") @Cached.Shared(value="resultProfile") ConditionProfile resultProfile) {
        return resultProfile.profile(JSObject.isExtensible(object));
    }

    public static IsExtensibleNode create() {
        return IsExtensibleNodeGen.create();
    }
}


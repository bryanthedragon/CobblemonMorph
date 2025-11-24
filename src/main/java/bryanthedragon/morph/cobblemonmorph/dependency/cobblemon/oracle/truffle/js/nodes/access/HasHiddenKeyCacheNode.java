
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.HasHiddenKeyCacheNodeGen;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class HasHiddenKeyCacheNode
extends JavaScriptBaseNode {
    protected final HiddenKey key;

    protected HasHiddenKeyCacheNode(HiddenKey key) {
        this.key = key;
    }

    public static HasHiddenKeyCacheNode create(HiddenKey key) {
        return HasHiddenKeyCacheNodeGen.create(key);
    }

    public abstract boolean executeHasHiddenKey(Object var1);

    @Specialization(guards={"cachedShape.check(object)"}, assumptions={"cachedShape.getValidAssumption()"}, limit="cacheLimit")
    protected static boolean doCached(JSDynamicObject object, @Cached(value="object.getShape()") Shape cachedShape, @Cached(value="doUncached(object)") boolean hasOwnProperty, @Cached(value="getPropertyCacheLimit()") int cacheLimit) {
        return hasOwnProperty;
    }

    protected int getPropertyCacheLimit() {
        return this.getLanguage().getJSContext().getPropertyCacheLimit();
    }

    @Specialization(guards={"isJSObject(object)"}, replaces={"doCached"})
    protected final boolean doUncached(JSDynamicObject object) {
        return JSDynamicObject.hasProperty(object, this.key);
    }

    @Specialization(guards={"!isJSObject(object)"})
    protected static boolean doNonObject(Object object) {
        return false;
    }

    public final HiddenKey getKey() {
        return this.key;
    }
}


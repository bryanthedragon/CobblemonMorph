
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsArrayNodeGen;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsObject;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

@ImportStatic(value={Kind.class, CompilerDirectives.class})
public abstract class IsArrayNode
extends JavaScriptBaseNode {
    final Kind kind;

    protected IsArrayNode(Kind kind) {
        this.kind = kind;
    }

    public abstract boolean execute(Object var1);

    @Specialization(guards={"kind == Array || kind == AnyArray"})
    protected final boolean doJSArray(JSArrayObject object) {
        return this.checkResult(object, true);
    }

    @Specialization(guards={"kind == FastArray || kind == FastOrTypedArray", "object.getShape() == cachedShape"}, limit="1")
    protected final boolean doJSFastArrayShape(JSArrayObject object, @Cached(value="getInitialArrayShape()") Shape cachedShape) {
        return this.checkResult(object, true);
    }

    @Specialization(guards={"kind == FastArray || kind == FastOrTypedArray"}, replaces={"doJSFastArrayShape"})
    protected final boolean doJSFastArray(JSArrayObject object) {
        return this.checkResult(object, JSArray.isJSFastArray(object));
    }

    @Specialization(guards={"kind == AnyArray || kind == FastOrTypedArray"})
    protected final boolean doJSTypedArray(JSTypedArrayObject object) {
        return this.checkResult(object, true);
    }

    @Specialization(guards={"kind == AnyArray || kind == FastOrTypedArray", "isJSArgumentsObject(object)"})
    protected final boolean doJSArgumentsObject(JSArgumentsObject object) {
        return this.checkResult(object, this.kind == Kind.AnyArray || this.kind == Kind.FastOrTypedArray && JSArgumentsArray.isJSFastArgumentsObject(object));
    }

    @Specialization(guards={"kind == AnyArray", "isJSObjectPrototype(object)"})
    protected final boolean doJSObjectPrototype(Object object) {
        return this.checkResult(object, true);
    }

    protected final boolean isArray(Object object) {
        if (this.kind == Kind.FastOrTypedArray) {
            return JSArray.isJSFastArray(object) || JSArgumentsArray.isJSFastArgumentsObject(object) || JSArrayBufferView.isJSArrayBufferView(object);
        }
        if (this.kind == Kind.FastArray) {
            return JSArray.isJSFastArray(object);
        }
        if (this.kind == Kind.Array) {
            return JSArray.isJSArray(object);
        }
        assert (this.kind == Kind.AnyArray);
        return JSObject.hasArray(object);
    }

    protected final Shape getInitialArrayShape() {
        JSRealm realm = this.getRealm();
        return realm.getContext().getArrayFactory().getShape(realm);
    }

    protected final boolean checkResult(Object object, boolean result) {
        assert (this.isArray(object) == result);
        return result;
    }

    @Specialization(guards={"kind == Array || kind == FastArray", "!isJSArray(object)"})
    protected final boolean doNotJSArray(Object object) {
        return this.checkResult(object, false);
    }

    @Specialization(guards={"isExact(object, cachedClass)"}, limit="1")
    protected final boolean doOtherCached(Object object, @Cached(value="object.getClass()") Class<?> cachedClass) {
        return this.checkResult(object, false);
    }

    @Specialization(replaces={"doOtherCached", "doJSArray", "doJSFastArray", "doJSTypedArray", "doJSArgumentsObject", "doJSObjectPrototype"})
    protected final boolean doOther(Object object) {
        return this.checkResult(object, this.isArray(object));
    }

    public static IsArrayNode createIsAnyArray() {
        return IsArrayNodeGen.create(Kind.AnyArray);
    }

    public static IsArrayNode createIsArray() {
        return IsArrayNodeGen.create(Kind.Array);
    }

    public static IsArrayNode createIsFastArray() {
        return IsArrayNodeGen.create(Kind.FastArray);
    }

    public static IsArrayNode createIsFastOrTypedArray() {
        return IsArrayNodeGen.create(Kind.FastOrTypedArray);
    }

    protected static enum Kind {
        FastOrTypedArray,
        FastArray,
        Array,
        AnyArray;

    }
}


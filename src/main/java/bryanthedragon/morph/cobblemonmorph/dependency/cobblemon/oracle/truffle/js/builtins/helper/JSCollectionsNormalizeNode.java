
package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.JSCollectionsNormalizeNodeGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSSet;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@ImportStatic(value={JSConfig.class})
@GenerateUncached
public abstract class JSCollectionsNormalizeNode
extends JavaScriptBaseNode {
    public abstract Object execute(Object var1);

    public static JSCollectionsNormalizeNode create() {
        return JSCollectionsNormalizeNodeGen.create();
    }

    @Specialization
    public int doInt(int value2) {
        return value2;
    }

    @Specialization
    public Object doDouble(double value2) {
        return JSSet.normalizeDouble(value2);
    }

    @Specialization
    public TruffleString doString(TruffleString value2) {
        return value2;
    }

    @Specialization
    public boolean doBoolean(boolean value2) {
        return value2;
    }

    @Specialization
    public Object doDynamicObject(JSDynamicObject object) {
        return object;
    }

    @Specialization
    public Symbol doSymbol(Symbol value2) {
        return value2;
    }

    @Specialization
    public BigInt doBigInt(BigInt bigInt) {
        return bigInt;
    }

    @Specialization(guards={"isForeignObject(object)"}, limit="InteropLibraryLimit")
    public Object doForeignObject(Object object, @CachedLibrary(value="object") InteropLibrary interop, @Cached(value="createBinaryProfile()") ConditionProfile primitiveProfile, @Cached(value="create()") JSCollectionsNormalizeNode nestedNormalizeNode) {
        Object primitive = JSInteropUtil.toPrimitiveOrDefault(object, null, interop, this);
        return primitiveProfile.profile(primitive == null) ? object : nestedNormalizeNode.execute(primitive);
    }
}


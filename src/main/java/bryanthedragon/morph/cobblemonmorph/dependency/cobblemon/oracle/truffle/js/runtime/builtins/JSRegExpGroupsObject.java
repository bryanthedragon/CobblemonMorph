
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSCopyableObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

public final class JSRegExpGroupsObject
extends JSNonProxyObject
implements JSCopyableObject {
    private Object regexResult;
    private TruffleString input;
    private boolean isIndices;

    protected JSRegExpGroupsObject(Shape shape, Object regexResult, TruffleString input, boolean isIndices) {
        super(shape);
        this.regexResult = regexResult;
        this.input = input;
        this.isIndices = isIndices;
    }

    public Object getRegexResult() {
        return this.regexResult;
    }

    public TruffleString getInputString() {
        return this.input;
    }

    public boolean isIndices() {
        return this.isIndices;
    }

    @Override
    public TruffleString getClassName() {
        return JSOrdinary.CLASS_NAME;
    }

    public static JSObject create(JSRealm realm, JSObjectFactory factory, Object regexResult, TruffleString input, boolean isIndices) {
        return factory.initProto(new JSRegExpGroupsObject(factory.getShape(realm), regexResult, input, isIndices), realm);
    }

    @Override
    protected JSObject copyWithoutProperties(Shape shape) {
        return new JSRegExpGroupsObject(shape, this.regexResult, this.input, this.isIndices);
    }
}


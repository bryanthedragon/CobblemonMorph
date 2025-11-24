
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferViewBase;
import com.oracle.truffle.js.runtime.builtins.JSDataView;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;

public final class JSDataViewObject
extends JSArrayBufferViewBase {
    protected JSDataViewObject(Shape shape, JSArrayBufferObject arrayBuffer, int length, int offset) {
        super(shape, arrayBuffer, length, offset);
    }

    @Override
    public TruffleString getClassName() {
        return JSDataView.CLASS_NAME;
    }

    public static JSArrayBufferObject getArrayBuffer(Object thisObj) {
        return ((JSDataViewObject)thisObj).getArrayBuffer();
    }

    public static int getLength(Object thisObj) {
        return ((JSDataViewObject)thisObj).length;
    }

    public static int getOffset(Object thisObj) {
        return ((JSDataViewObject)thisObj).offset;
    }

    public static JSDataViewObject create(JSRealm realm, JSObjectFactory factory, JSArrayBufferObject arrayBuffer, int length, int offset) {
        return factory.initProto(new JSDataViewObject(factory.getShape(realm), arrayBuffer, length, offset), realm);
    }
}


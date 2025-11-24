
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import java.util.Collections;
import java.util.List;

public abstract class AbstractJSClass
extends JSClass {
    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object thisObj, Object name, Node encapsulatingNode) {
        throw Errors.createTypeErrorCannotGetProperty(JavaScriptLanguage.getCurrentLanguage().getJSContext(), name, thisObj, false, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
        throw Errors.createTypeErrorCannotGetProperty(JavaScriptLanguage.getCurrentLanguage().getJSContext(), Strings.fromLong(index), thisObj, false, encapsulatingNode);
    }

    @Override
    public Object getMethodHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
        return this.getHelper(store, thisObj, key, encapsulatingNode);
    }

    @Override
    public Object getHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
        return this.getOwnHelper(store, thisObj, key, encapsulatingNode);
    }

    @Override
    public Object getHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
        return this.getOwnHelper(store, thisObj, index, encapsulatingNode);
    }

    @Override
    public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
        throw Errors.createTypeErrorNotAnObject(thisObj);
    }

    @Override
    public boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
        throw Errors.createTypeErrorNotAnObject(thisObj);
    }

    @Override
    public boolean hasProperty(JSDynamicObject thisObj, Object key) {
        return this.hasOwnProperty(thisObj, key);
    }

    @Override
    public boolean hasProperty(JSDynamicObject thisObj, long index) {
        return this.hasOwnProperty(thisObj, index);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        throw Errors.createTypeErrorCannotSetProperty(key, (Object)thisObj, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        throw Errors.createTypeErrorCannotSetProperty(Strings.fromLong(index), (Object)thisObj, encapsulatingNode);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
        throw Errors.createTypeErrorCannotDeletePropertyOf(key, thisObj);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
        throw Errors.createTypeErrorCannotDeletePropertyOf(Strings.fromLong(index), thisObj);
    }

    @Override
    public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        return Collections.emptyList();
    }

    @Override
    public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
        if (doThrow) {
            throw Errors.createTypeErrorCannotSetProperty(key, (Object)thisObj, null);
        }
        return false;
    }

    @Override
    public boolean preventExtensions(JSDynamicObject thisObj, boolean doThrow) {
        throw Errors.createTypeErrorNotAnObject(thisObj);
    }

    @Override
    public boolean isExtensible(JSDynamicObject thisObj) {
        throw Errors.createTypeErrorNotAnObject(thisObj);
    }

    @Override
    public boolean hasOnlyShapeProperties(JSDynamicObject obj) {
        return false;
    }

    @Override
    public boolean usesOrdinaryGetOwnProperty() {
        return false;
    }

    @Override
    public boolean usesOrdinaryIsExtensible() {
        return false;
    }

    @Override
    public JSDynamicObject getPrototypeOf(JSDynamicObject thisObj) {
        return Null.instance;
    }

    @Override
    public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
        return true;
    }

    @Override
    public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
        throw Errors.createTypeErrorNotAnObject(thisObj);
    }
}


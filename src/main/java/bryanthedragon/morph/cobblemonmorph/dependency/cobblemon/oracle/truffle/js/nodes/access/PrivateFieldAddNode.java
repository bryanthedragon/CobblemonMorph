
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PrivateFieldAddNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class PrivateFieldAddNode
extends JavaScriptBaseNode {
    protected final JSContext context;

    public static PrivateFieldAddNode create(JSContext context) {
        return PrivateFieldAddNodeGen.create(context);
    }

    protected PrivateFieldAddNode(JSContext context) {
        this.context = context;
    }

    public abstract void execute(Object var1, Object var2, Object var3);

    @Specialization(limit="3")
    void doFieldAdd(JSObject target, HiddenKey key, Object value2, @CachedLibrary(value="target") DynamicObjectLibrary access) {
        if (!Properties.containsKey(access, target, key)) {
            Properties.putWithFlags(access, target, key, value2, JSAttributes.getDefaultNotEnumerable());
        } else {
            this.duplicate(key);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private Object duplicate(HiddenKey key) {
        throw Errors.createTypeErrorCannotAddPrivateMember(key.getName(), this);
    }

    @CompilerDirectives.TruffleBoundary
    @Fallback
    void doFallback(Object target, Object key, Object value2) {
        throw Errors.createTypeErrorCannotSetProperty(key.toString(), target, (Node)this);
    }
}


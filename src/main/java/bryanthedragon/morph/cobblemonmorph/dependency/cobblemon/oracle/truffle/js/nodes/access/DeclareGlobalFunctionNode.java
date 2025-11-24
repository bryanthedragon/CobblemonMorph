
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.DeclareGlobalFunctionNodeGen;
import com.oracle.truffle.js.nodes.access.DeclareGlobalNode;
import com.oracle.truffle.js.nodes.access.IsExtensibleNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSGlobal;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class DeclareGlobalFunctionNode
extends DeclareGlobalNode {
    private final boolean configurable;
    @Node.Child
    protected JavaScriptNode valueNode;
    @Node.Child
    private JSGetOwnPropertyNode getOwnPropertyNode;
    @Node.Child
    private IsExtensibleNode isExtensibleNode = IsExtensibleNode.create();

    protected DeclareGlobalFunctionNode(TruffleString varName, boolean configurable, JavaScriptNode valueNode) {
        super(varName);
        this.configurable = configurable;
        this.valueNode = valueNode;
        this.getOwnPropertyNode = JSGetOwnPropertyNode.create(false);
    }

    public static DeclareGlobalFunctionNode create(TruffleString varName, boolean configurable, JavaScriptNode valueNode) {
        return DeclareGlobalFunctionNodeGen.create(varName, configurable, valueNode);
    }

    @Override
    public void verify(JSContext context, JSRealm realm) {
        super.verify(context, realm);
        JSDynamicObject globalObject = realm.getGlobalObject();
        PropertyDescriptor desc = this.getOwnPropertyNode.execute(globalObject, this.varName);
        if (desc == null) {
            if (!this.isExtensibleNode.executeBoolean(globalObject)) {
                this.errorProfile.enter();
                throw Errors.createTypeErrorGlobalObjectNotExtensible(this);
            }
        } else if (!(desc.getConfigurable() || desc.isDataDescriptor() && desc.getWritable() && desc.getEnumerable())) {
            this.errorProfile.enter();
            throw Errors.createTypeErrorCannotDeclareGlobalFunction(this.varName, this);
        }
    }

    @Override
    public final void executeVoid(VirtualFrame frame, JSContext context, JSRealm realm) {
        JSDynamicObject value2 = this.valueNode == null ? Undefined.instance : this.valueNode.execute(frame);
        JSDynamicObject globalObject = realm.getGlobalObject();
        PropertyDescriptor desc = this.getOwnPropertyNode.execute(globalObject, this.varName);
        this.executeVoid(globalObject, value2, desc, context);
    }

    protected abstract void executeVoid(JSDynamicObject var1, Object var2, PropertyDescriptor var3, JSContext var4);

    @Specialization(guards={"context.getPropertyCacheLimit() > 0", "isJSGlobalObject(globalObject)", "desc == null"})
    protected void doCached(JSDynamicObject globalObject, Object value2, PropertyDescriptor desc, JSContext context, @Cached(value="makeDefineOwnPropertyCache(context)") PropertySetNode cache) {
        cache.setValue(globalObject, value2);
    }

    @Specialization(replaces={"doCached"})
    protected void doUncached(JSDynamicObject globalObject, Object value2, PropertyDescriptor desc, JSContext context) {
        if (this.valueNode == null && desc == null && JSGlobal.isJSGlobalObject(globalObject)) {
            JSObjectUtil.putDeclaredDataProperty(context, globalObject, this.varName, value2, this.getAttributeFlags());
        } else if (desc == null || desc.getConfigurable()) {
            JSObject.defineOwnProperty(globalObject, this.varName, PropertyDescriptor.createData(value2, true, true, this.configurable), true);
        } else {
            JSObject.defineOwnProperty(globalObject, this.varName, PropertyDescriptor.createData(value2), true);
        }
    }

    private int getAttributeFlags() {
        return this.configurable ? JSAttributes.configurableEnumerableWritable() : JSAttributes.notConfigurableEnumerableWritable();
    }

    protected final PropertySetNode makeDefineOwnPropertyCache(JSContext context) {
        return PropertySetNode.createImpl(this.varName, false, context, true, true, this.getAttributeFlags(), this.valueNode == null);
    }

    @Override
    public boolean isGlobalFunctionDeclaration() {
        return true;
    }

    @Override
    protected DeclareGlobalNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return DeclareGlobalFunctionNode.create(this.varName, this.configurable, JavaScriptNode.cloneUninitialized(this.valueNode, materializedTags));
    }
}


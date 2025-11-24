
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.JSProxyPropertySetNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSUncheckedProxyHandlerObject;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

@NodeInfo(cost=NodeCost.NONE)
public abstract class JSProxyPropertySetNode
extends JavaScriptBaseNode {
    private final boolean isStrict;
    @Node.Child
    private JSFunctionCallNode call;
    @Node.Child
    private JSToBooleanNode toBoolean;
    @Node.Child
    protected GetMethodNode trapGet;
    @Node.Child
    private JSToPropertyKeyNode toPropertyKeyNode;
    @Node.Child
    private InteropLibrary interopNode;
    @Node.Child
    private ExportValueNode exportValueNode;
    private final BranchProfile errorBranch = BranchProfile.create();

    protected JSProxyPropertySetNode(JSContext context, boolean isStrict) {
        this.call = JSFunctionCallNode.createCall();
        this.trapGet = GetMethodNode.create(context, JSProxy.SET);
        this.toBoolean = JSToBooleanNode.create();
        this.isStrict = isStrict;
    }

    public abstract boolean executeWithReceiverAndValue(Object var1, Object var2, Object var3, Object var4);

    public abstract boolean executeWithReceiverAndValueInt(Object var1, Object var2, int var3, Object var4);

    public static JSProxyPropertySetNode create(JSContext context, boolean isStrict) {
        return JSProxyPropertySetNodeGen.create(context, isStrict);
    }

    @Specialization
    protected boolean doGeneric(JSDynamicObject proxy, Object receiver, Object value2, Object key, @Cached(value="createBinaryProfile()") ConditionProfile hasTrap, @Cached JSClassProfile targetClassProfile) {
        assert (JSProxy.isJSProxy(proxy));
        assert (!(key instanceof HiddenKey));
        Object propertyKey = this.toPropertyKey(key);
        JSDynamicObject handler = JSProxy.getHandlerChecked(proxy, this.errorBranch);
        Object target = JSProxy.getTarget(proxy);
        Object trapFun = this.trapGet.executeWithTarget(handler);
        if (hasTrap.profile(trapFun == Undefined.instance)) {
            if (JSDynamicObject.isJSDynamicObject(target)) {
                return JSObject.setWithReceiver((JSDynamicObject)target, propertyKey, value2, receiver, this.isStrict, targetClassProfile, (Node)this);
            }
            this.truffleWrite(target, propertyKey, value2);
            return true;
        }
        Object trapResult = this.call.executeCall(JSArguments.create(handler, trapFun, target, propertyKey, value2, receiver));
        boolean booleanTrapResult = this.toBoolean.executeBoolean(trapResult);
        if (!booleanTrapResult) {
            this.errorBranch.enter();
            if (this.isStrict) {
                throw Errors.createTypeErrorTrapReturnedFalsish(JSProxy.SET, propertyKey);
            }
            return false;
        }
        if (handler instanceof JSUncheckedProxyHandlerObject) {
            return true;
        }
        return JSProxy.checkProxySetTrapInvariants(proxy, propertyKey, value2);
    }

    private void truffleWrite(Object obj, Object key, Object value2) {
        InteropLibrary interop = this.interopNode;
        ExportValueNode exportValue = this.exportValueNode;
        if (interop == null || exportValue == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.interopNode = interop = this.insert(InteropLibrary.getFactory().createDispatched(5));
            this.exportValueNode = exportValue = this.insert(ExportValueNode.create());
        }
        JSInteropUtil.writeMember(obj, key, value2, interop, exportValue, this);
    }

    Object toPropertyKey(Object key) {
        if (this.toPropertyKeyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toPropertyKeyNode = this.insert(JSToPropertyKeyNode.create());
        }
        return this.toPropertyKeyNode.execute(key);
    }
}


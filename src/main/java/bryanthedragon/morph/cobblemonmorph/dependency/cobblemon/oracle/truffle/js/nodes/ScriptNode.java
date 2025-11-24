
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;

public final class ScriptNode {
    private final JSContext context;
    private final JSFunctionData functionData;
    private final RootCallTarget callTarget;

    private ScriptNode(JSContext context, JSFunctionData functionData, RootCallTarget callTarget) {
        this.context = context;
        this.functionData = functionData;
        this.callTarget = callTarget;
    }

    public static ScriptNode fromFunctionRoot(JSContext context, FunctionRootNode root) {
        return ScriptNode.fromFunctionData(context, root.getFunctionData());
    }

    public static ScriptNode fromFunctionData(JSContext context, JSFunctionData functionData) {
        return new ScriptNode(context, functionData, (RootCallTarget)functionData.getCallTarget());
    }

    public Object run(JSRealm realm) {
        return this.run(this.argumentsToRunWithThisObject(realm, realm.getGlobalObject()));
    }

    public Object[] argumentsToRun(JSRealm realm) {
        return this.argumentsToRunWithThisObject(realm, realm.getGlobalObject());
    }

    public Object[] argumentsToRunWithThisObject(JSRealm realm, Object thisObj) {
        JSFunctionObject functionObj = JSFunction.create(realm, this.functionData);
        return JSArguments.createZeroArg(thisObj, functionObj);
    }

    public Object[] argumentsToRunWithArguments(JSRealm realm, Object[] args) {
        return this.argumentsToRunWithThisObjectWithArguments(realm, realm.getGlobalObject(), args);
    }

    public Object[] argumentsToRunWithThisObjectWithArguments(JSRealm realm, Object thisObj, Object[] args) {
        JSFunctionObject functionObj = JSFunction.create(realm, this.functionData);
        return JSArguments.create(thisObj, functionObj, args);
    }

    public Object runEval(IndirectCallNode callNode, JSRealm realm, Object thisObj, MaterializedFrame materializedFrame) {
        JSFunctionObject functionObj = JSFunction.create(realm, this.getFunctionData(), materializedFrame);
        return callNode.call(this.callTarget, JSArguments.createZeroArg(thisObj, functionObj));
    }

    public Object runEval(IndirectCallNode callNode, JSRealm realm) {
        return this.runEval(callNode, realm, realm.getGlobalObject(), JSFrameUtil.NULL_MATERIALIZED_FRAME);
    }

    public Object run(Object[] args) {
        return this.callTarget.call(args);
    }

    public JSContext getContext() {
        return this.context;
    }

    public RootNode getRootNode() {
        return this.callTarget.getRootNode();
    }

    public RootCallTarget getCallTarget() {
        return this.callTarget;
    }

    public JSFunctionData getFunctionData() {
        return this.functionData;
    }
}



package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class InitFunctionNode
extends JavaScriptBaseNode {
    private final JSFunctionData functionData;
    private final JSContext context;
    @Node.Child
    private DynamicObjectLibrary setPrototypeNode;
    @Node.Child
    private DynamicObjectLibrary setLengthNode;
    @Node.Child
    private DynamicObjectLibrary setNameNode;
    @Node.Child
    private DynamicObjectLibrary setArgumentsNode;
    @Node.Child
    private DynamicObjectLibrary setCallerNode;
    private final int prototypeFlags;
    private final int lengthFlags;
    private final int nameFlags;
    private final int argumentsCallerFlags;
    private final boolean strictProperties;

    protected InitFunctionNode(JSFunctionData functionData, JSContext context, boolean strictProperties, boolean isConstructor, boolean isBound, boolean isGenerator, boolean prototypeNotWritable) {
        boolean hasPrototype;
        this.functionData = functionData;
        this.context = context;
        this.strictProperties = strictProperties;
        boolean bl = hasPrototype = isConstructor && !isBound || isGenerator;
        if (hasPrototype) {
            int prototypeAttributes = prototypeNotWritable ? JSAttributes.notConfigurableNotEnumerableNotWritable() : JSAttributes.notConfigurableNotEnumerableWritable();
            this.prototypeFlags = prototypeAttributes | 0x10;
            this.setPrototypeNode = JSObjectUtil.createDispatched(JSObject.PROTOTYPE);
        } else {
            this.prototypeFlags = 0;
        }
        int lengthAttributes = context.getEcmaScriptVersion() < 6 ? JSAttributes.notConfigurableNotEnumerableNotWritable() : JSAttributes.configurableNotEnumerableNotWritable();
        this.lengthFlags = lengthAttributes | 0x10;
        this.setLengthNode = JSObjectUtil.createDispatched(JSFunction.LENGTH);
        int nameAttributes = JSAttributes.configurableNotEnumerableNotWritable();
        this.nameFlags = nameAttributes | 0x10;
        this.setNameNode = JSObjectUtil.createDispatched(JSFunction.NAME);
        boolean argumentsCaller = false;
        int argumentsCallerAttributes = 0;
        if (context.getEcmaScriptVersion() >= 6) {
            if (!strictProperties) {
                argumentsCaller = true;
                argumentsCallerAttributes = JSAttributes.notConfigurableNotEnumerableNotWritable();
            }
        } else if (strictProperties) {
            argumentsCaller = true;
            argumentsCallerAttributes = JSAttributes.notConfigurableNotEnumerable() | 8;
        }
        if (argumentsCaller) {
            this.setArgumentsNode = JSObjectUtil.createDispatched(JSFunction.ARGUMENTS);
            this.setCallerNode = JSObjectUtil.createDispatched(JSFunction.CALLER);
        }
        this.argumentsCallerFlags = argumentsCallerAttributes;
    }

    protected InitFunctionNode(JSFunctionData functionData) {
        this(functionData, functionData.getContext(), functionData.hasStrictFunctionProperties(), functionData.isConstructor(), functionData.isBound(), functionData.isGenerator(), functionData.isPrototypeNotWritable());
        assert (!functionData.isBuiltin());
    }

    public static InitFunctionNode create(JSContext context, boolean strictProperties, boolean isConstructor, boolean isBound, boolean isGenerator, boolean prototypeNotWritable) {
        return new InitFunctionNode(null, context, strictProperties, isConstructor, isBound, isGenerator, prototypeNotWritable);
    }

    public static InitFunctionNode create(JSFunctionData functionData) {
        return new InitFunctionNode(functionData);
    }

    public final JSFunctionObject execute(JSFunctionObject function) {
        return this.execute(function, this.functionData.getLength(), this.functionData.getName());
    }

    public final JSFunctionObject execute(JSFunctionObject function, JSFunctionData functionData) {
        return this.execute(function, functionData.getLength(), functionData.getName());
    }

    public final JSFunctionObject execute(JSFunctionObject function, int length, TruffleString name) {
        Properties.putConstant(this.setLengthNode, function, JSFunction.LENGTH, JSFunction.LENGTH_PROXY, this.lengthFlags);
        assert (JSFunction.getFunctionData(function).isBound() || length == (Integer)JSFunction.LENGTH_PROXY.get(function));
        Properties.putConstant(this.setNameNode, function, JSFunction.NAME, JSFunction.NAME_PROXY, this.nameFlags);
        assert (JSFunction.getFunctionData(function).isBound() || Strings.equals(name, (TruffleString)JSFunction.NAME_PROXY.get(function)));
        if (this.setPrototypeNode != null) {
            Properties.putConstant(this.setPrototypeNode, function, JSObject.PROTOTYPE, JSFunction.PROTOTYPE_PROXY, this.prototypeFlags);
        }
        if (this.context.getEcmaScriptVersion() >= 6) {
            if (!this.strictProperties) {
                if (this.context.isOptionV8CompatibilityMode()) {
                    Properties.putConstant(this.setArgumentsNode, function, JSFunction.ARGUMENTS, JSFunction.ARGUMENTS_PROXY, this.argumentsCallerFlags | 0x10);
                    Properties.putConstant(this.setCallerNode, function, JSFunction.CALLER, JSFunction.CALLER_PROXY, this.argumentsCallerFlags | 0x10);
                } else {
                    Properties.putConstant(this.setArgumentsNode, function, JSFunction.ARGUMENTS, Undefined.instance, this.argumentsCallerFlags);
                    Properties.putConstant(this.setCallerNode, function, JSFunction.CALLER, Undefined.instance, this.argumentsCallerFlags);
                }
            }
        } else if (this.strictProperties) {
            Accessor throwerAccessor = JSFunction.getRealm(function).getThrowerAccessor();
            Properties.putWithFlags(this.setArgumentsNode, function, JSFunction.ARGUMENTS, throwerAccessor, this.argumentsCallerFlags);
            Properties.putWithFlags(this.setCallerNode, function, JSFunction.CALLER, throwerAccessor, this.argumentsCallerFlags);
        }
        return function;
    }
}


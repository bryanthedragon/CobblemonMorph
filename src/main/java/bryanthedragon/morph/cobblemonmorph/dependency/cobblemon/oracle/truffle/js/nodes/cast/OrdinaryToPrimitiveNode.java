
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsPrimitiveNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.OrdinaryToPrimitiveNodeGen;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

@ImportStatic(value={JSConfig.class})
public abstract class OrdinaryToPrimitiveNode
extends JavaScriptBaseNode {
    private final JSToPrimitiveNode.Hint hint;
    private final ConditionProfile toStringFunctionProfile = ConditionProfile.createBinaryProfile();
    private final ConditionProfile valueOfFunctionProfile = ConditionProfile.createBinaryProfile();
    @Node.Child
    private PropertyGetNode getToStringNode;
    @Node.Child
    private PropertyGetNode getValueOfNode;
    @Node.Child
    private IsCallableNode isCallableNode;
    @Node.Child
    private JSFunctionCallNode callToStringNode;
    @Node.Child
    private JSFunctionCallNode callValueOfNode;
    @Node.Child
    private IsPrimitiveNode isPrimitiveNode;
    @Node.Child
    private ForeignObjectPrototypeNode foreignObjectPrototypeNode;

    protected OrdinaryToPrimitiveNode(JSToPrimitiveNode.Hint hint) {
        assert (hint == JSToPrimitiveNode.Hint.String || hint == JSToPrimitiveNode.Hint.Number);
        this.hint = hint;
        this.isCallableNode = IsCallableNode.create();
        this.isPrimitiveNode = IsPrimitiveNode.create();
    }

    public abstract Object execute(Object var1);

    @Specialization
    protected Object doObject(JSObject object) {
        if (this.hint == JSToPrimitiveNode.Hint.String) {
            return this.doHintString(object);
        }
        assert (this.hint == JSToPrimitiveNode.Hint.Number);
        return this.doHintNumber(object);
    }

    @Specialization(guards={"isForeignObject(object)"}, limit="InteropLibraryLimit")
    protected Object doForeign(Object object, @CachedLibrary(value="object") InteropLibrary interop) {
        if (this.hint == JSToPrimitiveNode.Hint.String) {
            return this.doForeignHintString(object, interop);
        }
        assert (this.hint == JSToPrimitiveNode.Hint.Number);
        return this.doForeignHintNumber(object, interop);
    }

    public static OrdinaryToPrimitiveNode createHintString() {
        return OrdinaryToPrimitiveNode.create(JSToPrimitiveNode.Hint.String);
    }

    public static OrdinaryToPrimitiveNode createHintNumber() {
        return OrdinaryToPrimitiveNode.create(JSToPrimitiveNode.Hint.Number);
    }

    public static OrdinaryToPrimitiveNode create(JSToPrimitiveNode.Hint hint) {
        return OrdinaryToPrimitiveNodeGen.create(hint);
    }

    private Object doHintString(JSObject object) {
        Object result;
        Object result2;
        Object toString = this.getToString().getValue(object);
        if (this.toStringFunctionProfile.profile(this.isCallableNode.executeBoolean(toString)) && this.isPrimitiveNode.executeBoolean(result2 = this.callToStringNode.executeCall(JSArguments.createZeroArg(object, toString)))) {
            return result2;
        }
        Object valueOf = this.getValueOf().getValue(object);
        if (this.valueOfFunctionProfile.profile(this.isCallableNode.executeBoolean(valueOf)) && this.isPrimitiveNode.executeBoolean(result = this.callValueOfNode.executeCall(JSArguments.createZeroArg(object, valueOf)))) {
            return result;
        }
        throw Errors.createTypeErrorCannotConvertToPrimitiveValue(this);
    }

    private Object doHintNumber(JSObject object) {
        Object result;
        Object result2;
        assert (JSGuards.isJSObject(object));
        Object valueOf = this.getValueOf().getValue(object);
        if (this.valueOfFunctionProfile.profile(this.isCallableNode.executeBoolean(valueOf)) && this.isPrimitiveNode.executeBoolean(result2 = this.callValueOfNode.executeCall(JSArguments.createZeroArg(object, valueOf)))) {
            return result2;
        }
        Object toString = this.getToString().getValue(object);
        if (this.toStringFunctionProfile.profile(this.isCallableNode.executeBoolean(toString)) && this.isPrimitiveNode.executeBoolean(result = this.callToStringNode.executeCall(JSArguments.createZeroArg(object, toString)))) {
            return result;
        }
        throw Errors.createTypeErrorCannotConvertToPrimitiveValue(this);
    }

    private Object doForeignHintString(Object object, InteropLibrary interop) {
        Object result = this.tryInvokeForeignMethod(object, interop, Strings.TO_STRING_JLS);
        if (result != null) {
            return result;
        }
        JSDynamicObject proto = this.getForeignObjectPrototype(object);
        Object func = this.getToString().getValue(proto);
        if (this.toStringFunctionProfile.profile(this.isCallableNode.executeBoolean(func)) && this.isPrimitiveNode.executeBoolean(result = this.callToStringNode.executeCall(JSArguments.createZeroArg(object, func)))) {
            return result;
        }
        result = this.tryInvokeForeignMethod(object, interop, Strings.VALUE_OF_JLS);
        if (result != null) {
            return result;
        }
        func = this.getValueOf().getValue(proto);
        if (this.valueOfFunctionProfile.profile(this.isCallableNode.executeBoolean(func)) && this.isPrimitiveNode.executeBoolean(result = this.callValueOfNode.executeCall(JSArguments.createZeroArg(object, func)))) {
            return result;
        }
        throw Errors.createTypeErrorCannotConvertToPrimitiveValue(this);
    }

    private Object doForeignHintNumber(Object object, InteropLibrary interop) {
        Object result = this.tryInvokeForeignMethod(object, interop, Strings.VALUE_OF_JLS);
        if (result != null) {
            return result;
        }
        JSDynamicObject proto = this.getForeignObjectPrototype(object);
        Object func = this.getValueOf().getValue(proto);
        if (this.valueOfFunctionProfile.profile(this.isCallableNode.executeBoolean(func)) && this.isPrimitiveNode.executeBoolean(result = this.callValueOfNode.executeCall(JSArguments.createZeroArg(object, func)))) {
            return result;
        }
        result = this.tryInvokeForeignMethod(object, interop, Strings.TO_STRING_JLS);
        if (result != null) {
            return result;
        }
        func = this.getToString().getValue(proto);
        if (this.toStringFunctionProfile.profile(this.isCallableNode.executeBoolean(func)) && this.isPrimitiveNode.executeBoolean(result = this.callToStringNode.executeCall(JSArguments.createZeroArg(object, func)))) {
            return result;
        }
        throw Errors.createTypeErrorCannotConvertToPrimitiveValue(this);
    }

    private Object tryInvokeForeignMethod(Object object, InteropLibrary interop, String methodName) {
        if (interop.hasMembers(object) && interop.isMemberInvocable(object, methodName)) {
            if (OrdinaryToPrimitiveNode.isJavaArray(object, interop)) {
                return null;
            }
            try {
                Object result = JSRuntime.importValue(interop.invokeMember(object, methodName, new Object[0]));
                if (this.isPrimitiveNode.executeBoolean(result)) {
                    return result;
                }
            }
            catch (InteropException interopException) {
                // empty catch block
            }
        }
        return null;
    }

    public static boolean isJavaArray(Object object, InteropLibrary interop) {
        return JSRealm.get(interop).getEnv().isHostObject(object) && interop.hasArrayElements(object) && interop.isMemberReadable(object, "length");
    }

    private PropertyGetNode getToString() {
        if (this.getToStringNode == null || this.callToStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getToStringNode = this.insert(PropertyGetNode.createGetMethod(Strings.TO_STRING, this.getLanguage().getJSContext()));
            this.callToStringNode = this.insert(JSFunctionCallNode.createCall());
        }
        return this.getToStringNode;
    }

    private PropertyGetNode getValueOf() {
        if (this.getValueOfNode == null || this.callValueOfNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getValueOfNode = this.insert(PropertyGetNode.createGetMethod(Strings.VALUE_OF, this.getLanguage().getJSContext()));
            this.callValueOfNode = this.insert(JSFunctionCallNode.createCall());
        }
        return this.getValueOfNode;
    }

    private JSDynamicObject getForeignObjectPrototype(Object truffleObject) {
        assert (JSRuntime.isForeignObject(truffleObject));
        if (this.foreignObjectPrototypeNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.foreignObjectPrototypeNode = this.insert(ForeignObjectPrototypeNode.create());
        }
        return this.foreignObjectPrototypeNode.execute(truffleObject);
    }
}



package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.nodes.unary.TypeOfNodeGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@NodeInfo(shortName="typeof")
@ImportStatic(value={JSRuntime.class, JSConfig.class})
public abstract class TypeOfNode
extends JSUnaryNode {
    protected static final int MAX_CLASSES = 3;

    protected TypeOfNode(JavaScriptNode operand) {
        super(operand);
    }

    public static TypeOfNode create(JavaScriptNode operand) {
        return TypeOfNodeGen.create(operand);
    }

    public static TypeOfNode create() {
        return TypeOfNode.create(null);
    }

    public abstract TruffleString executeString(Object var1);

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        return tag == JSTags.UnaryOperationTag.class ? true : super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        return JSTags.createNodeObjectDescriptor("operator", this.getClass().getAnnotation(NodeInfo.class).shortName());
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == TruffleString.class;
    }

    @Specialization
    protected TruffleString doString(TruffleString operand) {
        return JSString.TYPE_NAME;
    }

    @Specialization
    protected TruffleString doInt(int operand) {
        return JSNumber.TYPE_NAME;
    }

    @Specialization
    protected TruffleString doDouble(double operand) {
        return JSNumber.TYPE_NAME;
    }

    @Specialization
    protected TruffleString doBoolean(boolean operand) {
        return JSBoolean.TYPE_NAME;
    }

    @Specialization
    protected TruffleString doBigInt(BigInt operand) {
        return JSBigInt.TYPE_NAME;
    }

    @Specialization(guards={"isJSNull(operand)"})
    protected TruffleString doNull(Object operand) {
        return Null.TYPE_NAME;
    }

    @Specialization(guards={"isUndefined(operand)"})
    protected TruffleString doUndefined(Object operand) {
        return Undefined.TYPE_NAME;
    }

    @Specialization(guards={"isJSFunction(operand)"})
    protected TruffleString doJSFunction(Object operand) {
        return JSFunction.TYPE_NAME;
    }

    @Specialization(guards={"isJSDynamicObject(operand)", "!isJSFunction(operand)", "!isUndefined(operand)", "!isJSProxy(operand)"})
    protected TruffleString doJSObjectOnly(Object operand) {
        return JSOrdinary.TYPE_NAME;
    }

    @Specialization
    protected TruffleString doJSProxy(JSProxyObject operand, @Cached(value="create()") TypeOfNode typeofNode) {
        Object target = JSProxy.getTargetNonProxy(operand);
        return typeofNode.executeString(target);
    }

    @Specialization
    protected TruffleString doSymbol(Symbol operand) {
        return JSSymbol.TYPE_NAME;
    }

    @Specialization(guards={"isForeignObject(operand)"}, limit="InteropLibraryLimit")
    protected TruffleString doTruffleObject(Object operand, @CachedLibrary(value="operand") InteropLibrary interop) {
        TruffleLanguage.Env env;
        if (this.getLanguage().getJSContext().isOptionNashornCompatibilityMode() && (env = this.getRealm().getEnv()).isHostSymbol(operand)) {
            return JSFunction.TYPE_NAME;
        }
        if (interop.isBoolean(operand)) {
            return JSBoolean.TYPE_NAME;
        }
        if (interop.isString(operand)) {
            return JSString.TYPE_NAME;
        }
        if (interop.isNumber(operand)) {
            return JSNumber.TYPE_NAME;
        }
        if (interop.isExecutable(operand) || interop.isInstantiable(operand)) {
            return JSFunction.TYPE_NAME;
        }
        return JSOrdinary.TYPE_NAME;
    }

    @Fallback
    protected TruffleString doJavaObject(Object operand) {
        assert (operand != null);
        return operand instanceof Number ? JSNumber.TYPE_NAME : JSOrdinary.TYPE_NAME;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return TypeOfNodeGen.create(TypeOfNode.cloneUninitialized(this.getOperand(), materializedTags));
    }
}



package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSEqualNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSIsNullOrUndefinedNodeGen;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.Set;

@ImportStatic(value={CompilerDirectives.class, JSConfig.class})
public abstract class JSIsNullOrUndefinedNode
extends JSUnaryNode {
    private final boolean isLeft;
    private final boolean isUndefined;

    protected JSIsNullOrUndefinedNode(JavaScriptNode operand, boolean isUndefined, boolean isLeft) {
        super(operand);
        this.isUndefined = isUndefined;
        this.isLeft = isLeft;
    }

    public abstract boolean executeBoolean(Object var1);

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.BinaryOperationTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (materializedTags.contains(JSTags.BinaryOperationTag.class)) {
            JSConstantNode constantNode = this.isUndefined ? JSConstantNode.createUndefined() : JSConstantNode.createNull();
            JavaScriptNode newOperand = JSIsNullOrUndefinedNode.cloneUninitialized(this.getOperand(), materializedTags);
            JavaScriptNode left = this.isLeft ? constantNode : newOperand;
            JavaScriptNode right = this.isLeft ? newOperand : constantNode;
            JavaScriptNode materialized = JSEqualNode.createUnoptimized(left, right);
            JSIsNullOrUndefinedNode.transferSourceSectionAddExpressionTag(this, constantNode);
            JSIsNullOrUndefinedNode.transferSourceSectionAndTags(this, materialized);
            return materialized;
        }
        return this;
    }

    @Specialization(guards={"isJSNull(operand)"})
    protected static boolean doNull(Object operand) {
        return true;
    }

    @Specialization(guards={"isUndefined(operand)"})
    protected static boolean doUndefined(Object operand) {
        return true;
    }

    @Specialization
    protected static boolean doSymbol(Symbol operand) {
        return false;
    }

    @Specialization
    protected static boolean doTString(TruffleString operand) {
        return false;
    }

    @Specialization
    protected static boolean doSafeInteger(SafeInteger operand) {
        return false;
    }

    @Specialization
    protected static boolean doBigInt(BigInt operand) {
        return false;
    }

    @Specialization(guards={"cachedClass != null", "isExact(object, cachedClass)"}, limit="1")
    protected static boolean doJSObjectCached(Object object, @Cached(value="getClassIfJSObject(object)") Class<?> cachedClass) {
        assert (!JSGuards.isNullOrUndefined(object));
        return false;
    }

    @Specialization(guards={"isJSObject(object)"}, replaces={"doJSObjectCached"})
    protected static boolean doJSObject(Object object) {
        assert (!JSGuards.isNullOrUndefined(object));
        return false;
    }

    @Specialization(guards={"!isJSDynamicObject(operand)"}, limit="InteropLibraryLimit")
    protected boolean doJSValueOrForeign(Object operand, @CachedLibrary(value="operand") InteropLibrary interop) {
        assert (JSRuntime.isJSPrimitive(operand) || JSRuntime.isForeignObject(operand));
        return interop.isNull(operand);
    }

    public static JSIsNullOrUndefinedNode createFromEquals(JavaScriptNode left, JavaScriptNode right) {
        assert (JSIsNullOrUndefinedNode.isNullOrUndefined(left) || JSIsNullOrUndefinedNode.isNullOrUndefined(right));
        boolean isLeft = JSIsNullOrUndefinedNode.isNullOrUndefined(left);
        JavaScriptNode operand = isLeft ? right : left;
        JavaScriptNode constant = isLeft ? left : right;
        boolean isUndefined = constant instanceof JSConstantNode.JSConstantUndefinedNode;
        return JSIsNullOrUndefinedNodeGen.create(operand, isUndefined, isLeft);
    }

    public static JSIsNullOrUndefinedNode create(JavaScriptNode value2) {
        return JSIsNullOrUndefinedNodeGen.create(value2, false, false);
    }

    public static JSIsNullOrUndefinedNode create() {
        return JSIsNullOrUndefinedNodeGen.create(null, true, true);
    }

    private static boolean isNullOrUndefined(JavaScriptNode node) {
        return node instanceof JSConstantNode.JSConstantUndefinedNode || node instanceof JSConstantNode.JSConstantNullNode;
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == Boolean.TYPE;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSIsNullOrUndefinedNodeGen.create(JSIsNullOrUndefinedNode.cloneUninitialized(this.getOperand(), materializedTags), this.isUndefined, this.isLeft);
    }
}


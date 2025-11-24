
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class JSConstantNode
extends JavaScriptNode
implements RepeatableNode {
    public static JSConstantNode create(Object value2) {
        assert (!(value2 instanceof Long) && !(value2 instanceof BigInteger));
        if (value2 instanceof Integer) {
            return JSConstantNode.createInt((Integer)value2);
        }
        if (value2 instanceof Double) {
            double doubleValue = (Double)value2;
            if (JSRuntime.doubleIsRepresentableAsInt(doubleValue)) {
                return JSConstantNode.createInt((int)doubleValue);
            }
            return JSConstantNode.createDouble(doubleValue);
        }
        if (value2 instanceof Boolean) {
            return JSConstantNode.createBoolean((Boolean)value2);
        }
        if (value2 instanceof TruffleString) {
            return JSConstantNode.createString((TruffleString)value2);
        }
        if (value2 == Null.instance) {
            return JSConstantNode.createNull();
        }
        if (value2 == Undefined.instance) {
            return JSConstantNode.createUndefined();
        }
        if (value2 instanceof BigInt) {
            return JSConstantNode.createBigInt((BigInt)value2);
        }
        if (value2 instanceof SafeInteger) {
            return JSConstantNode.createSafeInteger((SafeInteger)value2);
        }
        if (JSDynamicObject.isJSDynamicObject(value2)) {
            return new JSConstantJSObjectNode((JSDynamicObject)value2);
        }
        assert (!(value2 instanceof String));
        return new JSConstantObjectNode(value2);
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.LiteralTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor();
        if (this instanceof JSConstantDoubleNode || this instanceof JSConstantIntegerNode || this instanceof JSConstantSafeIntegerNode) {
            descriptor.addProperty("literalType", (Object)JSTags.LiteralTag.Type.NumericLiteral.name());
        } else if (this instanceof JSConstantBigIntNode) {
            descriptor.addProperty("literalType", (Object)JSTags.LiteralTag.Type.BigIntLiteral.name());
        } else if (this instanceof JSConstantBooleanNode) {
            descriptor.addProperty("literalType", (Object)JSTags.LiteralTag.Type.BooleanLiteral.name());
        } else if (this instanceof JSConstantStringNode) {
            descriptor.addProperty("literalType", (Object)JSTags.LiteralTag.Type.StringLiteral.name());
        } else if (this instanceof JSConstantNullNode) {
            descriptor.addProperty("literalType", (Object)JSTags.LiteralTag.Type.NullLiteral.name());
        } else if (this instanceof JSConstantUndefinedNode) {
            descriptor.addProperty("literalType", (Object)JSTags.LiteralTag.Type.UndefinedLiteral.name());
        } else if (this instanceof JSConstantObjectNode || this instanceof JSConstantJSObjectNode) {
            descriptor.addProperty("literalType", (Object)JSTags.LiteralTag.Type.ObjectLiteral.name());
        }
        return descriptor;
    }

    public static JSConstantNode createUndefined() {
        return new JSConstantUndefinedNode();
    }

    public static JSConstantNode createNull() {
        return new JSConstantNullNode();
    }

    public static JSConstantNode createInt(int value2) {
        return new JSConstantIntegerNode(value2);
    }

    public static JSConstantNode createSafeInteger(SafeInteger value2) {
        return new JSConstantSafeIntegerNode(value2);
    }

    public static JSConstantNode createBigInt(BigInt value2) {
        return new JSConstantBigIntNode(value2);
    }

    public static JSConstantNode createDouble(double value2) {
        return new JSConstantDoubleNode(value2);
    }

    public static JSConstantNode createConstantNumericUnit() {
        return new JSConstantNumericUnitNode();
    }

    public static JSConstantNode createBoolean(boolean value2) {
        return new JSConstantBooleanNode(value2);
    }

    public static JSConstantNode createString(TruffleString value2) {
        return new JSConstantStringNode(value2);
    }

    public abstract Object getValue();

    @Override
    public final void executeVoid(VirtualFrame frame) {
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return this.copy();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Map<String, Object> getDebugProperties() {
        Map<String, Object> map = super.getDebugProperties();
        map.put("value", Strings.isTString(this.getValue()) ? JSRuntime.quote(Strings.toJavaString((TruffleString)this.getValue())) : this.getValue());
        return map;
    }

    @Override
    public String expressionToString() {
        Object value2 = this.getValue();
        if (JSRuntime.isJSPrimitive(value2)) {
            String string = JSRuntime.toJavaString(value2);
            if (Strings.isTString(value2)) {
                return JSRuntime.quote(string);
            }
            return string;
        }
        return null;
    }

    public static final class JSConstantStringNode
    extends JSConstantNode {
        private final TruffleString stringValue;

        private JSConstantStringNode(TruffleString str) {
            this.stringValue = Objects.requireNonNull(str);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.stringValue;
        }

        @Override
        public boolean isResultAlwaysOfType(Class<?> clazz) {
            return clazz == TruffleString.class;
        }

        @Override
        public Object getValue() {
            return this.stringValue;
        }
    }

    public static final class JSConstantUndefinedNode
    extends JSConstantNode {
        private JSConstantUndefinedNode() {
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return Undefined.instance;
        }

        @Override
        public boolean isResultAlwaysOfType(Class<?> clazz) {
            return clazz == JSDynamicObject.class;
        }

        @Override
        public Object getValue() {
            return Undefined.instance;
        }
    }

    public static final class JSConstantNullNode
    extends JSConstantNode {
        private JSConstantNullNode() {
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return Null.instance;
        }

        @Override
        public boolean isResultAlwaysOfType(Class<?> clazz) {
            return clazz == JSDynamicObject.class;
        }

        @Override
        public Object getValue() {
            return Null.instance;
        }
    }

    private static final class JSConstantJSObjectNode
    extends JSConstantNode {
        private final JSDynamicObject objectValue;

        private JSConstantJSObjectNode(JSDynamicObject obj) {
            this.objectValue = obj;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.objectValue;
        }

        @Override
        public boolean isResultAlwaysOfType(Class<?> clazz) {
            return clazz == JSDynamicObject.class;
        }

        @Override
        public Object getValue() {
            return this.objectValue;
        }

        @Override
        public boolean hasTag(Class<? extends Tag> tag) {
            if (tag == JSTags.LiteralTag.class) {
                return false;
            }
            return super.hasTag(tag);
        }
    }

    private static final class JSConstantObjectNode
    extends JSConstantNode {
        private final Object objectValue;

        private JSConstantObjectNode(Object obj) {
            this.objectValue = obj;
            assert (!(obj instanceof JavaScriptNode)) : "must be JS value";
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.objectValue;
        }

        @Override
        public Object getValue() {
            return this.objectValue;
        }

        @Override
        public boolean hasTag(Class<? extends Tag> tag) {
            if (tag == JSTags.LiteralTag.class) {
                return false;
            }
            return super.hasTag(tag);
        }
    }

    public static final class JSConstantBooleanNode
    extends JSConstantNode {
        private final boolean booleanValue;

        private JSConstantBooleanNode(boolean value2) {
            this.booleanValue = value2;
        }

        @Override
        public boolean isResultAlwaysOfType(Class<?> clazz) {
            return clazz == Boolean.TYPE;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.booleanValue;
        }

        @Override
        public double executeDouble(VirtualFrame frame) {
            return this.booleanValue ? 1.0 : 0.0;
        }

        @Override
        public boolean executeBoolean(VirtualFrame frame) {
            return this.booleanValue;
        }

        @Override
        public Object getValue() {
            return this.booleanValue;
        }
    }

    public static final class JSConstantSafeIntegerNode
    extends JSConstantNode {
        private final SafeInteger safeIntValue;

        private JSConstantSafeIntegerNode(SafeInteger value2) {
            this.safeIntValue = value2;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.safeIntValue;
        }

        @Override
        public double executeDouble(VirtualFrame frame) {
            return this.safeIntValue.doubleValue();
        }

        @Override
        public boolean isResultAlwaysOfType(Class<?> clazz) {
            return clazz == Number.class || clazz == Double.TYPE;
        }

        @Override
        public Object getValue() {
            return this.safeIntValue;
        }
    }

    public static final class JSConstantBigIntNode
    extends JSConstantNode {
        private final BigInt bigIntValue;

        private JSConstantBigIntNode(BigInt value2) {
            this.bigIntValue = value2;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.bigIntValue;
        }

        public BigInt executeBigInt(VirtualFrame frame) {
            return this.bigIntValue;
        }

        @Override
        public boolean isResultAlwaysOfType(Class<?> clazz) {
            return clazz == BigInt.class;
        }

        @Override
        public Object getValue() {
            return this.bigIntValue;
        }
    }

    public static final class JSConstantNumericUnitNode
    extends JSConstantNode {
        private JSConstantNumericUnitNode() {
        }

        @Override
        public boolean isInstrumentable() {
            return false;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            throw Errors.shouldNotReachHere();
        }

        @Override
        public Object getValue() {
            throw Errors.shouldNotReachHere();
        }
    }

    public static final class JSConstantIntegerNode
    extends JSConstantNode {
        private final int intValue;

        private JSConstantIntegerNode(int value2) {
            this.intValue = value2;
        }

        @Override
        public int executeInt(VirtualFrame frame) {
            return this.intValue;
        }

        @Override
        public double executeDouble(VirtualFrame frame) {
            return this.intValue;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.intValue;
        }

        @Override
        public boolean isResultAlwaysOfType(Class<?> clazz) {
            return clazz == Integer.TYPE;
        }

        @Override
        public Object getValue() {
            return this.intValue;
        }
    }

    public static final class JSConstantDoubleNode
    extends JSConstantNode {
        private final double doubleValue;

        private JSConstantDoubleNode(double doubleValue) {
            this.doubleValue = doubleValue;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.doubleValue;
        }

        @Override
        public double executeDouble(VirtualFrame frame) {
            return this.doubleValue;
        }

        @Override
        public boolean isResultAlwaysOfType(Class<?> clazz) {
            return clazz == Double.TYPE;
        }

        @Override
        public Object getValue() {
            return this.doubleValue;
        }
    }
}


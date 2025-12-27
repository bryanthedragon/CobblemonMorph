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

public abstract class JSConstantNode extends JavaScriptNode implements RepeatableNode {
   public static JSConstantNode create(Object value) {
      assert !(value instanceof Long) && !(value instanceof BigInteger);

      if (value instanceof Integer) {
         return createInt((Integer)value);
      } else if (value instanceof Double) {
         double doubleValue = (Double)value;
         return JSRuntime.doubleIsRepresentableAsInt(doubleValue) ? createInt((int)doubleValue) : createDouble(doubleValue);
      } else if (value instanceof Boolean) {
         return createBoolean((Boolean)value);
      } else if (value instanceof TruffleString) {
         return createString((TruffleString)value);
      } else if (value == Null.instance) {
         return createNull();
      } else if (value == Undefined.instance) {
         return createUndefined();
      } else if (value instanceof BigInt) {
         return createBigInt((BigInt)value);
      } else if (value instanceof SafeInteger) {
         return createSafeInteger((SafeInteger)value);
      } else if (JSDynamicObject.isJSDynamicObject(value)) {
         return new JSConstantNode.JSConstantJSObjectNode((JSDynamicObject)value);
      } else {
         assert !(value instanceof String);

         return new JSConstantNode.JSConstantObjectNode(value);
      }
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.LiteralTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor();
      if (this instanceof JSConstantNode.JSConstantDoubleNode
         || this instanceof JSConstantNode.JSConstantIntegerNode
         || this instanceof JSConstantNode.JSConstantSafeIntegerNode) {
         descriptor.addProperty("literalType", JSTags.LiteralTag.Type.NumericLiteral.name());
      } else if (this instanceof JSConstantNode.JSConstantBigIntNode) {
         descriptor.addProperty("literalType", JSTags.LiteralTag.Type.BigIntLiteral.name());
      } else if (this instanceof JSConstantNode.JSConstantBooleanNode) {
         descriptor.addProperty("literalType", JSTags.LiteralTag.Type.BooleanLiteral.name());
      } else if (this instanceof JSConstantNode.JSConstantStringNode) {
         descriptor.addProperty("literalType", JSTags.LiteralTag.Type.StringLiteral.name());
      } else if (this instanceof JSConstantNode.JSConstantNullNode) {
         descriptor.addProperty("literalType", JSTags.LiteralTag.Type.NullLiteral.name());
      } else if (this instanceof JSConstantNode.JSConstantUndefinedNode) {
         descriptor.addProperty("literalType", JSTags.LiteralTag.Type.UndefinedLiteral.name());
      } else if (this instanceof JSConstantNode.JSConstantObjectNode || this instanceof JSConstantNode.JSConstantJSObjectNode) {
         descriptor.addProperty("literalType", JSTags.LiteralTag.Type.ObjectLiteral.name());
      }

      return descriptor;
   }

   public static JSConstantNode createUndefined() {
      return new JSConstantNode.JSConstantUndefinedNode();
   }

   public static JSConstantNode createNull() {
      return new JSConstantNode.JSConstantNullNode();
   }

   public static JSConstantNode createInt(int value) {
      return new JSConstantNode.JSConstantIntegerNode(value);
   }

   public static JSConstantNode createSafeInteger(SafeInteger value) {
      return new JSConstantNode.JSConstantSafeIntegerNode(value);
   }

   public static JSConstantNode createBigInt(BigInt value) {
      return new JSConstantNode.JSConstantBigIntNode(value);
   }

   public static JSConstantNode createDouble(double value) {
      return new JSConstantNode.JSConstantDoubleNode(value);
   }

   public static JSConstantNode createConstantNumericUnit() {
      return new JSConstantNode.JSConstantNumericUnitNode();
   }

   public static JSConstantNode createBoolean(boolean value) {
      return new JSConstantNode.JSConstantBooleanNode(value);
   }

   public static JSConstantNode createString(TruffleString value) {
      return new JSConstantNode.JSConstantStringNode(value);
   }

   public abstract Object getValue();

   @Override
   public final void executeVoid(VirtualFrame frame) {
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return this.copy();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Map<String, Object> getDebugProperties() {
      Map<String, Object> map = super.getDebugProperties();
      map.put("value", Strings.isTString(this.getValue()) ? JSRuntime.quote(Strings.toJavaString((TruffleString)this.getValue())) : this.getValue());
      return map;
   }

   @Override
   public String expressionToString() {
      Object value = this.getValue();
      if (JSRuntime.isJSPrimitive(value)) {
         String string = JSRuntime.toJavaString(value);
         return Strings.isTString(value) ? JSRuntime.quote(string) : string;
      } else {
         return null;
      }
   }

   public static final class JSConstantBigIntNode extends JSConstantNode {
      private final BigInt bigIntValue;

      private JSConstantBigIntNode(BigInt value) {
         this.bigIntValue = value;
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

   public static final class JSConstantBooleanNode extends JSConstantNode {
      private final boolean booleanValue;

      private JSConstantBooleanNode(boolean value) {
         this.booleanValue = value;
      }

      @Override
      public boolean isResultAlwaysOfType(Class<?> clazz) {
         return clazz == boolean.class;
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

   public static final class JSConstantDoubleNode extends JSConstantNode {
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
         return clazz == double.class;
      }

      @Override
      public Object getValue() {
         return this.doubleValue;
      }
   }

   public static final class JSConstantIntegerNode extends JSConstantNode {
      private final int intValue;

      private JSConstantIntegerNode(int value) {
         this.intValue = value;
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
         return clazz == int.class;
      }

      @Override
      public Object getValue() {
         return this.intValue;
      }
   }

   private static final class JSConstantJSObjectNode extends JSConstantNode {
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
         return tag == JSTags.LiteralTag.class ? false : super.hasTag(tag);
      }
   }

   public static final class JSConstantNullNode extends JSConstantNode {
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

   public static final class JSConstantNumericUnitNode extends JSConstantNode {
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

   private static final class JSConstantObjectNode extends JSConstantNode {
      private final Object objectValue;

      private JSConstantObjectNode(Object obj) {
         this.objectValue = obj;

         assert !(obj instanceof JavaScriptNode) : "must be JS value";
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
         return tag == JSTags.LiteralTag.class ? false : super.hasTag(tag);
      }
   }

   public static final class JSConstantSafeIntegerNode extends JSConstantNode {
      private final SafeInteger safeIntValue;

      private JSConstantSafeIntegerNode(SafeInteger value) {
         this.safeIntValue = value;
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
         return clazz == Number.class || clazz == double.class;
      }

      @Override
      public Object getValue() {
         return this.safeIntValue;
      }
   }

   public static final class JSConstantStringNode extends JSConstantNode {
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

   public static final class JSConstantUndefinedNode extends JSConstantNode {
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
}

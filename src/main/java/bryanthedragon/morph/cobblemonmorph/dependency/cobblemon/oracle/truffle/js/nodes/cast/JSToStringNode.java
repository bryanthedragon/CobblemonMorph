package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class JSToStringNode extends JavaScriptBaseNode {
   protected static final int MAX_CLASSES = 3;
   private final boolean undefinedToEmpty;
   private final boolean symbolToString;

   protected JSToStringNode() {
      this(false, false);
   }

   protected JSToStringNode(boolean undefinedToEmpty, boolean symbolToString) {
      this.undefinedToEmpty = undefinedToEmpty;
      this.symbolToString = symbolToString;
   }

   public static JSToStringNode create() {
      return JSToStringNodeGen.create(false, false);
   }

   public static JSToStringNode createUndefinedToEmpty() {
      return JSToStringNodeGen.create(true, false);
   }

   public static JSToStringNode createSymbolToString() {
      return JSToStringNodeGen.create(false, true);
   }

   public abstract TruffleString executeString(Object operand);

   @Specialization
   protected TruffleString doString(TruffleString value) {
      return value;
   }

   @Specialization(guards = "isJSNull(value)")
   protected TruffleString doNull(Object value) {
      return Null.NAME;
   }

   @Specialization(guards = "isUndefined(value)")
   protected TruffleString doUndefined(Object value) {
      return this.undefinedToEmpty ? Strings.EMPTY_STRING : Undefined.NAME;
   }

   @Specialization
   protected TruffleString doBoolean(boolean value) {
      return JSRuntime.booleanToString(value);
   }

   @Specialization
   protected TruffleString doInteger(int value) {
      return Strings.fromInt(value);
   }

   @Specialization
   protected TruffleString doBigInt(BigInt value) {
      return Strings.fromBigInt(value);
   }

   @Specialization
   protected TruffleString doLong(long value) {
      return Strings.fromLong(value);
   }

   @Specialization
   protected TruffleString doDouble(double d, @Cached("create()") JSDoubleToStringNode doubleToStringNode) {
      return doubleToStringNode.executeString(d);
   }

   @Specialization(replaces = "doUndefined")
   protected TruffleString doJSObject(
      JSDynamicObject value,
      @Cached.Shared("toPrimitiveHintStringNode") @Cached("createHintString()") JSToPrimitiveNode toPrimitiveHintStringNode,
      @Cached.Shared("toStringNode") @Cached JSToStringNode toStringNode
   ) {
      return this.undefinedToEmpty && value == Undefined.instance ? Strings.EMPTY_STRING : toStringNode.executeString(toPrimitiveHintStringNode.execute(value));
   }

   @CompilerDirectives.TruffleBoundary
   @Specialization
   protected TruffleString doSymbol(Symbol value) {
      if (this.symbolToString) {
         return value.toTString();
      } else {
         throw Errors.createTypeErrorCannotConvertToString("a Symbol value", this);
      }
   }

   @Specialization(guards = "isForeignObject(object)")
   protected TruffleString doTruffleObject(
      Object object,
      @Cached.Shared("toPrimitiveHintStringNode") @Cached("createHintString()") JSToPrimitiveNode toPrimitiveNode,
      @Cached.Shared("toStringNode") @Cached JSToStringNode toStringNode
   ) {
      return toStringNode.executeString(toPrimitiveNode.execute(object));
   }

   public abstract static class JSToStringWrapperNode extends JSUnaryNode {
      @Node.Child
      private JSToStringNode toStringNode;

      protected JSToStringWrapperNode(JavaScriptNode operand) {
         super(operand);
      }

      public static JSToStringNode.JSToStringWrapperNode create(JavaScriptNode child) {
         return JSToStringNodeGen.JSToStringWrapperNodeGen.create(child);
      }

      @Override
      public boolean isResultAlwaysOfType(Class<?> clazz) {
         return clazz == TruffleString.class;
      }

      @Specialization
      protected Object doDefault(Object value) {
         if (this.toStringNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toStringNode = this.insert(JSToStringNode.create());
         }

         return this.toStringNode.executeString(value);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return JSToStringNodeGen.JSToStringWrapperNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags));
      }
   }
}

package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

@ImportStatic({CompilerDirectives.class, JSConfig.class})
public abstract class RequireObjectCoercibleNode extends JavaScriptBaseNode {
   protected RequireObjectCoercibleNode() {
   }

   public static RequireObjectCoercibleNode create() {
      return RequireObjectCoercibleNodeGen.create();
   }

   public final Object execute(Object operand) {
      this.executeVoid(operand);
      return operand;
   }

   public abstract void executeVoid(Object operand);

   @Specialization
   protected static void doInt(int value) {
   }

   @Specialization
   protected static void doSafeInteger(SafeInteger value) {
   }

   @Specialization
   protected static void doLong(long value) {
   }

   @Specialization
   protected static void doDouble(double value) {
   }

   @Specialization
   protected static void doTString(TruffleString value) {
   }

   @Specialization
   protected static void doBoolean(boolean value) {
   }

   @Specialization
   protected static void doSymbol(Symbol value) {
   }

   @Specialization
   protected static void doBigInt(BigInt value) {
   }

   @Specialization(guards = {"cachedClass != null", "isExact(object, cachedClass)"}, limit = "1")
   protected static void doCachedJSClass(Object object, @Cached("getClassIfJSObject(object)") Class<?> cachedClass) {
   }

   @Specialization(guards = "isJSObject(object)", replaces = "doCachedJSClass")
   protected static void doJSObject(Object object) {
   }

   @Specialization(guards = "isForeignObject(object)", limit = "InteropLibraryLimit")
   protected void doForeignObject(Object object, @CachedLibrary("object") InteropLibrary interop) {
      if (interop.isNull(object)) {
         throw Errors.createTypeErrorNotObjectCoercible(object, this);
      }
   }

   @Specialization(guards = "isNullOrUndefined(object)")
   protected void doNullOrUndefined(JSDynamicObject object) {
      throw Errors.createTypeErrorNotObjectCoercible(object, this);
   }

   public abstract static class RequireObjectCoercibleWrapperNode extends JSUnaryNode {
      @Node.Child
      private RequireObjectCoercibleNode requireObjectCoercibleNode = RequireObjectCoercibleNode.create();

      protected RequireObjectCoercibleWrapperNode(JavaScriptNode operand) {
         super(operand);
      }

      public static RequireObjectCoercibleNode.RequireObjectCoercibleWrapperNode create(JavaScriptNode child) {
         return RequireObjectCoercibleNodeGen.RequireObjectCoercibleWrapperNodeGen.create(child);
      }

      @Override
      public boolean isResultAlwaysOfType(Class<?> clazz) {
         return this.getOperand().isResultAlwaysOfType(clazz);
      }

      @Specialization
      protected Object doDefault(Object value) {
         return this.requireObjectCoercibleNode.execute(value);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return create(cloneUninitialized(this.getOperand(), materializedTags));
      }
   }
}

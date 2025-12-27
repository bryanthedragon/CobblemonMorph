package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class JSGetLengthNode extends JavaScriptBaseNode {
   private final JSContext context;
   private final boolean toLength;
   @Node.Child
   private JSToUInt32Node toUInt32Node;
   @Node.Child
   private JSToLengthNode toLengthNode;

   protected JSGetLengthNode(JSContext context) {
      this.context = context;
      this.toLength = context.getEcmaScriptVersion() >= 6;
   }

   public static JSGetLengthNode create(JSContext context) {
      return JSGetLengthNodeGen.create(context);
   }

   public abstract Object execute(Object value);

   public final long executeLong(Object value) {
      return this.toLengthLong(this.execute(value));
   }

   @Specialization(rewriteOn = UnexpectedResultException.class)
   public int getArrayLengthInt(JSArrayObject target, @Cached("create()") ArrayLengthNode.ArrayLengthReadNode arrayLengthReadNode) throws UnexpectedResultException {
      return arrayLengthReadNode.executeInt(target);
   }

   @Specialization(replaces = "getArrayLengthInt")
   public double getArrayLength(JSArrayObject target, @Cached("create()") ArrayLengthNode.ArrayLengthReadNode arrayLengthReadNode) {
      return arrayLengthReadNode.executeDouble(target);
   }

   @Specialization(guards = "!isJSArray(target)")
   public double getNonArrayLength(JSDynamicObject target, @Cached("createLengthProperty()") PropertyGetNode getLengthPropertyNode) {
      return this.toLengthDouble(getLengthPropertyNode.getValue(target));
   }

   @Specialization(guards = "!isJSDynamicObject(target)", limit = "3")
   public double getLengthForeign(Object target, @CachedLibrary("target") InteropLibrary interop, @Cached("create()") ImportValueNode importValueNode) {
      return interop.hasArrayElements(target)
         ? JSInteropUtil.getArraySize(target, interop, this)
         : this.toLengthDouble(JSInteropUtil.readMemberOrDefault(target, JSAbstractArray.LENGTH, 0, interop, importValueNode, this));
   }

   protected PropertyGetNode createLengthProperty() {
      return PropertyGetNode.create(JSArray.LENGTH, this.context);
   }

   private double toUInt32Double(Object target) {
      return JSRuntime.doubleValue((Number)this.getUInt32Node().execute(target));
   }

   private long toUInt32Long(Object target) {
      return JSRuntime.longValue((Number)this.getUInt32Node().execute(target));
   }

   private double toLengthDouble(Object target) {
      return this.toLength ? this.getToLengthNode().executeLong(target) : this.toUInt32Double(target);
   }

   private long toLengthLong(Object target) {
      return this.toLength ? this.getToLengthNode().executeLong(target) : this.toUInt32Long(target);
   }

   private JSToLengthNode getToLengthNode() {
      if (this.toLengthNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toLengthNode = this.insert(JSToLengthNode.create());
      }

      return this.toLengthNode;
   }

   private JSToUInt32Node getUInt32Node() {
      if (this.toUInt32Node == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toUInt32Node = this.insert(JSToUInt32Node.create());
      }

      return this.toUInt32Node;
   }
}

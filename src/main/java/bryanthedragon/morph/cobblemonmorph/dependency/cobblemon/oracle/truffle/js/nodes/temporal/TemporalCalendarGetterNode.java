package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class TemporalCalendarGetterNode extends JavaScriptBaseNode {
   private final JSContext ctx;
   @Node.Child
   private GetMethodNode getMethodNode;
   @Node.Child
   private JSFunctionCallNode callNode;
   @Node.Child
   private JSToIntegerThrowOnInfinityNode toIntegerThrowOnInfinityNode;
   @Node.Child
   private JSToStringNode toStringNode;
   private final BranchProfile errorBranch = BranchProfile.create();
   private TruffleString cachedName;

   protected TemporalCalendarGetterNode(JSContext ctx) {
      this.ctx = ctx;
      this.callNode = JSFunctionCallNode.createCall();
   }

   public static TemporalCalendarGetterNode create(JSContext ctx) {
      return TemporalCalendarGetterNodeGen.create(ctx);
   }

   public abstract Object execute(JSDynamicObject calendar, JSDynamicObject dateLike, TruffleString name);

   public final Number executeInteger(JSDynamicObject calendar, JSDynamicObject dateLike, TruffleString name) {
      if (this.toIntegerThrowOnInfinityNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toIntegerThrowOnInfinityNode = this.insert(JSToIntegerThrowOnInfinityNode.create());
      }

      return (Number)this.toIntegerThrowOnInfinityNode.execute(this.execute(calendar, dateLike, name));
   }

   public final TruffleString executeString(JSDynamicObject calendar, JSDynamicObject dateLike, TruffleString name) {
      if (this.toStringNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toStringNode = this.insert(JSToStringNode.create());
      }

      return this.toStringNode.executeString(this.execute(calendar, dateLike, name));
   }

   @Specialization
   protected Object calendarGetter(JSDynamicObject calendar, JSDynamicObject dateLike, TruffleString name) {
      Object fn = this.getMethod(calendar, name);
      Object result = this.callNode.executeCall(JSArguments.create(calendar, fn, dateLike));
      if (result == Undefined.instance) {
         this.errorBranch.enter();
         throw Errors.createRangeError("expected a value.");
      } else {
         return result;
      }
   }

   private Object getMethod(JSDynamicObject calendar, TruffleString name) {
      if (this.getMethodNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getMethodNode = this.insert(GetMethodNode.create(this.ctx, name));
         this.cachedName = name;
      }

      assert this.cachedName.equals(name);

      return this.getMethodNode.executeWithTarget(calendar);
   }
}

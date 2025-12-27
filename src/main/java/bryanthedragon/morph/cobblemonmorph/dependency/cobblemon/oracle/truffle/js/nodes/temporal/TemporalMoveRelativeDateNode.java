package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalRelativeDateRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class TemporalMoveRelativeDateNode extends JavaScriptBaseNode {
   protected final JSContext ctx;
   @Node.Child
   private GetMethodNode getMethodDateAddNode;
   @Node.Child
   private JSFunctionCallNode callDateAddNode;
   private final BranchProfile errorBranch = BranchProfile.create();

   protected TemporalMoveRelativeDateNode(JSContext ctx) {
      this.ctx = ctx;
   }

   public static TemporalMoveRelativeDateNode create(JSContext ctx) {
      return TemporalMoveRelativeDateNodeGen.create(ctx);
   }

   public abstract JSTemporalRelativeDateRecord execute(JSDynamicObject calendar, JSDynamicObject relativeTo, JSDynamicObject duration);

   @Specialization
   protected JSTemporalRelativeDateRecord moveRelativeDate(JSDynamicObject calendar, JSDynamicObject relativeTo, JSDynamicObject duration) {
      JSTemporalPlainDateObject newDate = this.calendarDateAdd(calendar, relativeTo, duration);
      long days = TemporalUtil.daysUntil(relativeTo, newDate);
      return JSTemporalRelativeDateRecord.create(newDate, days);
   }

   protected JSTemporalPlainDateObject calendarDateAdd(JSDynamicObject calendar, JSDynamicObject date, JSDynamicObject duration) {
      if (this.getMethodDateAddNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getMethodDateAddNode = this.insert(GetMethodNode.create(this.ctx, TemporalConstants.DATE_ADD));
      }

      if (this.callDateAddNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.callDateAddNode = this.insert(JSFunctionCallNode.createCall());
      }

      Object dateAddPrepared = this.getMethodDateAddNode.executeWithTarget(calendar);
      Object addedDate = this.callDateAddNode.executeCall(JSArguments.create(calendar, dateAddPrepared, date, duration, Undefined.instance));
      return TemporalUtil.requireTemporalDate(addedDate, this.errorBranch);
   }
}

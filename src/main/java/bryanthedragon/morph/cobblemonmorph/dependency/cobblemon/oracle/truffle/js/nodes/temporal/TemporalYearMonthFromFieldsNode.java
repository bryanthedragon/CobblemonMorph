package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonthObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class TemporalYearMonthFromFieldsNode extends JavaScriptBaseNode {
   protected final BranchProfile errorBranch = BranchProfile.create();
   @Node.Child
   private GetMethodNode getMethodYearMonthFromFieldsNode;
   @Node.Child
   private JSFunctionCallNode callYearMonthFromFieldsNode;

   protected TemporalYearMonthFromFieldsNode(JSContext ctx) {
      this.getMethodYearMonthFromFieldsNode = GetMethodNode.create(ctx, TemporalUtil.YEAR_MONTH_FROM_FIELDS);
      this.callYearMonthFromFieldsNode = JSFunctionCallNode.createCall();
   }

   public static TemporalYearMonthFromFieldsNode create(JSContext ctx) {
      return TemporalYearMonthFromFieldsNodeGen.create(ctx);
   }

   public abstract JSTemporalPlainYearMonthObject execute(JSDynamicObject calendar, JSDynamicObject fields, JSDynamicObject options);

   @Specialization
   protected JSTemporalPlainYearMonthObject yearMonthFromFields(JSDynamicObject calendar, JSDynamicObject fields, JSDynamicObject options) {
      assert options != null;

      Object fn = this.getMethodYearMonthFromFieldsNode.executeWithTarget(calendar);
      Object yearMonth = this.callYearMonthFromFieldsNode.executeCall(JSArguments.create(calendar, fn, fields, options));
      return this.requireTemporalYearMonth(yearMonth);
   }

   public JSTemporalPlainYearMonthObject requireTemporalYearMonth(Object obj) {
      if (!(obj instanceof JSTemporalPlainYearMonthObject)) {
         this.errorBranch.enter();
         throw TemporalErrors.createTypeErrorTemporalPlainYearMonthExpected();
      } else {
         return (JSTemporalPlainYearMonthObject)obj;
      }
   }
}

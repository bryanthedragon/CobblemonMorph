package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class TemporalCalendarDateFromFieldsNode extends JavaScriptBaseNode {
   private final BranchProfile errorBranch = BranchProfile.create();
   @Node.Child
   protected PropertyGetNode getDateFromFieldsNode;
   @Node.Child
   protected JSFunctionCallNode callNode;

   protected TemporalCalendarDateFromFieldsNode(JSContext ctx) {
      this.getDateFromFieldsNode = PropertyGetNode.create(TemporalConstants.DATE_FROM_FIELDS, false, ctx);
      this.callNode = JSFunctionCallNode.createCall();
   }

   public static TemporalCalendarDateFromFieldsNode create(JSContext context) {
      return TemporalCalendarDateFromFieldsNodeGen.create(context);
   }

   public abstract JSTemporalPlainDateObject execute(JSDynamicObject calendar, JSDynamicObject fields, Object options);

   @Specialization
   public JSTemporalPlainDateObject toTemporalDate(JSDynamicObject calendar, JSDynamicObject fields, Object options) {
      Object dateFromFields = this.getDateFromFieldsNode.getValue(calendar);
      Object date = this.callNode.executeCall(JSArguments.create(calendar, dateFromFields, fields, options));
      return TemporalUtil.requireTemporalDate(date, this.errorBranch);
   }
}

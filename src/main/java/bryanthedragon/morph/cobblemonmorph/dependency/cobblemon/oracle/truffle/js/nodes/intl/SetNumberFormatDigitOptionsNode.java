package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.IntlUtil;

public abstract class SetNumberFormatDigitOptionsNode extends JavaScriptBaseNode {
   @Node.Child
   GetNumberOptionNode getMinIntDigitsOption;
   @Node.Child
   PropertyGetNode getMinFracDigitsOption;
   @Node.Child
   PropertyGetNode getMaxFracDigitsOption;
   @Node.Child
   PropertyGetNode getMinSignificantDigitsOption;
   @Node.Child
   PropertyGetNode getMaxSignificantDigitsOption;
   @Node.Child
   DefaultNumberOptionNode getMnsdDNO;
   @Node.Child
   DefaultNumberOptionNode getMxsdDNO;
   @Node.Child
   DefaultNumberOptionNode getMnfdDNO;
   @Node.Child
   DefaultNumberOptionNode getMxfdDNO;
   @Node.Child
   GetStringOptionNode getRoundingPriorityOption;
   private final BranchProfile errorBranch = BranchProfile.create();

   protected SetNumberFormatDigitOptionsNode(JSContext context) {
      this.getMinIntDigitsOption = GetNumberOptionNode.create(context, IntlUtil.KEY_MINIMUM_INTEGER_DIGITS);
      this.getMinFracDigitsOption = PropertyGetNode.create(IntlUtil.KEY_MINIMUM_FRACTION_DIGITS, context);
      this.getMaxFracDigitsOption = PropertyGetNode.create(IntlUtil.KEY_MAXIMUM_FRACTION_DIGITS, context);
      this.getMinSignificantDigitsOption = PropertyGetNode.create(IntlUtil.KEY_MINIMUM_SIGNIFICANT_DIGITS, context);
      this.getMaxSignificantDigitsOption = PropertyGetNode.create(IntlUtil.KEY_MAXIMUM_SIGNIFICANT_DIGITS, context);
      this.getMnsdDNO = DefaultNumberOptionNode.create();
      this.getMxsdDNO = DefaultNumberOptionNode.create();
      this.getMnfdDNO = DefaultNumberOptionNode.create();
      this.getMxfdDNO = DefaultNumberOptionNode.create();
      this.getRoundingPriorityOption = GetStringOptionNode.create(
         context, IntlUtil.KEY_ROUNDING_PRIORITY, new String[]{"auto", "morePrecision", "lessPrecision"}, "auto"
      );
   }

   public static SetNumberFormatDigitOptionsNode create(JSContext context) {
      return SetNumberFormatDigitOptionsNodeGen.create(context);
   }

   public abstract Object execute(JSNumberFormat.BasicInternalState intlObj, Object options, int mnfdDefault, int mxfdDefault, boolean compactNotation);

   @Specialization
   public Object setNumberFormatDigitOptions(
      JSNumberFormat.BasicInternalState intlObj, Object options, int mnfdDefault, int mxfdDefault, boolean compactNotation
   ) {
      int mnid = this.getMinIntDigitsOption.executeInt(options, 1, 21, 1);
      Object mnfdValue = this.getMinFracDigitsOption.getValue(options);
      Object mxfdValue = this.getMaxFracDigitsOption.getValue(options);
      Object mnsdValue = this.getMinSignificantDigitsOption.getValue(options);
      Object mxsdValue = this.getMaxSignificantDigitsOption.getValue(options);
      intlObj.setMinimumIntegerDigits(mnid);
      String roundingPriority = this.getRoundingPriorityOption.executeValue(options);
      boolean hasSd = mnsdValue != Undefined.instance || mxsdValue != Undefined.instance;
      boolean hasFd = mnfdValue != Undefined.instance || mxfdValue != Undefined.instance;
      boolean autoRoundingPriority = "auto".equals(roundingPriority);
      boolean needSd = hasSd || !autoRoundingPriority;
      boolean needFd = !hasSd && (hasFd || !compactNotation) || !autoRoundingPriority;
      if (needSd) {
         if (hasSd) {
            int mnsd = this.getMnsdDNO.executeInt(mnsdValue, 1, 21, 1);
            int mxsd = this.getMxsdDNO.executeInt(mxsdValue, mnsd, 21, 21);
            intlObj.setMinimumSignificantDigits(mnsd);
            intlObj.setMaximumSignificantDigits(mxsd);
         } else {
            intlObj.setMinimumSignificantDigits(1);
            intlObj.setMaximumSignificantDigits(21);
         }
      }

      if (needFd) {
         if (hasFd) {
            int mnfd = this.getMnfdDNO.executeInt(mnfdValue, 0, 20, -1);
            int mxfd = this.getMxfdDNO.executeInt(mxfdValue, 0, 20, -1);
            if (mnfd == -1) {
               mnfd = Math.min(mnfdDefault, mxfd);
            } else if (mxfd == -1) {
               mxfd = Math.max(mxfdDefault, mnfd);
            } else if (mnfd > mxfd) {
               this.errorBranch.enter();
               throw Errors.createRangeError("minimumFractionDigits higher than maximumFractionDigits");
            }

            intlObj.setMinimumFractionDigits(mnfd);
            intlObj.setMaximumFractionDigits(mxfd);
         } else {
            intlObj.setMinimumFractionDigits(mnfdDefault);
            intlObj.setMaximumFractionDigits(mxfdDefault);
         }
      }

      if (!needSd && !needFd) {
         intlObj.setRoundingType("morePrecision");
         intlObj.setMinimumFractionDigits(0);
         intlObj.setMaximumFractionDigits(0);
         intlObj.setMinimumSignificantDigits(1);
         intlObj.setMaximumSignificantDigits(2);
      } else if ("morePrecision".equals(roundingPriority) || "lessPrecision".equals(roundingPriority)) {
         intlObj.setRoundingType(roundingPriority);
      } else if (hasSd) {
         intlObj.setRoundingType("significantDigits");
      } else {
         intlObj.setRoundingType("fractionDigits");
      }

      return Undefined.instance;
   }
}

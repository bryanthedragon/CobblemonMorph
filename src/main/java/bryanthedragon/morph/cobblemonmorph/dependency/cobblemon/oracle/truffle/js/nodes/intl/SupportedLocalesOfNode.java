package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.List;

public abstract class SupportedLocalesOfNode extends JSBuiltinNode {
   @Node.Child
   JSToCanonicalizedLocaleListNode toCanonicalizedLocaleListNode;

   public SupportedLocalesOfNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
      this.toCanonicalizedLocaleListNode = JSToCanonicalizedLocaleListNode.create(context);
   }

   @Specialization(guards = "isUndefined(opts)")
   protected Object getSupportedLocales(Object locales, Object opts) {
      List<Object> supportedLocals = IntlUtil.supportedLocales(this.getContext(), this.toCanonicalizedLocaleListNode.executeLanguageTags(locales), "best fit");
      return JSRuntime.createArrayFromList(this.getContext(), this.getRealm(), supportedLocals);
   }

   @Specialization(guards = "!isUndefined(opts)")
   protected Object getSupportedLocalesWithOptions(
      Object locales,
      Object opts,
      @Cached("createToObject(getContext())") JSToObjectNode toObjectNode,
      @Cached("createMatcherGetter(getContext())") GetStringOptionNode getMatcherNode
   ) {
      String matcher = getMatcherNode.executeValue(toObjectNode.execute(opts));
      List<Object> supportedLocales = IntlUtil.supportedLocales(this.getContext(), this.toCanonicalizedLocaleListNode.executeLanguageTags(locales), matcher);
      return JSRuntime.createArrayFromList(this.getContext(), this.getRealm(), supportedLocales);
   }

   protected static GetStringOptionNode createMatcherGetter(JSContext context) {
      return GetStringOptionNode.create(context, IntlUtil.KEY_LOCALE_MATCHER, new String[]{"lookup", "best fit"}, "best fit");
   }
}

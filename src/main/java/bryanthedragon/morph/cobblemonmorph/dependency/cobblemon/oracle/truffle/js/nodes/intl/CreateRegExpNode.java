package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.util.TRegexUtil;

public abstract class CreateRegExpNode extends JavaScriptBaseNode {
   @Node.Child
   private TRegexUtil.InteropReadMemberNode readNamedCG = TRegexUtil.InteropReadMemberNode.create();
   @Node.Child
   private InteropLibrary isNamedCGNull = InteropLibrary.getFactory().createDispatched(3);
   @Node.Child
   private PropertySetNode setLastIndex;
   private final JSContext context;

   protected CreateRegExpNode(JSContext context) {
      this.context = context;
      this.setLastIndex = PropertySetNode.createImpl(JSRegExp.LAST_INDEX, false, context, true, true, JSAttributes.notConfigurableNotEnumerableWritable());
   }

   public static CreateRegExpNode create(JSContext context) {
      return CreateRegExpNodeGen.create(context);
   }

   public final JSRegExpObject createRegExp(Object compiledRegex) {
      return this.createRegExp(compiledRegex, true);
   }

   public final JSRegExpObject createRegExp(Object compiledRegex, boolean legacyFeaturesEnabled) {
      Object namedCaptureGroups = this.readNamedCG.execute(compiledRegex, "groups");
      boolean hasNamedCaptureGroups = !this.isNamedCGNull.isNull(namedCaptureGroups);
      return this.execute(compiledRegex, legacyFeaturesEnabled, namedCaptureGroups, hasNamedCaptureGroups);
   }

   protected abstract JSRegExpObject execute(Object compiledRegex, boolean legacyFeaturesEnabled, Object namedCaptureGroups, boolean hasNamedCG);

   @Specialization(guards = "!b(hasNamedCaptureGroups)")
   protected JSRegExpObject createWithoutNamedCG(Object compiledRegex, boolean legacyFeaturesEnabled, Object namedCaptureGroups, boolean hasNamedCaptureGroups) {
      JSRegExpObject reObj = JSRegExp.create(this.context, this.getRealm(), compiledRegex, null, legacyFeaturesEnabled);
      this.setLastIndex.setValueInt(reObj, 0);
      return reObj;
   }

   @Specialization(guards = "b(hasNamedCaptureGroups)")
   protected JSRegExpObject createWithNamedCG(Object compiledRegex, boolean legacyFeaturesEnabled, Object namedCaptureGroups, boolean hasNamedCaptureGroups) {
      JSRegExpObject reObj = JSRegExp.create(
         this.context, this.getRealm(), compiledRegex, JSRegExp.buildGroupsFactory(this.context, namedCaptureGroups), legacyFeaturesEnabled
      );
      this.setLastIndex.setValueInt(reObj, 0);
      return reObj;
   }

   static boolean b(boolean value) {
      return value;
   }
}

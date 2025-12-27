package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import java.util.Set;

public abstract class GetTemplateObjectNode extends JavaScriptNode {
   protected final JSContext context;
   @Node.Child
   private ArrayLiteralNode rawStrings;
   @Node.Child
   private ArrayLiteralNode cookedStrings;
   private final Object identity;

   protected GetTemplateObjectNode(JSContext context, ArrayLiteralNode rawStrings, ArrayLiteralNode cookedStrings) {
      this.context = context;
      this.rawStrings = rawStrings;
      this.cookedStrings = cookedStrings;
      this.identity = this;
   }

   protected GetTemplateObjectNode(JSContext context, ArrayLiteralNode rawStrings, ArrayLiteralNode cookedStrings, Object identity) {
      this.context = context;
      this.rawStrings = rawStrings;
      this.cookedStrings = cookedStrings;
      this.identity = identity;
   }

   public static GetTemplateObjectNode create(JSContext context, ArrayLiteralNode rawStrings, ArrayLiteralNode cookedStrings) {
      return GetTemplateObjectNodeGen.create(context, rawStrings, cookedStrings);
   }

   @Specialization(guards = "!context.isMultiContext()", assumptions = "context.getSingleRealmAssumption()")
   protected JSDynamicObject doCached(VirtualFrame frame, @Cached("doUncached(frame)") JSDynamicObject cachedTemplate) {
      return cachedTemplate;
   }

   @Specialization(replaces = "doCached")
   protected JSDynamicObject doUncached(VirtualFrame frame) {
      JSDynamicObject cached = Boundaries.mapGet(this.getRealm().getTemplateRegistry(), this.identity);
      if (cached != null) {
         return cached;
      } else {
         cached = this.buildTemplateObject(frame);
         Boundaries.mapPut(this.getRealm().getTemplateRegistry(), this.identity, cached);
         return cached;
      }
   }

   private JSDynamicObject buildTemplateObject(VirtualFrame frame) {
      JSDynamicObject template = this.cookedStrings.execute(frame);
      JSDynamicObject rawObj = this.rawStrings.execute(frame);
      JSObject.setIntegrityLevel(rawObj, true);
      JSObjectUtil.putDataProperty(this.context, template, Strings.RAW, rawObj, JSAttributes.notConfigurableNotEnumerableNotWritable());
      JSObject.setIntegrityLevel(template, true);
      return template;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return GetTemplateObjectNodeGen.create(
         this.context, cloneUninitialized(this.rawStrings, materializedTags), cloneUninitialized(this.cookedStrings, materializedTags), this.identity
      );
   }
}

package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSGlobal;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class DeclareGlobalVariableNode extends DeclareGlobalNode {
   private final boolean configurable;
   @Node.Child
   private HasPropertyCacheNode hasOwnPropertyNode;
   @Node.Child
   private IsExtensibleNode isExtensibleNode = IsExtensibleNode.create();

   protected DeclareGlobalVariableNode(TruffleString varName, boolean configurable) {
      super(varName);
      this.configurable = configurable;
   }

   public static DeclareGlobalVariableNode create(TruffleString varName, boolean configurable) {
      return DeclareGlobalVariableNodeGen.create(varName, configurable);
   }

   @Override
   public void verify(JSContext context, JSRealm realm) {
      super.verify(context, realm);
      JSDynamicObject globalObject = realm.getGlobalObject();
      if (this.hasOwnPropertyNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.hasOwnPropertyNode = this.insert(HasPropertyCacheNode.create(this.varName, context, true));
      }

      if (!this.hasOwnPropertyNode.hasProperty(globalObject) && !this.isExtensibleNode.executeBoolean(globalObject)) {
         this.errorProfile.enter();
         throw Errors.createTypeErrorGlobalObjectNotExtensible(this);
      }
   }

   @Override
   public final void executeVoid(VirtualFrame frame, JSContext context, JSRealm realm) {
      JSDynamicObject globalObject = realm.getGlobalObject();
      if (!this.hasOwnPropertyNode.hasProperty(globalObject)) {
         assert JSObject.isExtensible(globalObject);

         this.executeVoid(globalObject, context);
      }
   }

   protected abstract void executeVoid(JSDynamicObject globalObject, JSContext context);

   @Specialization(guards = {"context.getPropertyCacheLimit() > 0", "isJSGlobalObject(globalObject)"})
   protected void doCached(JSDynamicObject globalObject, JSContext context, @Cached("makeDefineOwnPropertyCache(context)") PropertySetNode cache) {
      cache.setValue(globalObject, Undefined.instance);
   }

   @Specialization(replaces = "doCached")
   protected void doUncached(JSDynamicObject globalObject, JSContext context) {
      if (JSGlobal.isJSGlobalObject(globalObject)) {
         JSObjectUtil.putDeclaredDataProperty(context, globalObject, this.varName, Undefined.instance, this.getAttributeFlags());
      } else {
         PropertyDescriptor desc = this.configurable ? PropertyDescriptor.undefinedDataDesc : PropertyDescriptor.undefinedDataDescNotConfigurable;
         JSObject.defineOwnProperty(globalObject, this.varName, desc, true);
      }
   }

   private int getAttributeFlags() {
      return this.configurable ? JSAttributes.configurableEnumerableWritable() : JSAttributes.notConfigurableEnumerableWritable();
   }

   protected final PropertySetNode makeDefineOwnPropertyCache(JSContext context) {
      return PropertySetNode.createImpl(this.varName, false, context, true, true, this.getAttributeFlags(), true);
   }

   @Override
   protected DeclareGlobalNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(this.varName, this.configurable);
   }
}

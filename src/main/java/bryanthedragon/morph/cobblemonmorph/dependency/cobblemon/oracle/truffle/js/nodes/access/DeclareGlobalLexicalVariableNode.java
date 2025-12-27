package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.Dead;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import java.util.Set;

public abstract class DeclareGlobalLexicalVariableNode extends DeclareGlobalNode {
   private final boolean isConst;
   @Node.Child
   private JSGetOwnPropertyNode getOwnPropertyNode;

   protected DeclareGlobalLexicalVariableNode(TruffleString varName, boolean isConst) {
      super(varName);
      this.isConst = isConst;
      this.getOwnPropertyNode = JSGetOwnPropertyNode.create(false);
   }

   public static DeclareGlobalLexicalVariableNode create(TruffleString varName, boolean isConst) {
      return DeclareGlobalLexicalVariableNodeGen.create(varName, isConst);
   }

   @Override
   public void verify(JSContext context, JSRealm realm) {
      super.verify(context, realm);
      PropertyDescriptor desc = this.getOwnPropertyNode.execute(realm.getGlobalObject(), this.varName);
      if (desc != null && !desc.getConfigurable()) {
         this.errorProfile.enter();
         throw Errors.createSyntaxErrorVariableAlreadyDeclared(this.varName, this);
      }
   }

   @Override
   public final void executeVoid(VirtualFrame frame, JSContext context, JSRealm realm) {
      JSDynamicObject globalScope = realm.getGlobalScope();

      assert !JSObject.hasOwnProperty(globalScope, this.varName);

      assert JSObject.isExtensible(globalScope);

      this.executeVoid(globalScope, context);
   }

   private int getAttributeFlags() {
      return this.isConst ? JSAttributes.notConfigurableEnumerableWritable() | 32 : JSAttributes.notConfigurableEnumerableWritable();
   }

   protected abstract void executeVoid(JSDynamicObject globalScope, JSContext context);

   @Specialization(guards = "context.getPropertyCacheLimit() > 0")
   protected void doCached(JSDynamicObject globalScope, JSContext context, @Cached("makeDefineOwnPropertyCache(context)") PropertySetNode cache) {
      cache.setValue(globalScope, Dead.instance());
   }

   @Specialization(replaces = "doCached")
   protected void doUncached(JSDynamicObject globalScope, JSContext context) {
      JSObjectUtil.putDeclaredDataProperty(context, globalScope, this.varName, Dead.instance(), this.getAttributeFlags());
   }

   protected final PropertySetNode makeDefineOwnPropertyCache(JSContext context) {
      return PropertySetNode.createImpl(this.varName, false, context, true, true, this.getAttributeFlags(), true);
   }

   @Override
   public boolean isLexicallyDeclared() {
      return true;
   }

   @Override
   protected DeclareGlobalNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(this.varName, this.isConst);
   }
}

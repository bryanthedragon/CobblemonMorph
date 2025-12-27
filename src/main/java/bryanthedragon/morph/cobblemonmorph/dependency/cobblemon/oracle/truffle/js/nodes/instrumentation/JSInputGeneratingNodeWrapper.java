package com.oracle.truffle.js.nodes.instrumentation;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.SuperPropertyReferenceNode;
import java.util.Set;

public final class JSInputGeneratingNodeWrapper extends JavaScriptNode {
   @Node.Child
   private JavaScriptNode delegate;

   private JSInputGeneratingNodeWrapper(JavaScriptNode toWrap) {
      this.delegate = toWrap;
   }

   public static JavaScriptNode create(JavaScriptNode toWrap) {
      assert !(toWrap instanceof SuperPropertyReferenceNode);

      JSInputGeneratingNodeWrapper wrapper = new JSInputGeneratingNodeWrapper(toWrap);
      transferSourceSectionAndTags(toWrap, wrapper);
      return wrapper;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      return this.delegate.execute(frame);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.InputNodeTag.class ? true : this.delegate.hasTag(tag);
   }

   @Override
   public boolean isInstrumentable() {
      return true;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new JSInputGeneratingNodeWrapper(cloneUninitialized(this.delegate, materializedTags));
   }

   public JavaScriptNode getDelegateNode() {
      return this.delegate;
   }
}

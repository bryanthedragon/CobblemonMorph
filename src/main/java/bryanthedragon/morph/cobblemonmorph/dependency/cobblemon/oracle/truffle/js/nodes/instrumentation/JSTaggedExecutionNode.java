package com.oracle.truffle.js.nodes.instrumentation;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.SuperPropertyReferenceNode;
import java.util.Objects;
import java.util.Set;

public final class JSTaggedExecutionNode extends JavaScriptNode {
   @Node.Child
   private JavaScriptNode child;
   private final Class<? extends Tag> expectedTag;
   private final boolean inputTag;
   private final NodeObjectDescriptor descriptor;

   public static JavaScriptNode createFor(JavaScriptNode originalNode, Class<? extends Tag> expectedTag, Set<Class<? extends Tag>> materializedTags) {
      return createImpl(originalNode, originalNode, expectedTag, false, null, materializedTags);
   }

   public static JavaScriptNode createForInput(JavaScriptNode originalNode, Class<? extends Tag> expectedTag, Set<Class<? extends Tag>> materializedTags) {
      return createImpl(originalNode, originalNode, expectedTag, true, null, materializedTags);
   }

   public static JavaScriptNode createForInput(
      JavaScriptNode originalNode, Class<? extends Tag> expectedTag, NodeObjectDescriptor descriptor, Set<Class<? extends Tag>> materializedTags
   ) {
      return createImpl(originalNode, originalNode, expectedTag, true, descriptor, materializedTags);
   }

   public static JavaScriptNode createForInput(JavaScriptNode originalNode, JavaScriptNode transferSourcesFrom, Set<Class<? extends Tag>> materializedTags) {
      return createImpl(originalNode, transferSourcesFrom, null, true, null, materializedTags);
   }

   private static JavaScriptNode createImpl(
      JavaScriptNode originalNode,
      JavaScriptNode transferSourcesFrom,
      Class<? extends Tag> expectedTag,
      boolean inputTag,
      NodeObjectDescriptor descriptor,
      Set<Class<? extends Tag>> materializedTags
   ) {
      JavaScriptNode realOriginal = originalNode;
      if (originalNode instanceof InstrumentableNode.WrapperNode) {
         realOriginal = (JavaScriptNode)((InstrumentableNode.WrapperNode)originalNode).getDelegateNode();
      }

      assert !(realOriginal instanceof SuperPropertyReferenceNode);

      if (!realOriginal.hasTag(expectedTag) || inputTag && !realOriginal.hasTag(JSTags.InputNodeTag.class)) {
         JavaScriptNode clone = cloneUninitialized(originalNode, materializedTags);
         JavaScriptNode wrapper = new JSTaggedExecutionNode(clone, expectedTag, inputTag, descriptor);
         transferSourceSection(transferSourcesFrom, wrapper);
         return wrapper;
      } else {
         return originalNode;
      }
   }

   private JSTaggedExecutionNode(JavaScriptNode child, Class<? extends Tag> expectedTag, boolean inputTag, NodeObjectDescriptor descriptor) {
      this.child = Objects.requireNonNull(child);
      this.expectedTag = expectedTag;
      this.inputTag = inputTag;
      this.descriptor = descriptor;
   }

   @Override
   public Object getNodeObject() {
      return this.descriptor != null ? this.descriptor : this.child.getNodeObject();
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      if (this.expectedTag != null && tag == this.expectedTag) {
         return true;
      } else {
         return tag == JSTags.InputNodeTag.class ? this.inputTag : super.hasTag(tag);
      }
   }

   @Override
   public Object execute(VirtualFrame frame) {
      return this.child.execute(frame);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new JSTaggedExecutionNode(cloneUninitialized(this.child, materializedTags), this.expectedTag, this.inputTag, this.descriptor);
   }

   public JavaScriptNode getDelegateNode() {
      return this.child;
   }
}

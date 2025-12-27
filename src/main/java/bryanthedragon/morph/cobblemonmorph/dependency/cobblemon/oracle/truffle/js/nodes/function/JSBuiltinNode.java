package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInterface;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.Arrays;
import java.util.Set;

@NodeChild(value = "arguments", type = JavaScriptNode.class)
public abstract class JSBuiltinNode extends AbstractBodyNode {
   public static final String ARGUMENTS = "arguments";
   private final JSContext context;
   private final JSBuiltin builtin;
   boolean construct;
   boolean newTarget;
   private static final boolean VERIFY_ARGUMENT_COUNT = false;

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.BuiltinRootTag.class ? super.hasTag(StandardTags.RootBodyTag.class) : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("name", this.getBuiltin().getFullName());
   }

   protected JSBuiltinNode(JSContext context, JSBuiltin builtin) {
      this.context = context;
      this.builtin = builtin;
   }

   protected JSBuiltinNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget) {
      this.context = context;
      this.builtin = builtin;
      this.construct = construct;
      this.newTarget = newTarget;
   }

   public final JSContext getContext() {
      return this.context;
   }

   public JSBuiltin getBuiltin() {
      return this.builtin;
   }

   public abstract JavaScriptNode[] getArguments();

   public boolean isInlineable() {
      return this instanceof JSBuiltinNode.Inlineable;
   }

   public JSBuiltinNode.Inlined tryCreateInlined() {
      return this.isInlineable() ? ((JSBuiltinNode.Inlineable)this).createInlined() : null;
   }

   public boolean isCallerSensitive() {
      return false;
   }

   public boolean countsTowardsStackTraceLimit() {
      return true;
   }

   public static JSBuiltinNode createBuiltin(JSContext ctx, JSBuiltin builtin, boolean construct, boolean newTarget) {
      JSBuiltinNode builtinNode = builtin.createNode(ctx, construct, newTarget);
      builtinNode.addRootBodyTag();
      return builtinNode;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return createBuiltin(this.context, this.builtin, this.construct, this.newTarget);
   }

   private static void verifyArgumentCount(JSBuiltinNode builtinNode) {
      assert !JSConfig.SubstrateVM;

      int argumentNodeCount = 0;
      Class<? extends JSBuiltinNode> nodeclass = (Class<? extends JSBuiltinNode>)builtinNode.getClass();

      for (Class<?> superclass = nodeclass; superclass != null; superclass = superclass.getSuperclass()) {
         argumentNodeCount = (int)(
            argumentNodeCount
               + Arrays.stream(superclass.getDeclaredFields())
                  .filter(f -> f.getAnnotation(Node.Child.class) != null && f.getName().startsWith("arguments"))
                  .count()
         );
      }

      int providedArgumentNodeCount = 0;

      for (Class<?> superclass = nodeclass; superclass != null; superclass = superclass.getSuperclass()) {
         providedArgumentNodeCount = (int)(
            providedArgumentNodeCount
               + Arrays.stream(superclass.getDeclaredFields())
                  .filter(f -> f.getAnnotation(Node.Child.class) != null && f.getName().startsWith("arguments"))
                  .filter(f -> {
                     try {
                        f.setAccessible(true);
                        return f.get(builtinNode) != null;
                     } catch (IllegalAccessException var3x) {
                        throw new AssertionError(var3x);
                     }
                  })
                  .count()
         );
      }

      assert providedArgumentNodeCount == argumentNodeCount : nodeclass + " provided=" + providedArgumentNodeCount + " required=" + argumentNodeCount;
   }

   public interface Inlineable extends NodeInterface {
      JSBuiltinNode.Inlined createInlined();
   }

   public interface Inlined extends NodeInterface {
      Object callInlined(Object[] arguments) throws JSBuiltinNode.RewriteToCallException;

      default RuntimeException rewriteToCall() {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw JSBuiltinNode.RewriteToCallException.INSTANCE;
      }
   }

   static final class RewriteToCallException extends RuntimeException {
      static final RuntimeException INSTANCE = new JSBuiltinNode.RewriteToCallException();

      private RewriteToCallException() {
         super(null, null, true, false);
      }
   }
}

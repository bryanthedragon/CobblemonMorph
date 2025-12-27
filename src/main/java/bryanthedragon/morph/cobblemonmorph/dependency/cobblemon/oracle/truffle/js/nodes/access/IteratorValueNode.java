package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.Set;

@GenerateUncached
@ImportStatic(JSConfig.class)
public abstract class IteratorValueNode extends JavaScriptBaseNode {
   protected IteratorValueNode() {
   }

   public abstract Object execute(Object iterResult);

   public static IteratorValueNode create() {
      return IteratorValueNodeGen.create();
   }

   public static IteratorValueNode getUncached() {
      return IteratorValueNodeGen.getUncached();
   }

   public static JavaScriptNode create(JavaScriptNode iterResult) {
      return new IteratorValueNode.Unary(iterResult);
   }

   @Specialization
   protected Object doIteratorNext(
      JSDynamicObject iterResult, @Cached(value = "createGetValueNode()", uncached = "uncachedGetValueNode()") PropertyGetNode getValueNode
   ) {
      return getValueNode != null ? getValueNode.getValue(iterResult) : JSObject.get(iterResult, Strings.VALUE);
   }

   @Specialization(guards = "isForeignObject(obj)", limit = "InteropLibraryLimit")
   protected Object doForeignObject(Object obj, @CachedLibrary("obj") InteropLibrary interop, @Cached ImportValueNode importValueNode) {
      try {
         return importValueNode.executeWithTarget(interop.readMember(obj, Strings.VALUE_JLS));
      } catch (UnknownIdentifierException | UnsupportedMessageException var5) {
         throw Errors.createTypeErrorInteropException(obj, var5, Strings.VALUE_JLS, this);
      }
   }

   PropertyGetNode createGetValueNode() {
      return PropertyGetNode.create(Strings.VALUE, this.getLanguage().getJSContext());
   }

   static PropertyGetNode uncachedGetValueNode() {
      return null;
   }

   public static final class Unary extends JavaScriptNode {
      @Node.Child
      @Executed
      protected JavaScriptNode iterResultNode;
      @Node.Child
      protected IteratorValueNode iteratorValueNode;

      protected Unary(JavaScriptNode iterResult) {
         this.iterResultNode = iterResult;
         this.iteratorValueNode = IteratorValueNode.create();
      }

      @Override
      public Object execute(VirtualFrame frame) {
         return this.iteratorValueNode.execute(this.iterResultNode.execute(frame));
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new IteratorValueNode.Unary(cloneUninitialized(this.iterResultNode, materializedTags));
      }
   }
}

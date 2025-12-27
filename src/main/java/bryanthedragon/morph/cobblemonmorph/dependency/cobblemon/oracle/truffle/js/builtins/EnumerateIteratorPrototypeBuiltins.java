package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class EnumerateIteratorPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<EnumerateIteratorPrototypeBuiltins.EnumerateIteratorPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new EnumerateIteratorPrototypeBuiltins();

   protected EnumerateIteratorPrototypeBuiltins() {
      super(JSFunction.ENUMERATE_ITERATOR_PROTOTYPE_NAME, EnumerateIteratorPrototypeBuiltins.EnumerateIteratorPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, EnumerateIteratorPrototypeBuiltins.EnumerateIteratorPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case next:
            return EnumerateIteratorPrototypeBuiltinsFactory.EnumerateNextNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum EnumerateIteratorPrototype implements BuiltinEnum<EnumerateIteratorPrototypeBuiltins.EnumerateIteratorPrototype> {
      next(0);

      private final int length;

      private EnumerateIteratorPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class EnumerateNextNode extends JSBuiltinNode {
      @Node.Child
      private CreateIterResultObjectNode createIterResultObjectNode;
      @Node.Child
      private PropertyGetNode getIteratorNode;
      @Node.Child
      private ImportValueNode importValueNode;
      private final BranchProfile errorBranch;

      public EnumerateNextNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.createIterResultObjectNode = CreateIterResultObjectNode.create(context);
         this.getIteratorNode = PropertyGetNode.createGetHidden(JSRuntime.ENUMERATE_ITERATOR_ID, context);
         this.importValueNode = ImportValueNode.create();
         this.errorBranch = BranchProfile.create();
      }

      @Specialization
      public JSDynamicObject execute(VirtualFrame frame, Object target, @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary interop) {
         Object iterator = this.getIteratorNode.getValue(target);
         if (iterator == Undefined.instance) {
            this.errorBranch.enter();
            throw Errors.createTypeError("Enumerate iterator required");
         } else {
            try {
               if (interop.hasIteratorNextElement(iterator)) {
                  try {
                     Object nextValue = interop.getIteratorNextElement(iterator);
                     Object importedValue = this.importValueNode.executeWithTarget(nextValue);
                     return this.createIterResultObjectNode.execute(frame, importedValue, false);
                  } catch (StopIterationException var7) {
                  }
               }

               return this.createIterResultObjectNode.execute(frame, Undefined.instance, true);
            } catch (UnsupportedMessageException var8) {
               this.errorBranch.enter();
               throw Errors.createTypeErrorInteropException(iterator, var8, "next", null);
            }
         }
      }
   }
}

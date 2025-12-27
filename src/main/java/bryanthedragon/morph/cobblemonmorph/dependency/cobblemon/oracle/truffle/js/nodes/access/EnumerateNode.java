package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.interop.EmptyIterator;
import com.oracle.truffle.js.runtime.interop.InteropArrayIndexIterator;
import com.oracle.truffle.js.runtime.interop.InteropMemberIterator;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.ForInIterator;
import java.util.Set;

@ImportStatic(JSConfig.class)
public abstract class EnumerateNode extends JavaScriptNode {
   private final boolean values;
   private final boolean requireIterable;
   protected final JSContext context;
   @Node.Child
   @Executed
   protected JavaScriptNode targetNode;
   @Node.Child
   private PropertySetNode setEnumerateIteratorNode;
   @Node.Child
   private PropertySetNode setForInIteratorNode;

   protected EnumerateNode(JSContext context, boolean values, boolean requireIterable, JavaScriptNode targetNode) {
      this.context = context;
      this.values = values;
      this.requireIterable = requireIterable;
      this.targetNode = targetNode;
   }

   public static EnumerateNode create(JSContext context, JavaScriptNode target, boolean values) {
      return EnumerateNodeGen.create(context, values, false, target);
   }

   public static EnumerateNode create(JSContext context, boolean values, boolean requireIterable) {
      return EnumerateNodeGen.create(context, values, requireIterable, null);
   }

   EnumerateNode copyRecursive() {
      return create(this.context, this.values, this.requireIterable);
   }

   public abstract JSDynamicObject execute(VirtualFrame frame);

   public abstract JSDynamicObject execute(Object iteratedObject);

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return EnumerateNodeGen.create(this.context, this.values, this.requireIterable, cloneUninitialized(this.targetNode, materializedTags));
   }

   @Specialization(guards = {"isJSDynamicObject(iteratedObject)", "!isJSAdapter(iteratedObject)"})
   protected JSDynamicObject doEnumerateObject(JSDynamicObject iteratedObject, @Cached("createBinaryProfile()") ConditionProfile isObject) {
      return isObject.profile(JSRuntime.isObject(iteratedObject)) ? this.newForInIterator(iteratedObject) : this.newEmptyIterator();
   }

   @Specialization(guards = "isJSAdapter(iteratedObject)")
   protected JSDynamicObject doEnumerateJSAdapter(JSDynamicObject iteratedObject, @Cached("createValues()") EnumerateNode enumerateCallbackResultNode) {
      JSDynamicObject adaptee = JSAdapter.getAdaptee(iteratedObject);

      assert JSRuntime.isObject(adaptee);

      Object getIds = JSObject.get(adaptee, this.values ? JSAdapter.GET_VALUES : JSAdapter.GET_IDS);
      if (JSFunction.isJSFunction(getIds)) {
         Object returnValue = JSFunction.call((JSFunctionObject)getIds, adaptee, JSArguments.EMPTY_ARGUMENTS_ARRAY);
         if (JSRuntime.isObject(returnValue)) {
            return enumerateCallbackResultNode.execute(returnValue);
         }
      }

      return this.newEmptyIterator();
   }

   EnumerateNode createValues() {
      return create(this.context, true, false);
   }

   @Specialization(guards = "isForeignObject(iteratedObject)", limit = "InteropLibraryLimit")
   protected JSDynamicObject doEnumerateTruffleObject(
      Object iteratedObject,
      @CachedLibrary("iteratedObject") InteropLibrary interop,
      @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary keysInterop,
      @Cached BranchProfile notIterable
   ) {
      try {
         if (!interop.isNull(iteratedObject)) {
            if (this.values) {
               if (interop.hasIterator(iteratedObject)) {
                  Object iterator = interop.getIterator(iteratedObject);
                  return this.newEnumerateIterator(iterator);
               }
            } else if (interop.hasArrayElements(iteratedObject)) {
               return this.newEnumerateIterator(InteropArrayIndexIterator.create(iteratedObject));
            }

            if (interop.isString(iteratedObject)) {
               TruffleString string = interop.asTruffleString(iteratedObject);
               return this.enumerateString(string);
            }

            if (interop.hasHashEntries(iteratedObject)) {
               Object iterator = this.values ? interop.getHashValuesIterator(iteratedObject) : interop.getHashKeysIterator(iteratedObject);
               return this.newEnumerateIterator(iterator);
            }

            if (interop.hasMembers(iteratedObject)) {
               Object keysObj = interop.getMembers(iteratedObject);

               assert InteropLibrary.getUncached().hasArrayElements(keysObj);

               long longSize = keysInterop.getArraySize(keysObj);
               return this.newEnumerateIterator(InteropMemberIterator.create(this.values, iteratedObject, keysObj, longSize));
            }
         }
      } catch (UnsupportedMessageException var8) {
      }

      notIterable.enter();
      if (this.requireIterable) {
         throw Errors.createTypeErrorNotIterable(iteratedObject, this);
      } else {
         return this.newEmptyIterator();
      }
   }

   private JSDynamicObject enumerateString(TruffleString string) {
      return this.newForInIterator(JSString.create(this.context, this.getRealm(), string));
   }

   private JSDynamicObject newEmptyIterator() {
      return this.newEnumerateIterator(EmptyIterator.create());
   }

   private JSDynamicObject newEnumerateIterator(Object iterator) {
      if (this.setEnumerateIteratorNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.setEnumerateIteratorNode = this.insert(PropertySetNode.createSetHidden(JSRuntime.ENUMERATE_ITERATOR_ID, this.context));
      }

      JSObject obj = JSOrdinary.create(this.context, this.context.getEnumerateIteratorFactory(), this.getRealm());
      this.setEnumerateIteratorNode.setValue(obj, iterator);
      return obj;
   }

   private JSDynamicObject newForInIterator(JSDynamicObject obj) {
      if (this.setForInIteratorNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.setForInIteratorNode = this.insert(PropertySetNode.createSetHidden(JSRuntime.FOR_IN_ITERATOR_ID, this.context));
      }

      JSDynamicObject iteratorObj = JSOrdinary.create(this.context, this.context.getForInIteratorFactory(), this.getRealm());
      this.setForInIteratorNode.setValue(iteratorObj, new ForInIterator(obj, this.values));
      return iteratorObj;
   }

   @Specialization(guards = {"!isJSObject(iteratedObject)", "!isForeignObject(iteratedObject)"})
   protected JSDynamicObject doNonObject(
      Object iteratedObject, @Cached("createToObjectNoCheck(context)") JSToObjectNode toObjectNode, @Cached("copyRecursive()") EnumerateNode enumerateNode
   ) {
      return enumerateNode.execute(toObjectNode.execute(iteratedObject));
   }
}

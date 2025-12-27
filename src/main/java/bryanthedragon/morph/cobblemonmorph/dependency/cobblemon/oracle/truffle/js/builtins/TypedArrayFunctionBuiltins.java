package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;

public final class TypedArrayFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<TypedArrayFunctionBuiltins.TypedArrayFunction> {
   public static final JSBuiltinsContainer BUILTINS = new TypedArrayFunctionBuiltins();

   protected TypedArrayFunctionBuiltins() {
      super(JSArrayBufferView.CLASS_NAME, TypedArrayFunctionBuiltins.TypedArrayFunction.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TypedArrayFunctionBuiltins.TypedArrayFunction builtinEnum
   ) {
      switch (builtinEnum) {
         case of:
            return TypedArrayFunctionBuiltinsFactory.TypedArrayOfNodeGen.create(context, builtin, args().withThis().varArgs().createArgumentNodes(context));
         case from:
            return TypedArrayFunctionBuiltinsFactory.TypedArrayFromNodeGen.create(context, builtin, args().withThis().varArgs().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public abstract static class TypedArrayFromNode extends ArrayFunctionBuiltins.JSArrayFromNode {
      private final BranchProfile growProfile = BranchProfile.create();

      public TypedArrayFromNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, true);
      }

      @Specialization
      @Override
      protected JSDynamicObject arrayFrom(Object thisObj, Object[] args) {
         Object source = JSRuntime.getArgOrUndefined(args, 0);
         Object mapFn = JSRuntime.getArgOrUndefined(args, 1);
         Object thisArg = JSRuntime.getArgOrUndefined(args, 2);
         if (!JSFunction.isConstructor(thisObj)) {
            throw Errors.createTypeErrorNotAConstructor(thisObj, this.getContext());
         } else {
            return this.arrayFromIntl(thisObj, source, mapFn, thisArg, false);
         }
      }

      @Override
      protected JSDynamicObject arrayFromIterable(Object thisObj, Object items, Object usingIterator, Object mapFn, Object thisArg, boolean mapping) {
         SimpleArrayList<Object> values = new SimpleArrayList<>();
         IteratorRecord iteratorRecord = this.getIterator(items, usingIterator);

         while (true) {
            Object next = this.iteratorStep(iteratorRecord);
            if (next == Boolean.FALSE) {
               int len = values.size();
               JSTypedArrayObject obj = this.getArraySpeciesConstructorNode().typedArrayCreate((JSDynamicObject)thisObj, len);

               for (int k = 0; k < len; k++) {
                  Object mapped = values.get(k);
                  if (mapping) {
                     mapped = this.callMapFn(thisArg, (JSDynamicObject)mapFn, mapped, k);
                  }

                  this.writeOwn(obj, k, mapped);
               }

               return obj;
            }

            Object nextValue = this.getIteratorValue((JSDynamicObject)next);
            values.add(nextValue, this.growProfile);
         }
      }
   }

   public static enum TypedArrayFunction implements BuiltinEnum<TypedArrayFunctionBuiltins.TypedArrayFunction> {
      of(0),
      from(1);

      private final int length;

      private TypedArrayFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class TypedArrayOfNode extends ArrayFunctionBuiltins.JSArrayFunctionOperation {
      public TypedArrayOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, true);
      }

      @Specialization
      protected JSDynamicObject arrayOf(Object thisObj, Object... args) {
         if (!this.isTypedArrayConstructor(thisObj)) {
            throw Errors.createTypeErrorNotAConstructor(thisObj, this.getContext());
         } else {
            int len = args.length;
            JSTypedArrayObject newObj = this.getArraySpeciesConstructorNode().typedArrayCreate((JSDynamicObject)thisObj, len);

            for (int k = 0; k < len; k++) {
               Object kValue = args[k];
               this.write(newObj, k, kValue);
            }

            return newObj;
         }
      }
   }
}

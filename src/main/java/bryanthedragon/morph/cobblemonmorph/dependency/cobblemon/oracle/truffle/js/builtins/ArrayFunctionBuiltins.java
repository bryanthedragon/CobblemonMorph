package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.access.GetIteratorNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IsArrayNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.IteratorCloseNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.array.ArrayCreateNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.EnumSet;

public final class ArrayFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<ArrayFunctionBuiltins.ArrayFunction> {
   public static final JSBuiltinsContainer BUILTINS = new ArrayFunctionBuiltins();

   protected ArrayFunctionBuiltins() {
      super(JSArray.CLASS_NAME, ArrayFunctionBuiltins.ArrayFunction.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ArrayFunctionBuiltins.ArrayFunction builtinEnum) {
      switch (builtinEnum) {
         case isArray:
            return ArrayFunctionBuiltinsFactory.JSIsArrayNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case of:
            return ArrayFunctionBuiltinsFactory.JSArrayOfNodeGen.create(context, builtin, false, args().withThis().varArgs().createArgumentNodes(context));
         case from:
            return ArrayFunctionBuiltinsFactory.JSArrayFromNodeGen.create(context, builtin, false, args().withThis().varArgs().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum ArrayFunction implements BuiltinEnum<ArrayFunctionBuiltins.ArrayFunction> {
      isArray(1),
      of(0),
      from(1);

      private final int length;

      private ArrayFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public int getECMAScriptVersion() {
         return EnumSet.of(of, from).contains(this) ? 6 : BuiltinEnum.super.getECMAScriptVersion();
      }
   }

   public abstract static class JSArrayFromNode extends ArrayFunctionBuiltins.JSArrayFunctionOperation {
      @Node.Child
      private JSFunctionCallNode callMapFnNode;
      @Node.Child
      private IteratorCloseNode iteratorCloseNode;
      @Node.Child
      private JSFunctionCallNode callIteratorMethodNode;
      @Node.Child
      private IteratorValueNode getIteratorValueNode;
      @Node.Child
      private IteratorStepNode iteratorStepNode;
      @Node.Child
      private GetMethodNode getIteratorMethodNode;
      @Node.Child
      private IsJSObjectNode isObjectNode;
      @Node.Child
      private PropertyGetNode getNextMethodNode;
      @Node.Child
      private JSGetLengthNode getSourceLengthNode;
      @Node.Child
      private IsArrayNode isFastArrayNode;
      private final ConditionProfile isIterable = ConditionProfile.createBinaryProfile();

      public JSArrayFromNode(JSContext context, JSBuiltin builtin, boolean isTypedArray) {
         super(context, builtin, isTypedArray);
         this.getIteratorMethodNode = GetMethodNode.create(context, Symbol.SYMBOL_ITERATOR);
         this.isFastArrayNode = this.isTypedArrayImplementation ? null : IsArrayNode.createIsFastArray();
      }

      protected void iteratorCloseAbrupt(JSDynamicObject iterator) {
         if (this.iteratorCloseNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.iteratorCloseNode = this.insert(IteratorCloseNode.create(this.getContext()));
         }

         this.iteratorCloseNode.executeAbrupt(iterator);
      }

      protected IteratorRecord getIterator(Object object, Object usingIterator) {
         if (this.callIteratorMethodNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callIteratorMethodNode = this.insert(JSFunctionCallNode.createCall());
         }

         if (this.isObjectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.isObjectNode = this.insert(IsJSObjectNode.create());
         }

         if (this.getNextMethodNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getNextMethodNode = this.insert(PropertyGetNode.create(Strings.NEXT, this.getContext()));
         }

         return GetIteratorNode.getIterator(object, usingIterator, this.callIteratorMethodNode, this.isObjectNode, this.getNextMethodNode, this);
      }

      protected Object getIteratorValue(JSDynamicObject iteratorResult) {
         if (this.getIteratorValueNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getIteratorValueNode = this.insert(IteratorValueNode.create());
         }

         return this.getIteratorValueNode.execute(iteratorResult);
      }

      protected Object iteratorStep(IteratorRecord iteratorRecord) {
         if (this.iteratorStepNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.iteratorStepNode = this.insert(IteratorStepNode.create());
         }

         return this.iteratorStepNode.execute(iteratorRecord);
      }

      protected final Object callMapFn(Object target, JSDynamicObject function, Object... userArguments) {
         if (this.callMapFnNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callMapFnNode = this.insert(JSFunctionCallNode.createCall());
         }

         return this.callMapFnNode.executeCall(JSArguments.create(target, function, userArguments));
      }

      protected long getSourceLength(Object thisObject) {
         if (this.getSourceLengthNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getSourceLengthNode = this.insert(JSGetLengthNode.create(this.getContext()));
         }

         return this.getSourceLengthNode.executeLong(thisObject);
      }

      @Specialization
      protected JSDynamicObject arrayFrom(Object thisObj, Object[] args) {
         Object items = JSRuntime.getArgOrUndefined(args, 0);
         Object mapFn = JSRuntime.getArgOrUndefined(args, 1);
         Object thisArg = JSRuntime.getArgOrUndefined(args, 2);
         return this.arrayFromIntl(thisObj, items, mapFn, thisArg, true);
      }

      protected JSDynamicObject arrayFromIntl(Object thisObj, Object items, Object mapFn, Object thisArg, boolean setLength) {
         boolean mapping;
         if (mapFn == Undefined.instance) {
            mapping = false;
         } else {
            this.checkCallbackIsFunction(mapFn);
            mapping = true;
         }

         Object usingIterator = this.getIteratorMethodNode.executeWithTarget(items);
         if (this.isIterable.profile(usingIterator != Undefined.instance)) {
            return this.arrayFromIterable(thisObj, items, usingIterator, mapFn, thisArg, mapping);
         } else {
            Object itemsObject = this.toObject(items);
            return this.arrayFromArrayLike(thisObj, itemsObject, mapFn, thisArg, mapping, setLength);
         }
      }

      protected JSDynamicObject arrayFromIterable(Object thisObj, Object items, Object usingIterator, Object mapFn, Object thisArg, boolean mapping) {
         JSDynamicObject obj = this.constructOrArray(thisObj, 0L, false);
         IteratorRecord iteratorRecord = this.getIterator(items, usingIterator);
         return this.arrayFromIteratorRecord(obj, iteratorRecord, mapFn, thisArg, mapping);
      }

      private JSDynamicObject arrayFromIteratorRecord(JSDynamicObject obj, IteratorRecord iteratorRecord, Object mapFn, Object thisArg, boolean mapping) {
         long k = 0L;

         try {
            while (true) {
               Object next = this.iteratorStep(iteratorRecord);
               if (next == Boolean.FALSE) {
                  this.setLength(obj, k);
                  return obj;
               }

               Object mapped = this.getIteratorValue((JSDynamicObject)next);
               if (mapping) {
                  mapped = this.callMapFn(thisArg, (JSDynamicObject)mapFn, mapped, JSRuntime.positiveLongToIntOrDouble(k));
               }

               if (!this.isTypedArrayImplementation && !this.isFastArrayNode.execute(obj)) {
                  JSRuntime.createDataPropertyOrThrow(obj, Strings.fromLong(k), mapped);
               } else {
                  this.writeOwn(obj, k, mapped);
               }

               k++;
            }
         } catch (AbstractTruffleException var10) {
            this.iteratorCloseAbrupt(iteratorRecord.getIterator());
            throw var10;
         }
      }

      protected JSDynamicObject arrayFromArrayLike(Object thisObj, Object items, Object mapFn, Object thisArg, boolean mapping, boolean setLength) {
         long len = this.getSourceLength(items);
         JSDynamicObject obj = this.constructOrArray(thisObj, len, true);

         for (long k = 0L; k < len; k++) {
            Object value = this.read(items, k);
            Object mapped = value;
            if (mapping) {
               mapped = this.callMapFn(thisArg, (JSDynamicObject)mapFn, value, JSRuntime.positiveLongToIntOrDouble(k));
            }

            if (!this.isTypedArrayImplementation && !this.isFastArrayNode.execute(obj)) {
               JSRuntime.createDataPropertyOrThrow(obj, Strings.fromLong(k), mapped);
            } else {
               this.writeOwn(obj, k, mapped);
            }
         }

         if (setLength) {
            this.setLength(obj, len);
         }

         return obj;
      }
   }

   public abstract static class JSArrayFunctionOperation extends ArrayPrototypeBuiltins.JSArrayOperation {
      @Node.Child
      private ArrayCreateNode arrayCreateNode;
      private final ConditionProfile isConstructor = ConditionProfile.createBinaryProfile();

      public JSArrayFunctionOperation(JSContext context, JSBuiltin builtin, boolean isTypedArray) {
         super(context, builtin, isTypedArray);
      }

      protected JSDynamicObject constructOrArray(Object thisObj, long len, boolean provideLengthArg) {
         if (this.isTypedArrayImplementation) {
            return this.getArraySpeciesConstructorNode().typedArrayCreate((JSDynamicObject)thisObj, JSRuntime.longToIntOrDouble(len));
         } else if (this.isConstructor.profile(JSFunction.isConstructor(thisObj))) {
            return provideLengthArg
               ? (JSDynamicObject)this.getArraySpeciesConstructorNode().construct((JSDynamicObject)thisObj, JSRuntime.longToIntOrDouble(len))
               : (JSDynamicObject)this.getArraySpeciesConstructorNode().construct((JSDynamicObject)thisObj);
         } else {
            if (this.arrayCreateNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.arrayCreateNode = this.insert(ArrayCreateNode.create(this.getContext()));
            }

            return this.arrayCreateNode.execute(len);
         }
      }

      protected boolean isTypedArrayConstructor(Object thisObj) {
         return JSFunction.isConstructor(thisObj) && thisObj != this.getRealm().getArrayConstructor();
      }
   }

   public abstract static class JSArrayOfNode extends ArrayFunctionBuiltins.JSArrayFunctionOperation {
      public JSArrayOfNode(JSContext context, JSBuiltin builtin, boolean isTypedArray) {
         super(context, builtin, isTypedArray);
      }

      @Specialization
      protected JSDynamicObject arrayOf(Object thisObj, Object[] args) {
         int len = args.length;
         JSDynamicObject obj = this.constructOrArray(thisObj, len, true);
         int pos = 0;

         for (Object arg : args) {
            Object value = JSRuntime.nullToUndefined(arg);
            JSRuntime.createDataPropertyOrThrow(obj, Strings.fromInt(pos), value);
            pos++;
         }

         JSObject.set(obj, JSAbstractArray.LENGTH, len, true, this);
         return obj;
      }
   }

   public abstract static class JSIsArrayNode extends JSBuiltinNode {
      @Node.Child
      private com.oracle.truffle.js.nodes.unary.JSIsArrayNode isArrayNode = com.oracle.truffle.js.nodes.unary.JSIsArrayNode.createIsArrayLike();

      public JSIsArrayNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean isArray(Object object) {
         return this.isArrayNode.execute(object);
      }
   }
}
